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
			
			g.drawString("You will the 'a' key to move left, 's' key to move down, 'w' to move up, and 'd' to move right. You click the mouse to ", 50, 475);
			g.drawString("attack, and to aim where your attacking to. In some levels, once you've defeated the enemies, you'll realize there's nothing coming to attack you, ", 50, 500);
			g.drawString("once you get to that stage, you will need to go into a space you'll see anywhere along the perimeter of the map, to enter a ", 50, 525);
			g.drawString("new map where enemeies are waiting to attack.", 50, 550);
			
			g.drawString("If at anytime, you would like to pause the game to resume later, press the 'esc' key to get to the", 50, 600);
			g.drawString("pause screen.", 50, 625);
			
			g.drawString("Have Fun!!", 50, 675);
			//Button
			backButton.render(g);
		
		}
}
