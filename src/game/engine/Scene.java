package game.engine;

import java.awt.Graphics2D;

/**
 * The Scene class is an abstract class all scenes need to extend.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public abstract class Scene {

	protected AppManager app;

    /**
	 * The constructor for the Scene class.
	 *
	 * @param app The AppManager of the App.
	 */
	public Scene(AppManager app) {
		this.app = app;
	}

	/**
	 * Executed each frame.
	 */
	public void update() {};

	/**
	 * Executed each frame.
	 */
	public void render(Graphics2D g) {};

}