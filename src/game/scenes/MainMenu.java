package game.scenes;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;
import game.engine.util.Positioning;

/**
 * A class which controls the main menu scene of the game.
 * 
 * @version 1.0
 * @since 1.0
 */
public class MainMenu extends Scene{

    // Colours
    private Color BLACK = new Color(40, 38, 48);
    private Color BLUE = new Color(75, 80, 105);
    private Color LIGHT_BLUE = new Color(160, 190, 200);
    private Color WHITE = new Color(230, 240, 238);
    
    private Font subtitleFont;
    
    private Button startGame;
    private Button instructions;
    private Button backToTitleScreen;
    private Button quitGame;
    
    private BufferedImage mainMenu_Background;

    public MainMenu(AppManager app) {
        super(app);
        subtitleFont = new Font("DialogInput", Font.PLAIN, 20);
        
        // Start Game Button
        startGame = new Button(250, 50);
        startGame.setCenter(Positioning.SCREEN_CENTER_X, 240);
        startGame.backgroundColor = BLUE;
        startGame.font = subtitleFont;
        startGame.text = "Start Game";
        
        // Instructions Button
        instructions = new Button(250, 50);
        instructions.setCenter(Positioning.SCREEN_CENTER_X, 310);
        instructions.backgroundColor = BLUE;
        instructions.font = subtitleFont;
        instructions.text = "Instructions";
                
        // Back to Title Screen Button
        backToTitleScreen = new Button(250, 50);
        backToTitleScreen.setCenter(Positioning.SCREEN_CENTER_X, 380);
        backToTitleScreen.backgroundColor = BLUE;
        backToTitleScreen.font = subtitleFont;
        backToTitleScreen.text = "Back to Title Screen";
                
        // Quit Game Button
        quitGame = new Button(250, 50);
        quitGame.setCenter(Positioning.SCREEN_CENTER_X, 450);
        quitGame.backgroundColor = BLUE;
        quitGame.font = subtitleFont;
        quitGame.text = "Quit Game";
        
        try {
			
            mainMenu_Background = ImageIO.read(new File("./assets/MainMenu_Background.jpeg"));
            
        } catch (IOException e) {
        	
            e.printStackTrace();
        }
        
    }
    
    public void update() {
        
        // Starting the game if user presses "Start Game"
        if (startGame.isClicked()) {
            app.currentScene = "game";
        } else if (instructions.isClicked()) {
            app.currentScene = "instructions";
        } else if (backToTitleScreen.isClicked()) {
            app.currentScene = "title screen";
        } else if (quitGame.isClicked()) {
            System.exit(0);
        }

    }

    public void render(Graphics2D g) {
    	
    	g.setColor(Color.black);
        g.fillRect(0,0, Positioning.SCREEN_WIDTH, Positioning.SCREEN_HEIGHT);
        
    	AffineTransform titleTransform = new AffineTransform();
        titleTransform.translate(20, -15);
        titleTransform.scale(1.2, 1.2);
        
        g.drawImage(mainMenu_Background, titleTransform, null);
    	
        startGame.render(g);
        instructions.render(g);
        backToTitleScreen.render(g);
        quitGame.render(g);



        g.drawString("Highscore: Level " + app.getHighscore(), 170, 120);

    }

}
