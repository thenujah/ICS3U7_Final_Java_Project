package game.engine.components;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.components.Rect;
import game.game_objects.Entity;

/**
 * @version 2.0
 * @since 2.0
 */
public class Collider {

	public Rect rect;
	public Rect sprite;

    /**
	 * The constructor for the Collider.
	 *
	 * @param rect The rect of the Entity that this Collider belongs to.
	 */
	public Collider(Rect rect) {
		sprite = rect;
		this.rect = new Rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

    /**
	 * @see #Collider(Rect)
	 */
	public Collider(int x, int y, int width, int height) {
		rect = new Rect(x, y, width, height);
	}

    /**
	 * A method which specifies the sprite the Collider belongs to.
	 */
	public void addSprite(Rect rect) { sprite = rect; }

	public boolean rightCollision(Rect rect) { 
		return this.rect.getRight() > rect.getLeft() && this.rect.getRight() < rect.getRight(); 
	}

	public boolean leftCollision(Rect rect) {
		return this.rect.getLeft() < rect.getRight() && this.rect.getLeft() > rect.getLeft(); 
	}

	public boolean topCollision(Rect rect) {
		return this.rect.getTop() < rect.getBottom() && this.rect.getTop() > rect.getTop(); 
	}

	public boolean bottomCollision(Rect rect) {
		return this.rect.getBottom() > rect.getTop() && this.rect.getBottom() < rect.getBottom(); 
	}

	public boolean collision(Rect rect) {
		return this.rect.overlaps(rect) || rect.overlaps(this.rect);
	}
	
	public ArrayList<Collider> getCollisions(ArrayList<Collider> colliders) {
		ArrayList<Collider> collisions = new ArrayList<>();
		for (Collider collider : colliders) {
			if (collision(collider.rect)) {
				collisions.add(collider);
			}
		}
		
		return collisions;
	}

	public ArrayList<Entity> getEntityCollisions(ArrayList<Entity> colliders) {
		ArrayList<Entity> collisions = new ArrayList<>();
		for (Entity entity : colliders) {
			if (collision(entity.getCollider().rect)) {
				collisions.add(entity);
			}
		}
		
		return collisions;
	}

	/**
	 * For barriers.
	 */
	public void xCollision(ArrayList<Collider> hits) {
		for (Collider instance : hits) {
			if (instance == this) continue;
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
	public void yCollision(ArrayList<Collider> hits) {
		for (Collider instance : hits) {
			if (instance == this) continue;
			Rect rect = instance.rect;

			if (topCollision(rect)) {
				this.rect.setTop(rect.getBottom());
				this.sprite.setTop(rect.getBottom());
			} else if (bottomCollision(rect)) {
				this.rect.setBottom(rect.getTop());
				this.sprite.setBottom(rect.getTop());
			}
		}
	}

	// TODO: Have the collider linked to the sprite by an offset.

	public void debug(Graphics2D g) {
		g.setColor(Color.blue);
		g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

}