package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;

public class TitleScreen extends Scene {

	private Color BLACK = new Color(40, 38, 48);
	private Color BLUE = new Color(75, 80, 105);
	private Color LIGHT_BLUE = new Color(160, 190, 200);
	private Color WHITE = new Color(230, 240, 238);

	private Font titleFont;
	private Font subtitleFont;

	private Button mainMenuButton;
	private Button exitButton;

	public TitleScreen(AppManager app) {
		super(app);

		titleFont = new Font("Serif", Font.BOLD, 60);
		subtitleFont = new Font("DialogInput", Font.PLAIN, 20);
		
		// Main Menu Button
		mainMenuButton = new Button(150, 50);
		mainMenuButton.setBackgroundColor(BLUE);
		mainMenuButton.setFont(subtitleFont);
		mainMenuButton.setText("Main Menu");
		mainMenuButton.setCenter(625, 350);
		
		// Quit Game Button
		exitButton = new Button(625, 420, 150, 50);
		exitButton.setText("Quit Game");
	}

	public void update() {
		if (mainMenuButton.isClicked()) {
			app.currentScene = "main menu";
		}
	}

	public void render(Graphics2D g) {
		g.setColor(WHITE);
		g.fillRect(0, 0, 1280, 800);

		// Title of the game formatting:'Winter Wonderland'
	    g.setFont(titleFont);
	    g.setColor(BLACK);
		g.drawString("Winter Wonderland", 410, 300);
		
		// Formatting of names, date and class
	    g.setFont(subtitleFont);

	    // Names: Monica and Thenujah
		g.drawString("By: Monica and Thenujah", 900, 500);
		
		// Class and Teacher's Name
		g.drawString("Ms.Xie - ICS3U7", 900, 540);
		
		// Class and Teacher's Name
		g.drawString("January 4, 2022", 900, 580);
		
		//Implementing General Buttons
		mainMenuButton.render(g);
		exitButton.render(g);
	}

}

// TODO: Maybe add a fade-in animation?
