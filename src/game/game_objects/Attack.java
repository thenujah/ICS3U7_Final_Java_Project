package game.game_objects;

import java.awt.Graphics2D;

import game.engine.components.Rect;
import game.engine.util.Animation;
import game.engine.util.MouseInput;

public class Attack {

	Animation animation;
	Rect rect;
	Rect owner;
	String direction = "up";
	boolean isAttacking = false;
	
	public Attack(Rect owner) {
		this.owner = owner;
	}
	
	public void addAnimation(Animation animation) { 
		this.animation = animation;
		rect = animation.getRect();
	}

	public void update(int x, int y) {
		if (MouseInput.isClicked(1)) {
			isAttacking = true;
			animation.play();
		}
		// System.out.println(x);

		rect.setTopLeft(x - 10, y - 15); // TODO: Remove the magic values.
	}

	public void render(Graphics2D g, int[] translation, double scale) {
		// System.out.println(rect.getY());
		animation.render(g, translation, scale);
	}

}