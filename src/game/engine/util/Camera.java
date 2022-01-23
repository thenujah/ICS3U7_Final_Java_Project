package game.engine.util;

import game.engine.util.Positioning;
import game.engine.components.Rect;
import game.game_objects.Map;

/**
 * A class which handles scaling and translating the visuals of the game.
 */
public class Camera {

	private final int lag = 25;
	
	private double[] offsets = new double[2];
	private int xTranslation = 0;
	private int yTranslation = 0;
	private double scale = 1;
	private Map level;

	public Camera(double scale) {
		this.scale = scale;
	}

	public void update(Rect rect) {
		offsets[0] += (rect.getX() * scale - offsets[0] - Positioning.SCREEN_CENTER_X + rect.getWidth() * scale)
					  / lag;

		offsets[1] += (rect.getY() * scale - offsets[1] - Positioning.SCREEN_CENTER_Y + rect.getHeight() * scale)
					  / lag;
	}

	public int[] getTranslation() { return new int[]{ (int) offsets[0], (int) offsets[1] }; }

	public double getScale() { return scale; }

	/**
	 * A setter method for the current map of the game.
	 * 
	 * @param level The map which contains the tilemaps.
	 */
	public void setMap(Map level) { this.level = level; }

}