package game.engine.util;

import java.util.Collections;
import java.util.Arrays;
import java.util.Map;

import game.game_objects.TileMap;

/**
 * A class with some methods and variables used for positioning.
 * 
 * @version 2.0
 * @since 1.0
 */
public class Positioning {
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 800;
	
	public static final int SCREEN_CENTER_X = SCREEN_WIDTH / 2;
	public static final int SCREEN_CENTER_Y = SCREEN_HEIGHT / 2;

	public static final int TILE_SIZE = 32;

	public enum Direction { 
		UP(0), DOWN(180), RIGHT(90), LEFT(270); 

		private int rotation;

		Direction(int rotation) {
			this.rotation = rotation;
		}

		public int rotation() { return rotation; }
	}
	
	public static Direction[] directions = Direction.values();
	public static final Map<Direction, Direction> oppositeDirections = Map.of(
		Direction.UP, Direction.DOWN, 
		Direction.DOWN, Direction.UP,
		Direction.LEFT, Direction.RIGHT,
		Direction.RIGHT, Direction.LEFT
	);

	/**
	 * A method which finds the center of a line.
	 * 
	 * @param pos The position of the line on an axis.
	 * @param length The length of the line along that axis.
	 * 
	 * @return The position of the center of the line.
	 */
	public static int findCenter(int pos, int length) {
		return pos + (length / 2);
	}

	/**
	 * A method which calculates the center position of two points.
	 * 
	 * @param firstPos The first position.
	 * @param secondPos The second position.
	 * 
	 * @return The mid point of the two specified points.
	 */
	public static int[] averagePos(int[] firstPos, int[] secondPos) {
		return new int[]{(firstPos[0] + secondPos[0]) / 2, (firstPos[1] + secondPos[1]) / 2};
	}

	/**
	 * A method which clamps a value between two bounds.
	 * 
	 * @param value The value to be clamped.
	 * @param upperBound The highest value the specified value should have.
	 * @param lowerBound The lowest value the specified value should have.
	 * @return A value between the two specified bounds.
	 */
	public static int clamp(int value, int upperBound, int lowerBound) {
		if (value > upperBound) {
			return upperBound;
		} else if (value < lowerBound) {
			return lowerBound;
		} else {
			return value;
		}
	}

	/**
	 * @see #clamp(int, int, int)
	 */
	public static double clamp(double value, double upperBound, double lowerBound) {
		if (value > upperBound) {
			return upperBound;
		} else if (value < lowerBound) {
			return lowerBound;
		} else {
			return value;
		}
	}

	/**
	 * A method to generate a random position that is within a tilemap.
	 * 
	 * @param tilemap The TileMap that the position must be within.
	 * 
	 * @return The position generated.
	 */
	public static int[] generateRandomPositionWithin(TileMap tilemap) {
		int[] position = new int[2];

		while (true) {
			position[0] = (int) (Math.random() * tilemap.rect.getRight()) + tilemap.rect.getLeft();
			position[1] = (int) (Math.random() * tilemap.rect.getBottom()) + tilemap.rect.getTop();

			if (tilemap.contains(new int[] { position[0] / TILE_SIZE, position[1] / TILE_SIZE })) {
				return position;
			}
		}
	}
	
}