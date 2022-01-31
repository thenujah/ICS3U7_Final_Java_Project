package game.scenes;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;
import game.engine.util.KeyboardInput;
import game.scenes.*;

/**
 * A class which controls the instruction scene of the game.
 * 
 * @version 1.0
 * @since 1.0
 */
public class InstructionsPage extends Scene{

	private Button instructions;
	
	//Colours
	private Color BLACK = new Color(40, 38, 48);
	private Color GREEN = new Color(1, 97, 53);
	private Color MINT_GREEN = new Color(193,254,193);
	private Font subtitleFont;

	//Fonts
	private final Font titleFont;
	
	//Buttons
	private Button backButton;
	
	public InstructionsPage (AppManager app) {
		super(app);
		subtitleFont = new Font("DialogInput", Font.PLAIN, 20);
		titleFont = new Font("Serif", Font.BOLD, 60);
		
		//Back Button
			backButton = new Button(200, 50);
			backButton.setCenter(1100, 600);
			backButton.backgroundColor = GREEN;
			backButton.font = subtitleFont;
			backButton.text = "Back";

	}
	
public void update() {
		
		if (backButton.isClicked()) {
			app.currentScene = "main menu";
		}
		}

	
		public void render(Graphics2D g) {
			g.setColor(MINT_GREEN);
			g.fillRect(0, 0, 1280, 800);
			
			//Title
			g.setFont(titleFont);
		    g.setColor(GREEN);
			g.drawString("Instructions", 480, 100);
			
			//Instructions
			g.setFont(subtitleFont);
			g.drawString("Hello, and welcome to our game! Here you will find the instructions on how to play our game as well as the objective. Have fun!\n", 50, 150);
		
			g.drawString("This is a top-down game created for the user to be able to fight and defeat a number of enemies who are designed to attack ",50 ,200);
			g.drawString("you with their weapons.", 50, 225);
			
			g.drawString("You must defeat each enemy and level to move on to higher levels and battle more enemies at a time.", 50, 275);
			g.drawString("The difficulty level will increase as you go and you will be rewarded with a point for each level you complete.", 50, 300);
			
			g.drawString("You would keep playing the game until you lose a level, and whichever checkpoint/ level you stopped at and however many ", 50, 350);
			g.drawString("points you’ve earned, that will be your high score saved for you in the game section. ", 50, 375);
			
			g.drawString("Then you will be able to play again and again if you choose to beat your high score or save it for another time.", 50, 425);
			
			g.drawString("You will start off by having your character with no weapons whatsoever to fight the enemy, but as you move on to the higher", 50, 475);
			g.drawString("levels you will be upgraded. Your main objective is to fight and battle as many enemies as you can and move on to the", 50, 500);
			g.drawString("the next level.", 50, 525);
		
			//Button
			backButton.render(g);
		
		}
}
