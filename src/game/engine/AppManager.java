package game.engine;

import java.util.HashMap;
import java.awt.Graphics;

import game.engine.Scene;
import game.engine.AppManager;
import game.scenes.TitleScreen;
import game.scenes.Game;
import game.scenes.MainMenu;

public class AppManager {

	public String currentScene;
	private HashMap<String, Scene> scenes = new HashMap <String, Scene>();

	public AppManager() {
		scenes.put("title screen", new TitleScreen(this));
		scenes.put("main menu", new MainMenu(this));
		scenes.put("game", new Game(this));

		currentScene = "game";
	}

	public void update() {
		scenes.get(currentScene).update();
	}

	public void render(Graphics g) {
		scenes.get(currentScene).render(g);
	}

}