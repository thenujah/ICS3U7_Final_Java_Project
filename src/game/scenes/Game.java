package game.scenes;

import java.util.ArrayList; 
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;
import game.engine.util.Camera;
import game.engine.util.Positioning;
import game.engine.util.KeyboardInput;
import game.game_objects.Level;
import game.game_objects.Player;
import game.game_objects.Enemy;
import game.game_objects.Entity;

/**
 * A class which controls the game scene of the game.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Game extends Scene {

    private final Font font = new Font("DialogInput", Font.PLAIN, 20);

    private Level level;
    private final Camera camera;
    private final Player player;

    private int currentLevel = 1;
    private boolean initializeLevel = false;

    private BufferedImage pauseTitle;
    private BufferedImage youWin;
    private BufferedImage youLost;
    private Button resume;
    private Button back;

    private final int INBETWEEN_LEVEL_TIMER = 120;
    private final int DISTANCE_BETWEEN_BUTTONS = 25;

    private int levelCompleteMessageTimer = 0;
    private boolean levelCompleted = false;
    private boolean wonLevel = false;
    private boolean paused = false;
    private boolean fadeIn = false;
    private double titleVelocity = 0;

    /**
     * The constructor for the Game.
     *
     * @param app The AppManager controlling the app.
     */
    public Game(AppManager app) {
        super(app);

        camera = new Camera(2);
        player = new Player();
        level = new Level(getDifficulty());

        try {
            pauseTitle = ImageIO.read(new File("./assets/Paused.png"));
            youWin = ImageIO.read(new File("./assets/YouWin.png"));
            youLost = ImageIO.read(new File("./assets/YouLost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        resume = new Button("./assets/Resume.png", camera.getScale());
        resume.setCenter(Positioning.SCREEN_CENTER_X, 275);

        back = new Button("./assets/Back.png", camera.getScale());
        back.setTopLeft(resume.rect.getLeft(), resume.rect.getTop() + DISTANCE_BETWEEN_BUTTONS * 2);
    }

    /**
     * A getter method for the difficulty of the current level.
     * 
     * @return The difficulty.
     */
    public int getDifficulty() { return currentLevel / 5 + 1; }

    /**
     * A method which initializes a new level.
     * 
     * @param difficulty The difficulty of the level.
     */
    public void initLevel(int difficulty) {
        level = new Level(difficulty);
        player.setCenter(200, 200);
        player.resetHealth();
        currentLevel++;
        initializeLevel = false;
    }

    /**
     * A method which updates the state of the instructions page.
     */
    public void update() {
        if (initializeLevel) initLevel(getDifficulty());

        // Pausing the game.
        if (KeyboardInput.wasPressed("esc")) {
            paused = !paused;
            fadeIn = true;
        }

        if (!paused && !levelCompleted) {
        	// Updating the player.
            player.updatePosition(level.currentRoom, level);
            player.updateDirection(camera.getTranslation(), camera.getScale());

            // Updating the enemies.
            for (Entity entity : level.currentRoom.enemies) {
                Enemy enemy = (Enemy) entity;
                enemy.movement(player, level.currentRoom);
            }

            // Attacking the enemies.
            ArrayList<Entity> enemiesHit = player.attack(level.currentRoom.enemies);

            for (Entity enemy : enemiesHit) {

                if (player.isAttacking() && !enemy.collidedWith(player)) {
                    enemy.push(player.getDirectionFacing(), 12);
                }

                if (enemy.getHealth() == 0) {
                    level.killEntity(enemy);
                }

            }

            // Checking if the player won.
            if (level.getTotalEnemies() == 0) {
                levelCompleted = true;
                wonLevel = true;
                fadeIn = true;
                levelCompleteMessageTimer = INBETWEEN_LEVEL_TIMER;
            }

            // Enemies attacking the player.
            ArrayList<Entity> playerHits = player.getEntityCollisions(level.currentRoom.enemies);
            for (Entity entity : playerHits) {
                Enemy enemy = (Enemy) entity;

                enemy.attack(player);

                // Checking if the player lost.
                if (player.getHealth() == 0) {
                    if (app.getHighScore() < currentLevel) app.setHighScore(currentLevel);
                    currentLevel = 0;
                    levelCompleted = true;
                    fadeIn = true;
                    levelCompleteMessageTimer = INBETWEEN_LEVEL_TIMER;
                }
            }

            // Adding a camera shake if the player was hit by an enemy.
            if (playerHits.size() > 0) {
                camera.shake();
            }
        } else if (paused) {

        	// Pause menu events
            if (resume.isClicked()) paused = !paused;
            else if (back.isClicked()) {
                paused = !paused;
                app.currentScene = "main menu";
            }

        }

        camera.update(player.getSprite());
    }

    /**
     * A method which renders the all the objects in the game.
     *
     * @param g The Graphics2D object used to draw images to the screen.
     */
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Positioning.SCREEN_WIDTH, Positioning.SCREEN_HEIGHT);
        level.currentRoom.renderGround(g, camera.getTranslation(), camera.getScale());
        level.currentRoom.renderBackground(g, camera.getTranslation(), camera.getScale());

        for (Entity enemy : level.currentRoom.enemies) {
            enemy.render(g, camera.getTranslation(), camera.getScale());
        }

        player.render(g, camera.getTranslation(), camera.getScale());

        level.currentRoom.renderForeground(g, camera.getTranslation(), camera.getScale());

        // Render the UI elements.
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Lvl - " + currentLevel, 50, 75);
        g.drawString("HP - " + player.getHealth(), 50, 50);

        // Update the animations for the pause menu.
        if (paused || levelCompleteMessageTimer > 0) {
        	if (titleVelocity <= 0.1 && fadeIn) {
                titleVelocity = 128;
                fadeIn = false;
            } else { 
                titleVelocity = Positioning.clamp(titleVelocity / 1.2, titleVelocity, 0.1);
            }
        }

        // Render the pause menu.
        if (paused) {
            renderPauseMenu(g);
        } else if (levelCompleteMessageTimer > 1) {
            renderGameOverScreen(g);
        } else if (levelCompleteMessageTimer == 1) {
        	initializeLevel = true;
        	levelCompleted = false;
        	levelCompleteMessageTimer--;
        	if (!wonLevel) app.currentScene = "main menu";
        	wonLevel = false;
        }
    }

    /**
     * A method which renders the pause menu over the game.
     *
     * @param g The Graphics2D object used to draw images to the screen.
     */
    private void renderPauseMenu(Graphics2D g) {
        g.setColor(new Color(0f, 0f, 0f, 0.5f));
        g.fillRect(0, 0, Positioning.SCREEN_WIDTH, Positioning.SCREEN_HEIGHT);

        AffineTransform pauseTitleTransform = new AffineTransform();
        pauseTitleTransform.translate(Positioning.SCREEN_CENTER_X - pauseTitle.getWidth(),
                            (int)(80 - titleVelocity * camera.getScale()));
        pauseTitleTransform.scale(camera.getScale(), camera.getScale());

        AffineTransform menuTransform = new AffineTransform();
        menuTransform.translate((int)(resume.rect.getLeft() 
                                - titleVelocity * camera.getScale()), resume.rect.getTop());
        menuTransform.scale(camera.getScale(), camera.getScale());

        g.drawImage(pauseTitle, pauseTitleTransform, null);

        resume.render(g, menuTransform);
        menuTransform.translate(0, DISTANCE_BETWEEN_BUTTONS);
        back.render(g, menuTransform);
    }

    /**
     * A method which renders the game-over screen over the game.
     *
     * @param g The Graphics2D object used to draw images to the screen.
     */
    private void renderGameOverScreen(Graphics2D g) {
    	levelCompleteMessageTimer--; 

        g.setColor(new Color(0f, 0f, 0f, 0.5f));
        g.fillRect(0, 0, Positioning.SCREEN_WIDTH, Positioning.SCREEN_HEIGHT);

        AffineTransform titleTransform = new AffineTransform();
        titleTransform.translate(Positioning.SCREEN_CENTER_X - pauseTitle.getWidth(),
                            (int)(80 - titleVelocity * camera.getScale()));
        titleTransform.scale(camera.getScale(), camera.getScale());

        if (wonLevel) {
			g.drawImage(youWin, titleTransform, null);
        } else {
			g.drawImage(youLost, titleTransform, null);
        }

    }

}