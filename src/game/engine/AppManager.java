package game.engine;

import java.util.HashMap;

import game.engine.Scene;
import game.scenes.TitleScreen;
import game.scenes.Game;

public class AppManager {

	public String currentScene;
	private HashMap<String, Scene> scenes = new HashMap <String, Scene>();

	public AppManager() {
		scenes.put("title screen", new TitleScreen(this));
		scenes.put("game", new Game(this));

		currentScene = "game";
	}

	public void update() {
		scenes.get(currentScene).update();
	}

	public void render() {
		scenes.get(currentScene).render();
	}

}