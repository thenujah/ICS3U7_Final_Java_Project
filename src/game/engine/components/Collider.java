package game.engine.components;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.components.Rect;

public class Collider {

	public Rect rect;
	public Rect sprite;

	public Collider(Rect rect) {
		sprite = rect;
		this.rect = new Rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

	public Collider(int x, int y, int width, int height) {
		rect = new Rect(x, y, width, height);
	}

	public void addSprite(Rect rect) { sprite = rect; }

	public boolean rightCollision(Rect rect) { return this.rect.getRight() > rect.getLeft(); }

	public boolean leftCollision(Rect rect) { return this.rect.getLeft() < rect.getRight(); }

	public boolean topCollision(Rect rect) { return this.rect.getTop() < rect.getBottom(); }

	public boolean bottomCollision(Rect rect) { return this.rect.getBottom() > rect.getTop(); }

	public boolean collision(Rect rect) {
		return this.rect.overlaps(rect) || rect.overlaps(this.rect);
	}

	/**
	 * For barriers.
	 */
	public void xCollision(ArrayList<Collider> collisionGroup) {
		for (Collider instance : collisionGroup) {
			Rect rect = instance.rect;

			if (rightCollision(rect)) { 
				this.rect.setRight(rect.getLeft());
				this.sprite.setRight(rect.getLeft());
			} else if (leftCollision(rect)) {
				this.rect.setLeft(rect.getRight());
				this.sprite.setLeft(rect.getRight());
			}

		}
	}

	/**
	 * For barriers.
	 */
	public void yCollision(ArrayList<Collider> collisionGroup) {
		for (Collider instance : collisionGroup) {
			Rect rect = instance.rect;

			if (topCollision(rect)) 
				this.rect.setTop(rect.getBottom());
			else if (bottomCollision(rect)) 
				this.rect.setBottom(rect.getTop());

		}
	}

	public void debug(Graphics2D g) {
		g.setColor(Color.blue);
		g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

}