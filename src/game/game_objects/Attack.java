package game.game_objects;

import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.components.Rect;
import game.engine.util.Animation;
import game.engine.util.MouseInput;

public class Attack {

	Animation animation;
	Rect rect;
	Rect owner;
	String direction = "right";
	boolean isAttacking = false;
	
	public Attack(Rect owner) {
		this.owner = owner;
	}
	
	public void addAnimation(Animation animation) { 
		this.animation = animation;
		rect = animation.getRect();
	}

	public void update(String direction) {
		this.direction = direction;

		if (MouseInput.isClicked(1)) {
			isAttacking = true;
			animation.play();
		}

		int OFFSET = 5;

		switch (this.direction) {
			case "up" -> rect.setCenter(owner.getCenter()[0], owner.getTop() - OFFSET);
			case "down" -> rect.setCenter(owner.getCenter()[0], owner.getBottom() + OFFSET);
			case "left" -> {
				rect.rotate();
				rect.setCenter(owner.getLeft() - OFFSET, owner.getCenter()[1]);
			}
			case "right" -> {
				rect.rotate();
				rect.setCenter(owner.getRight() + OFFSET, owner.getCenter()[1]);
			}
		}
	}

	public void render(Graphics2D g, int[] translation, double scale) {
		animation.render(g, translation, scale);
	}

	public void debug(Graphics2D g, int[] translation, double scale) {
		g.setColor(Color.green);
		g.drawRect((int) (rect.getX() * scale - translation[0]), 
				   (int) (rect.getY() * scale - translation[1]),
				   (int) (rect.getWidth() * scale),
				   (int) (rect.getHeight() * scale));

		if (direction.equals("left") || direction.equals("right")) {
			rect.rotate();
		}
	}

}