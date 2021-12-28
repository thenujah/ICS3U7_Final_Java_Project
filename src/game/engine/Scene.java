package game.engine;

import java.util.ArrayList;

import game.engine.GameObject;

public abstract class Scene {

	protected AppManager app;
	public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	public Scene(AppManager app) {
		this.app = app;
	}

	public void update() {};
	public void render() {};

}