package game.engine.util;

public class Positioning {

	public static int findCenter(int pos, int length) {
		return pos + (length / 2);
	}

	public static int[] averagePos(int[] firstPos, int[] secondPos) {
		return new int[]{(firstPos[0] + secondPos[0]) / 2, (firstPos[1] + secondPos[1]) / 2};
	}
}