package game.engine.components;

import game.engine.util.Positioning;

public class Rect {

	public int x, y;
	public int width, height;
	public int top, bottom, left, right;
	public int[] topLeft, topRight, bottomLeft, bottomRight, center;

	public Rect(int x, int y, int width, int height) {
		this.update(x, y, width, height);
	}

	public void update(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		top = y;
		bottom = y + height;
		left = x;
		right = x + width;
		topLeft = new int[]{x, y};
		topRight = new int[]{x + width, y};
		bottomLeft = new int[]{x, y + height};
		bottomRight = new int[]{x + width, y + height};
		center = Positioning.averagePos(topLeft, bottomRight);
	}

	public String toString() {
		return String.format("<Rect points: (%d, %d) (%d, %d)>", 
			topLeft[0], topLeft[1], bottomLeft[0], bottomRight[1]);
	}

	public boolean contains(int[] point) {
		return bottomRight[0] >= point[0] && point[0] >= topLeft[0] 
			&& bottomRight[1] >= point[1] && point[1] >= topLeft[1];
	}

	public boolean isWithin(Rect rect) {
		return rect.contains(topLeft) && rect.contains(bottomRight);
	}

}