package game.scenes;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Positioning;
import game.engine.util.KeyboardInput;
import game.engine.util.Camera;
import game.game_objects.TileMap;
import game.game_objects.Level;
import game.game_objects.Player;
import game.game_objects.Enemy;
import game.game_objects.Entity;

/**
 * A class which controls the game scene of the game.
 *
 * @version 2.0
 * @since 1.0
 */
public class Test extends Scene {

	private final Font font = new Font("DialogInput", Font.PLAIN, 20);

	private Level level;
	private Camera camera;
	private Player player;

	private int counter = 0;

	private boolean paused = false;

	public Test(AppManager app) {
		super(app);

		camera = new Camera(2);
		player = new Player();
		level = new Level();
	}

	public void update() {
		if (KeyboardInput.wasPressed("esc")) {
			paused = !paused;
			counter++;
			System.out.println("paused " + counter);
		}

		if (!paused) {
			player.updatePosition(level.currentRoom, level);
			player.updateDirection(camera.getTranslation(), camera.getScale());

			for (Entity entity : level.currentRoom.enemies) {
				Enemy enemy = (Enemy) entity;
				enemy.movement(player, level.currentRoom);
			}

			ArrayList<Entity> enemiesHit = player.attack(level.currentRoom.enemies);

			for (Entity enemy : enemiesHit) {

				if (player.isAttacking() && !enemy.collidedWith(player)) {
					enemy.push(player.getDirectionFacing(), 12);
				}

				if (enemy.getHealth() == 0) {
					level.killEntity(enemy);
				}

			}

			ArrayList<Entity> playerHits = player.getEntityCollisions(level.currentRoom.enemies);
			for (Entity entity : playerHits) {
				Enemy enemy = (Enemy) entity;

				enemy.attack(player);

				if (player.getHealth() == 0) {
					System.out.println("u ded");
					System.exit(0);
				}
			}

			if (level.getTotalEnemies() == 0) {
				System.out.println("u win");
				System.exit(0);
			}

			if (playerHits.size() > 0) {
				camera.shake();
			}
		}

		camera.update(player.getSprite());
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 800);
		level.currentRoom.renderGround(g, camera.getTranslation(), camera.getScale());
		level.currentRoom.renderBackground(g, camera.getTranslation(), camera.getScale());

		for (Entity enemy : level.currentRoom.enemies) {
			enemy.render(g, camera.getTranslation(), camera.getScale());
		}

		player.render(g, camera.getTranslation(), camera.getScale());

		level.currentRoom.renderForeground(g, camera.getTranslation(), camera.getScale());

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("HP: " + player.getHealth(), 50, 50);
	}

}