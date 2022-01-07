package game.scenes;

import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.MouseInput;
import game.gameObjects.Player;

/**
 * A class which controls the game scene of the game.
 *
 * @version 1.0
 * @since 1.0
 */
public class Game extends Scene {

	public Player play;

	public Game(AppManager app) {
		super(app);
		
		play = new Player();
		
	}

	public void update() {
		play.movement();
	}

	public void render(Graphics2D g) {
		play.render(g);

	}
}