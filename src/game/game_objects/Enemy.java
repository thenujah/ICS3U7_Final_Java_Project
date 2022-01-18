package game.game_objects;

import java.util.*;

import java.awt.Color;
import java.awt.*;
import java.awt.Graphics2D;

import game.engine.components.Rect;
import game.engine.util.KeyboardInput;


public class Enemy {
	
	public Rect enemy;
	
	int speed = 3;
	
	public Enemy() {
		
		enemy = new Rect(20,80,67,98);    // Enemy
		
	}
	
	
public void movement(int[] playerPosition) {
		
	//Positioning coordinates for enemy
		int x = playerPosition[0];
		int y = playerPosition[1];
		
		// Below is the code for chasing/ attacking the character
		if (x > enemy.getX()) {
			enemy.setX(enemy.getX() + speed);
			
			
		}
		
		else if (x < enemy.getX()) {
			enemy.setX(enemy.getX() - speed);
			
			
		}
		
		if (y > enemy.getY()) {
			enemy.setY(enemy.getY() + speed);
			
			
		}
	
		else if (y < enemy.getY()) {
			enemy.setY(enemy.getY() - speed);
			
			
		}

		
//		// User's Character/ Player
//		// When player presses 'w', character will move up on the screen.
//		if (KeyboardInput.isPressed("w")) {
//			enemy.setY(enemy.getY() - 3);
//		}
//		// When player presses 's' character will move down on the screen.
//		if (KeyboardInput.isPressed("s")) {
//			enemy.setY(enemy.getY() + 3);
//		}
//		// When player presses 'a' character will move left on the screen.
//		if (KeyboardInput.isPressed("a")) {
//			enemy.setX(enemy.getX() - 3);
//		}
//		// When player presses 'd' character will move right on the screen.
//		if (KeyboardInput.isPressed("d")) {
//			enemy.setX(enemy.getX() + 3);
//		}
		
	
		
	}	


public void update() {
	
	
}

public void render(Graphics2D g) {
	
	g.setColor(Color.red);
	g.fillRect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
}

}

