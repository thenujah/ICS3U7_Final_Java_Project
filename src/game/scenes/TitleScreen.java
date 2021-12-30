package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.engine.AppManager;
import game.engine.Scene;

public class TitleScreen extends Scene {

	public TitleScreen(AppManager app) {
		super(app);
		
		
		
		
		
	}

	public void render(Graphics g) {
		
		// Title of the game formatting:'Winter Wonderland'
		Font font = new Font("Verdana", Font.BOLD, 50);
	    g.setFont(font);
	    g.setColor(Color.black);
		g.drawString("Winter Wonderland", 380, 300);
		
		// Formatting of names, date and class
		
		// Names: Monica and Thenujah
		Font font2 = new Font("Verdana", Font.PLAIN, 20);
	    g.setFont(font2);
	    g.setColor(Color.black);
		g.drawString("By: Monica and Thenujah", 900, 500);
		
		// Class and Teacher's Name
		Font font3 = new Font("Verdana", Font.PLAIN, 20);
	    g.setFont(font3);
	    g.setColor(Color.black);
		g.drawString("M.Xie - ICS3U7", 900, 540);
		
		// Class and Teacher's Name
		Font font4 = new Font("Verdana", Font.PLAIN, 20);
	    g.setFont(font4);
	    g.setColor(Color.black);
		g.drawString("January 4, 2022", 900, 580);
	}

}