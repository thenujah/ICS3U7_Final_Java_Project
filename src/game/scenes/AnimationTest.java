package game.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.KeyboardInput;
import game.engine.util.Positioning;
import game.engine.util.Animation;
import game.engine.util.Camera;

/**
 * A class which controls the game scene of the game.
 *
 * @version 2.0
 * @since 1.0
 */
public class AnimationTest extends Scene {

	private Animation animation;
	private Camera camera;

	public AnimationTest(AppManager app) {
		super(app);

		animation = new Animation("./assets/swipe", 12);
		camera = new Camera(5);
	}

	public void update() {
		camera.update(animation.getRect());
	}

	public void render(Graphics2D g) {
		animation.render(g, camera.getTranslation(), camera.getScale());
	}

}