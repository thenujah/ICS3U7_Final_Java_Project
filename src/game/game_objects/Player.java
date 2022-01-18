package game.game_objects;

import java.awt.Color;
import java.awt.*;
import java.awt.Graphics2D;

import game.engine.components.Rect;
import game.engine.util.KeyboardInput;

public class Player {	
	
	public Rect rect;

	private int borderRadius = 0;

	public Player() {
		
		rect = new Rect(20,80,67,98);     // Player

	}
	
	public void movement() {
		
		// User's Character/ Player
		// When player presses 'w', character will move up on the screen.
		if (KeyboardInput.isPressed("w")) {
			rect.setY(rect.getY() - 5);
			//rect1.setY(rect1.getY() - 3);
		}
		// When player presses 's' character will move down on the screen.
		if (KeyboardInput.isPressed("s")) {
			rect.setY(rect.getY() + 5);
			//rect1.setY(rect1.getY() + 3);
		}
		// When player presses 'a' character will move left on the screen.
		if (KeyboardInput.isPressed("a")) {
			rect.setX(rect.getX() - 5);
			//rect1.setX(rect1.getX() - 3);
		}
		// When player presses 'd' character will move right on the screen.
		if (KeyboardInput.isPressed("d")) {
			rect.setX(rect.getX() + 5);
			//rect1.setX(rect1.getX() + 3);
		}
		
		
	}
	
	
    
	public void render(Graphics2D g) {

		g.setColor(Color.blue);
		g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		
		//g.setColor(Color.red);
		//g.fillRect(rect1.getX(), rect1.getY(), rect1.getWidth(), rect1.getHeight());
	}

}
