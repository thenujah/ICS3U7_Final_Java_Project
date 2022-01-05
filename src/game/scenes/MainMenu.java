package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;
import game.engine.util.KeyboardInput;


/**
 * A class which controls the main menu scene of the game.
 * 
 * @version 1.0
 * @since 1.0
 */
public class MainMenu extends Scene{
	
<<<<<<< HEAD
	private final Button startButton;
	private final Button instructionsButton;
	private final Button backButton;
	private final Button quitButton;
=======
	//Colours
	private Color BLACK = new Color(40, 38, 48);
	private Color BLUE = new Color(75, 80, 105);
	private Color LIGHT_BLUE = new Color(160, 190, 200);
	private Color WHITE = new Color(230, 240, 238);
	
	private Font subtitleFont;
	
	private Button startGame;
	private Button instructions;
	private Button backToTitleScreen;
	private Button quitGame;
>>>>>>> Formatting for the title scrren and main menu of buttons done. Colours also updated/changed. New classes created so scenes can be changed. One error present in program though.

	public MainMenu(AppManager app) {
		super(app);
		subtitleFont = new Font("DialogInput", Font.PLAIN, 20);

		
<<<<<<< HEAD
		// Start Button
		startButton = new Button(60, 200, 150, 50);
		startButton.text = "Start Game";
		
		// Instructions Button
		instructionsButton = new Button(625, 350, 150, 50);
		instructionsButton.text = "Instructions";

		// Back to Title Screen Button
		backButton = new Button(625, 350, 150, 50);
		backButton.text = "Back to Title Screen";

		// Quit Game Button
		quitButton = new Button(625, 250, 150, 50);
		quitButton.text = "Quit Game";
	}

	public void update() {}

	public void render(Graphics2D g) {

=======
		// Start Game Button
		startGame = new Button(150, 50);
		startGame.setCenter(625, 240);
		startGame.backgroundColor = BLUE;
		startGame.font = subtitleFont;
		startGame.text = "Start Game";
		
		// Instructions Button
		instructions = new Button(150, 50);
		instructions.setCenter(625, 310);
		instructions.backgroundColor = BLUE;
		instructions.font = subtitleFont;
		instructions.text = "Instructions";
				
		// Back to Title Screen Button
		backToTitleScreen = new Button(200, 50);
		backToTitleScreen.setCenter(625, 380);
		backToTitleScreen.backgroundColor = BLUE;
		backToTitleScreen.font = subtitleFont;
		backToTitleScreen.text = "Back to Title Screen";
				
		// Quit Game Button
		quitGame = new Button(150, 50);
		quitGame.setCenter(625, 450);
		quitGame.backgroundColor = BLUE;
		quitGame.font = subtitleFont;
		quitGame.text = "Quit Game";

	}
	
	public void update() {
		
		// Starting the game if user presses "Start Game"
		if (startGame.isClicked()) {
			app.currentScene = "game";
		}

		if (KeyboardInput.isPressed("space")) {
			System.out.println(true);
		}
		
		// Goes to instructions page if user presses "Instructions"
		if (instructions.isClicked()) {
			app.currentScene = "instructions";
		}

		if (KeyboardInput.isPressed("space")) {
			System.out.println(true);
		}
		
		// Goes back to title screen if user presses "Back to Title Screen"
		if (backToTitleScreen.isClicked()) {
			app.currentScene = "backToTitleScreen";
		}

		if (KeyboardInput.isPressed("space")) {
			System.out.println(true);
		}
		
		// Quits game if user presses "Quit Game"
		if (quitGame.isClicked()) {
			app.currentScene = "quitting";
		}

		if (KeyboardInput.isPressed("space")) {
			System.out.println(true);
		}
	}

	public void render(Graphics2D g) {
		
		//Implementing Buttons
>>>>>>> Formatting for the title scrren and main menu of buttons done. Colours also updated/changed. New classes created so scenes can be changed. One error present in program though.
		startButton.render(g);
		instructionsButton.render(g);
		backButton.render(g);
		quitButton.render(g);

	}

}
