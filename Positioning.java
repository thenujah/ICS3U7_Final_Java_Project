package game.engine.util;

/**
 * A class with some methods used for positioning.
 * 
 * Might be deleted later.
 * 
 * @version 1.0
 * @since 1.0
 */
public class Positioning {
	
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 800;
	
	public static final int SCREEN_CENTER_X = SCREEN_WIDTH / 2;
	public static final int SCREEN_CENTER_Y = SCREEN_HEIGHT / 2;

	public static int findCenter(int pos, int length) {
		return pos + (length / 2);
	}

	public static int[] averagePos(int[] firstPos, int[] secondPos) {
		return new int[]{(firstPos[0] + secondPos[0]) / 2, (firstPos[1] + secondPos[1]) / 2};
	}
	
}