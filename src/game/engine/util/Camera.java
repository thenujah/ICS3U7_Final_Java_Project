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
	private double scale;

	public Camera(double scale) {
		this.scale = scale;
	}

	/**
	 * A method which updates the offset of the camera.
	 * 
	 * @param focus The rect which will be the followed by the camera.
	 */
	public void update(Rect focus) {
		offsets[0] += (focus.getCenter()[0] * scale - offsets[0] - Positioning.SCREEN_CENTER_X) / lag;
		offsets[1] += (focus.getCenter()[1] * scale - offsets[1] - Positioning.SCREEN_CENTER_Y) / lag;
	}

	/**
	 * A getter method for the scale.
	 * 
	 * @return The amount the graphics need to be offset.
	 */
	public int[] getTranslation() { return new int[]{ (int) offsets[0], (int) offsets[1] }; }

	/**
	 * A getter method for the scale.
	 * 
	 * @return The scale of the camera.
	 */
	public double getScale() { return scale; }

}