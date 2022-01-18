package game.engine;

import java.util.HashMap;
import java.awt.Graphics2D;

import game.engine.Scene;
import game.scenes.*;

/**
 * A class which handles the changing of the scenes.
 * 
 * @version 1.0
 * @since 1.0
 */
public class AppManager {

	public String currentScene;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

	public AppManager() {
		scenes.put("title screen", new TitleScreen(this));
		scenes.put("main menu", new MainMenu(this));
		scenes.put("game", new Game(this));
		scenes.put("instructions", new InstructionsPage(this));
		scenes.put("test", new Test(this));

		currentScene = "title screen";
	}

	/**
	 * Executed each frame.
	 */
	public void update() {
		scenes.get(currentScene).update();
	}

	/**
	 * Executed each frame.
	 */
	public void render(Graphics2D g) {
		scenes.get(currentScene).render(g);
	}

}