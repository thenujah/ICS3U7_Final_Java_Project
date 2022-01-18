package game.engine.components;

import game.engine.components.Rect;

public class Collider {

	private Rect rect;

	public Collider(Rect rect) {
		this.rect = rect;
	}

	public boolean rightCollision(Rect rect) { return this.rect.getRight() > rect.x.getLeft(); }

	public boolean leftCollision(Rect rect) { return this.rect.getLeft() < rect.x.getRight(); }

	public boolean topCollision(Rect rect) { return this.rect.getTop() < rect.x.getBottom(); }

	public boolean bottomCollision(Rect rect) { return this.rect.getBottom() > rect.x.getTop(); }

	public boolean collision(Rect rect) {
		return rightCollision() || leftCollision() || topCollision() || bottomCollision();
	}

	/**
	 * For barriers.
	 */
	public void xCollision(Rect[] collisionGroup) {
		for (Rect instance : collisionGroup) {

			if (rightCollision(instance)) rect.setRight(instance.getLeft());
			else if (leftCollision(instance)) rect.setLeft(instance.getRight());

		}
	}

	/**
	 * For barriers.
	 */
	public void yCollision(Rect[] collisionGroup) {
		for (Rect instance : collisionGroup) {

			if (topCollision(instance)) rect.setTop(instance.getBottom());
			else if (bottomCollision(instance)) rect.setBottom(instance.getTop());

		}
	}

}