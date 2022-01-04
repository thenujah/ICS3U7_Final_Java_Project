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
	
	private Button startButton;
	private Button button2;
	private Button button3;
	private Button button4;

	public MainMenu(AppManager app) {
		super(app);
		
		// Main Menu Button
		startButton = new Button(60, 200, 150, 50);
		startButton.text = "Start Game";
		
		// Instructions Button
		button3 = new Button(625, 350, 150, 50);
		button3.text = "Instructions";
				
		// Back to Title Screen Button
		button3 = new Button(625, 300, 150, 50);
		button3.text = "Back to Title Screen";
				
		// Quit Game Button
		button2 = new Button(625, 350, 150, 50);
		button2.text = "Quit Game";

		button4 = new Button(625, 250, 150, 50);
		button4.text = "Quit Game";
	}

	public void update() {
		
	}

	public void render(Graphics2D g) {
		
		// Implementing Buttons
		startButton.render(g);
		// button2.render(g);

		//Implementing Buttons
		startButton.render(g);
		button2.render(g);
		button3.render(g);
		button4.render(g);

	}
	

}
