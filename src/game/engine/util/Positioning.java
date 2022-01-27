package game.engine.util;

import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

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

	public static List<String> directions = Collections.unmodifiableList(
		Arrays.asList("up", "down", "left", "right")
	);

	public static final Map<String, String> oppositeDirections = Map.of(
		"up", "down", 
		"down", "up",
		"left", "right",
		"right", "left"
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