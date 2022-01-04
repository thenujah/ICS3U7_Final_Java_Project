package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;

/**
 * A class which controls the main menu scene of the game.
 * 
 * @version 1.0
 * @since 1.0
 */
public class MainMenu extends Scene{
	
	private final Button startButton;
	private final Button instructionsButton;
	private final Button backButton;
	private final Button quitButton;

	public MainMenu(AppManager app) {
		super(app);
		
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

		startButton.render(g);
		instructionsButton.render(g);
		backButton.render(g);
		quitButton.render(g);

	}

}
