package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;


public class MainMenu extends Scene{
	
	private Button button;
	private Button button2;
	private Button button3;
	private Button button4;

	
	public MainMenu(AppManager app) {
		super(app);
		
		// Main Menu Button
		button = new Button(625, 400, 150, 50);
		button.setText("Start Game");
		
		// Quit Game Button
		button2 = new Button(625, 350, 150, 50);
		button2.setText("Quit Game");
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		
		//Implementing Buttons
		button.render(g);
		button2.render(g);
	}
	

}
