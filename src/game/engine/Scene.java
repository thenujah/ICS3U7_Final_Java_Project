package game.engine;

import java.awt.Graphics2D;

/**
 * The Scene class is an abstract class all scenes need to extend.
 * 
 * @version 1.0
 * @since 1.0
 */
public abstract class Scene {

	protected AppManager app;

    /**
	 * The constructor for the Scene class.
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