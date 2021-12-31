package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;

public class TitleScreen extends Scene {

	private Button button;
	private Button button2;

	

	public TitleScreen(AppManager app) {
		super(app);
		
		// Main Menu Button
		button = new Button(625, 350, 150, 50);
		button.setText("Main Menu");
		
		// Quit Game Button
		button2 = new Button(625, 400, 150, 50);
		button2.setText("Quit Game");
	}

	public void update() {
		if (button.isClicked()) {
			app.currentScene = "main menu";
		}
	}

	public void render(Graphics g) {
		// Title of the game formatting:'Winter Wonderland'
		Font font = new Font("Serif", Font.BOLD, 60);
	    g.setFont(font);
	    g.setColor(Color.black);
		g.drawString("Winter Wonderland", 410, 300);
		
		// Formatting of names, date and class
		
		// Names: Monica and Thenujah
		Font font2 = new Font("DialogInput", Font.PLAIN, 20);
	    g.setFont(font2);
	    g.setColor(Color.black);
		g.drawString("By: Monica and Thenujah", 900, 500);
		
		// Class and Teacher's Name
		Font font3 = new Font("DialogInput", Font.PLAIN, 20);
	    g.setFont(font3);
	    g.setColor(Color.black);
		g.drawString("Ms.Xie - ICS3U7", 900, 540);
		
		// Class and Teacher's Name
		Font font4 = new Font("DialogInput", Font.PLAIN, 20);
	    g.setFont(font4);
	    g.setColor(Color.black);
		g.drawString("January 4, 2022", 900, 580);
		
		//Implementing General Buttons
		button.render(g);
		button2.render(g);
	}

}