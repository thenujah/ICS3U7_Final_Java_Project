package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;
import game.engine.util.KeyboardInput;

/**
 * A class which controls the title screen scene of the game.
 * 
 * @version 1.0
 * @since 1.0
 */
public class TitleScreen extends Scene {

	private final Color BLACK = new Color(40, 38, 48);
	private final Color BLUE = new Color(75, 80, 105);
	private final Color LIGHT_BLUE = new Color(160, 190, 200);
	private final Color WHITE = new Color(230, 240, 238);

	private final Font titleFont;
	private final Font subtitleFont;

	private final Button mainMenuButton;
	private final Button exitButton;

	public TitleScreen(AppManager app) {
		super(app);

		titleFont = new Font("Serif", Font.BOLD, 60);
		subtitleFont = new Font("DialogInput", Font.PLAIN, 20);
		
		// Main Menu Button
		mainMenuButton = new Button(150, 50);
		mainMenuButton.setCenter(625, 350);
		mainMenuButton.backgroundColor = BLUE;
		mainMenuButton.font = subtitleFont;
		mainMenuButton.text = "Main Menu";
		
		// Quit Game Button
		exitButton = new Button(625, 420, 150, 50);
		exitButton.text = "Quit Game";
	}

	public void update() {
		if (mainMenuButton.isClicked()) {
			app.currentScene = "main menu";
		}

		if (KeyboardInput.isPressed("space")) {
			System.out.println(true);
		}
	}

	public void render(Graphics2D g) {
		g.setColor(WHITE);
		g.fillRect(0, 0, 1280, 800);

		// Title
	    g.setFont(titleFont);
	    g.setColor(BLACK);
		g.drawString("Winter Wonderland", 410, 300);
		
		// Formatting of names, date and class
	    g.setFont(subtitleFont);

		g.drawString("By: Monica and Thenujah", 900, 500);
		g.drawString("Ms.Xie - ICS3U7", 900, 540);
		g.drawString("January 4, 2022", 900, 580);

		mainMenuButton.render(g);
		exitButton.render(g);
	}

}

// TODO: Maybe add a fade-in animation?
