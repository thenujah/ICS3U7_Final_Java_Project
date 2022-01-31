package game.engine.components;

import game.engine.util.Positioning;

/**
 * A class which creates objects to represent points on a 2D plane in the form of a rectangle. 
 * Game objects and various other renderable things can use this to aid in positioning, collisions,
 * and anything else.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Rect {

	private int x, y;
	private int width, height;
	private int top, bottom, left, right;
	private int[] topLeft, topRight, bottomLeft, bottomRight;
	private int[] midTop, midBottom, midLeft, midRight;
	private int[] center;

	/**
	 * The constructor for a new Rect object.
	 * 
	 * @param x The x position of the Rect.
	 * @param y The y position of the Rect.
	 * @param width The width of the Rect.
	 * @param height The height of the Rect.
	 */
	public Rect(int x, int y, int width, int height) { update(x, y, width, height); }

	/**
	 * The constructor for a new Rect object.
	 *
	 * @param width The width of the Rect.
	 * @param height The height of the Rect.
	 */
	public Rect(int width, int height) { update(0, 0, width, height); }

	/**
	 * A method which updates the positions of all the points on the rect as well as other 
	 * attributes.
	 * This method is used in all setters and the constructor.
	 * 
	 * @param x The x position of the Rect.
	 * @param y The y position of the Rect.
	 * @param width The width of the Rect.
	 * @param height The height of the Rect.
	 */
	public void update(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		top = y;
		bottom = y + height;
		left = x;
		right = x + width;

		topLeft = new int[]{left, top};
		topRight = new int[]{right, top};
		bottomLeft = new int[]{left, bottom};
		bottomRight = new int[]{right, bottom};

		midTop = new int[]{Positioning.findCenter(x, width), top};
		midBottom = new int[]{Positioning.findCenter(x, width), bottom};
		midLeft = new int[]{left, Positioning.findCenter(y, height)};
		midRight = new int[]{right, Positioning.findCenter(y, height)};

		center = Positioning.averagePos(topLeft, bottomRight);
	}

	/**
	 * A method which returns the string representation of a Rect.
	 *
	 * @return The string representation of the Rect.
	 */
	public String toString() {
		return String.format("<Rect points: (%d, %d) (%d, %d)>", 
			topLeft[0], topLeft[1], bottomRight[0], bottomRight[1]);
	}

	/**
	 * A method which rotates a Rect 90 degrees.
	 */
	public void rotate() {
		int[] center = getCenter();

		update(x, y, height, width);
		setCenter(center);
	}

	/**
	 * A method which checks if a point lies on the Rect.
	 * 
	 * @param x The x position of the point.
	 * @param y The y position of the point.
	 * @return A boolean representing if the instance contains the point.
	 */
	public boolean containsExclusive(int x, int y) {
		return right > x && x > left
			&& bottom > y && y > top;
	}

	/**
	 * @see #containsExclusive(int, int)
	 * @param point The point represented by an array.
	 */
	public boolean containsExclusive(int[] point) { return containsExclusive(point[0], point[1]); }

	/**
	 * A method which checks if a point lies on the Rect.
	 * 
	 * @param x The x position of the point.
	 * @param y The y position of the point.
	 * @return A boolean representing if the instance contains the point.
	 */
	public boolean contains(int x, int y) {
		return right >= x && x >= left
			&& bottom >= y && y >= top;
	}

	/**
	 * @see #contains(int, int)
	 * @param point The point represented by an array.
	 */
	public boolean contains(int[] point) { return contains(point[0], point[1]); }
	
	/**
	 * A method which checks if a rect is within another.
	 * 
	 * @param rect The rect which might contain the instance calling the method.
	 * @return A boolean representing if the rect contains the instance.
	 */
	public boolean isWithin(Rect rect) {
		return rect.contains(topLeft) && rect.contains(bottomRight);
	}

	/**
	 * A method which checks if one Rect overlaps another.
	 *
	 * @param rect The Rect that is possibly overlapping the current Rect instance.
	 * @return A boolean saying if the Rects are overlapping.
	 */
	public boolean overlaps(Rect rect) {
		return containsExclusive(rect.getTopLeft())
			|| containsExclusive(rect.getTopRight())
			|| containsExclusive(rect.getBottomLeft())
			|| containsExclusive(rect.getBottomRight())
			|| containsExclusive(rect.getMidTop())
			|| containsExclusive(rect.getMidBottom())
			|| containsExclusive(rect.getMidLeft())
			|| containsExclusive(rect.getMidRight());
	}

	/**
	 * A getter method for the top left x position of the Rect.
	 * 
	 * @return The x position.
	 */
	public int getX() { return x; }
	
	/**
	 * A getter method for the top left y position of the Rect.
	 * 
	 * @return The y position.
	 */
	public int getY() { return y; }

	/**
	 * A getter method for the width of the Rect.
	 * 
	 * @return The width.
	 */
	public int getWidth() { return width; }

	/**
	 * A getter method for the height of the Rect.
	 * 
	 * @return The height.
	 */
	public int getHeight() { return height; }

	/**
	 * A getter method for the top of the Rect.
	 * 
	 * @return The smallest y value in the Rect.
	 */
	public int getTop() { return top; }

	/**
	 * A getter method for the left side of the Rect.
	 * 
	 * @return The smallest x value in the Rect.
	 */
	public int getLeft() { return left; }

	/**
	 * A getter method for the bottom of the Rect.
	 * 
	 * @return The largest y value in the Rect.
	 */
	public int getBottom() { return bottom; }

	/**
	 * A getter method for the right side of the Rect.
	 * 
	 * @return The largest x value in the Rect.
	 */
	public int getRight() { return right; }

	/**
	 * A getter method for the coordinates of the top left corner of the Rect.
	 * 
	 * @return The coordinates of the top left corner.
	 */
	public int[] getTopLeft() { return topLeft; }

	/**
	 * A getter method for the coordinates of the top right corner of the Rect.
	 * 
	 * @return The coordinates of the top right corner.
	 */
	public int[] getTopRight() { return topRight; }

	/**
	 * A getter method for the coordinates of the bottom left corner of the Rect.
	 * 
	 * @return The coordinates of the bottom left corner.
	 */
	public int[] getBottomLeft() { return bottomLeft; }

	/**
	 * A getter method for the coordinates of the bottom right corner of the Rect.
	 * 
	 * @return The coordinates of the bottom left corner.
	 */
	public int[] getBottomRight() { return bottomRight; }

	/**
	 * A getter method for the coordinates of the center of the top side of the Rect.
	 * 
	 * @return The coordinates of the bottom right corner.
	 */
	public int[] getMidTop() { return midTop; }

	/**
	 * A getter method for the coordinates of the center of the bottom side of the Rect.
	 * 
	 * @return The coordinates of the bottom right corner.
	 */
	public int[] getMidBottom() { return midBottom; }

	/**
	 * A getter method for the coordinates of the center of the left side of the Rect.
	 * 
	 * @return The coordinates of the bottom right corner.
	 */
	public int[] getMidLeft() { return midLeft; }

	/**
	 * A getter method for the coordinates of the center of the right side of the Rect.
	 * 
	 * @return The coordinates of the bottom right corner.
	 */
	public int[] getMidRight() { return midRight; }

	/**
	 * A getter method for the coordinates of the center of the Rect.
	 * 
	 * @return The coordinates of the center.
	 */
	public int[] getCenter() { return center; }

	/**
	 * The setter method for the x coordinate of the Rect.
	 * 
	 * @param x The new x position for the top left corner.
	 */
	public void setX(int x) { update(x, y, width, height); }

	/**
	 * The setter method for the y coordinate of the Rect.
	 * 
	 * @param y The new y position for the top left corner.
	 */
	public void setY(int y) { update(x, y, width, height); }

	/**
	 * The setter method for the width of the Rect.
	 * 
	 * @param width The new width.
	 */
	public void setWidth(int width) { update(x, y, width, height); }

	/**
	 * The setter method for the height of the Rect.
	 * 
	 * @param height The new height.
	 */
	public void setHeight(int height) { update(x, y, width, height); }

	/**
	 * The setter method for the top of the Rect.
	 * Another name for setY().
	 * 
	 * @param y The y position for the top of the Rect.
	 */
	public void setTop(int y) { setY(y); }

	/**
	 * The setter method for the left side of the Rect.
	 * Another name for setX().
	 * 
	 * @param x The x position for the left side of the Rect.
	 */
	public void setLeft(int x) { setX(x); }

	/**
	 * The setter method for the bottom of the Rect.
	 * 
	 * @param y The y position for the bottom of the Rect.
	 */
	public void setBottom(int y) { update(x, this.y + (y - bottom), width, height); }

	/**
	 * The setter method for the right side of the Rect.
	 * 
	 * @param x The x position for the right side of the Rect.
	 */
	public void setRight(int x) { update(this.x + (x - right), y, width, height); }

	/**
	 * The setter method for the top left corner of the Rect.
	 * 
	 * @param x The new x position for the corner.
	 * @param y The new y position for the corner.
	 */
	public void setTopLeft(int x, int y) {
		int[] transform = { x - topLeft[0], y - topLeft[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setTopLeft(int, int)
	 * @param point The new position of the top left corner.
	 */
	public void setTopLeft(int[] point) { setTopLeft(point[0], point[1]); }

	/**
	 * The setter method for the top right corner of the Rect.
	 * 
	 * @param x The new x position for the corner.
	 * @param y The new y position for the corner.
	 */
	public void setTopRight(int x, int y) {
		int[] transform = { x - topRight[0], y - topRight[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setTopRight(int, int)
	 * @param point The new position of the top right corner.
	 */
	public void setTopRight(int[] point) { setTopRight(point[0], point[1]); }

	/**
	 * The setter method for the bottom left corner of the Rect.
	 * 
	 * @param x The new x position for the corner.
	 * @param y The new y position for the corner.
	 */
	public void setBottomLeft(int x, int y) {
		int[] transform = { x - bottomLeft[0], y - bottomLeft[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setBottomLeft(int, int)
	 * @param point The new position of the bottom left corner.
	 */
	public void setBottomLeft(int[] point) { setBottomLeft(point[0], point[1]); }

	/**
	 * The setter method for the bottom right corner of the Rect.
	 * 
	 * @param x The new x position for the corner.
	 * @param y The new y position for the corner.
	 */
	public void setBottomRight(int x, int y) {
		int[] transform = { x - bottomRight[0], y - bottomRight[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setBottomRight(int, int)
	 * @param point The new position of the bottom right corner.
	 */
	public void setBottomRight(int[] point) { setBottomRight(point[0], point[1]); }

	/**
	 * The setter method for the center of the top side of the Rect.
	 * 
	 * @param x The new x position for the point.
	 * @param y The new y position for the point.
	 */
	public void setMidTop(int x, int y) {
		int[] transform = { x - midTop[0], y - midTop[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setMidTop(int, int)
	 * @param point The new position of the center of the top side.
	 */
	public void setMidTop(int[] point) { setMidTop(point[0], point[1]); }

		/**
	 * The setter method for the center of the bottom side of the Rect.
	 * 
	 * @param x The new x position for the point.
	 * @param y The new y position for the point.
	 */
	public void setMidBottom(int x, int y) {
		int[] transform = { x - midBottom[0], y - midBottom[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setMidBottom(int, int)
	 * @param point The new position of the center of the bottom side.
	 */
	public void setMidBottom(int[] point) { setMidBottom(point[0], point[1]); }

		/**
	 * The setter method for the center of the left side of the Rect.
	 * 
	 * @param x The new x position for the point.
	 * @param y The new y position for the point.
	 */
	public void setMidLeft(int x, int y) {
		int[] transform = { x - midLeft[0], y - midLeft[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setMidLeft(int, int)
	 * @param point The new position of the center of the left side.
	 */
	public void setMidLeft(int[] point) { setMidLeft(point[0], point[1]); }

		/**
	 * The setter method for the center of the right side of the Rect.
	 * 
	 * @param x The new x position for the point.
	 * @param y The new y position for the point.
	 */
	public void setMidRight(int x, int y) {
		int[] transform = { x - midRight[0], y - midRight[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setMidRight(int, int)
	 * @param point The new position of the center of the right side.
	 */
	public void setMidRight(int[] point) { setMidRight(point[0], point[1]); }

	/**
	 * The setter method for the center of the Rect.
	 * 
	 * @param x The new x position for the center.
	 * @param y The new y position for the center.
	 */
	public void setCenter(int x, int y) {
		int[] transform = { x - center[0], y - center[1] };
		update(this.x + transform[0], this.y + transform[1], width, height);
	}

	/**
	 * @see #setCenter(int, int)
	 * @param point The new position of center.
	 */
	public void setCenter(int[] point) { setCenter(point[0], point[1]); }

}
