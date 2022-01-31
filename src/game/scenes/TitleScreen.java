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

/**
 * A class which controls the title screen scene of the game.
 * 
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class TitleScreen extends Scene {

	private Color BLACK = new Color(40, 38, 48);
	private Color BLUE = new Color(27, 21, 219);
	private Color LIGHT_BLUE = new Color(135, 220, 255);
	private Color WHITE = new Color(230, 240, 238);

	private final Font titleFont;
	private final Font subtitleFont;

	private final Button mainMenuButton;
	private final Button exitButton;
    private BufferedImage titleScreenBackground;

	/**
	 * The constructor for the title screen.
	 *
	 * @param app The AppManager that controls the app.
	 */
	public TitleScreen(AppManager app) {
		super(app);

		titleFont = new Font("Serif", Font.BOLD, 60);
		subtitleFont = new Font("DialogInput", Font.PLAIN, 20);
		
		// Main Menu Button
		mainMenuButton = new Button(150, 50);
		mainMenuButton.setCenter(650, 250);
		mainMenuButton.backgroundColor = LIGHT_BLUE;
		mainMenuButton.font = subtitleFont;
		mainMenuButton.text = "Main Menu";
		
		// Quit Game Button
		exitButton = new Button(150, 50);
		exitButton.setCenter(650, 320);
		exitButton.backgroundColor = LIGHT_BLUE;
		exitButton.font = subtitleFont;
		exitButton.text = "Quit Game";

		try {
            titleScreenBackground = ImageIO.read(new File("./assets/TitleScreen_Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * A method which updates the state of the title screen.
	 */
	public void update() {
		if (mainMenuButton.isClicked()) {
			app.currentScene = "main menu";
		}
		
		if (exitButton.isClicked()) {
			System.exit(0);
		}

	}

	/**
	 * A method which renders the main menu.
	 *
	 * @param g The Graphics2D object used to draw images to the screen.
	 */
	public void render(Graphics2D g) {
		AffineTransform titleTransform = new AffineTransform();
		titleTransform.translate(-170, 0);
		titleTransform.scale(1.3, 1.3);

		g.drawImage(titleScreenBackground, titleTransform, null);

		// Title
		g.setFont(titleFont);
		g.setColor(WHITE);
		g.drawString("Winter Wonderland", 390, 200);

		// Formatting of names, date and class
		g.setFont(subtitleFont);

		g.drawString("By: Monica and Thenujah", 1000, 500);
		g.drawString("Ms.Xie - ICS3U7", 1000, 540);
		g.drawString("January 4, 2022", 1000, 580);

		mainMenuButton.render(g);
		exitButton.render(g);
		
	}

}
