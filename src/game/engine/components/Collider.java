package game.engine.components;

import game.engine.components.Rect;

public class Collider {

	private Rect rect;

	public Collider(Rect rect) {
		this.rect = rect;
	}

	public boolean rightCollision(Rect rect) { return this.rect.getRight() > rect.getLeft(); }

	public boolean leftCollision(Rect rect) { return this.rect.getLeft() < rect.getRight(); }

	public boolean topCollision(Rect rect) { return this.rect.getTop() < rect.getBottom(); }

	public boolean bottomCollision(Rect rect) { return this.rect.getBottom() > rect.getTop(); }

	public boolean collision(Rect rect) {
		return rightCollision(rect) || leftCollision(rect)
				|| topCollision(rect) || bottomCollision(rect);
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