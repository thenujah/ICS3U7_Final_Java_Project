package game.scenes;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.MouseInput;

public class Game extends Scene {

	public Game(AppManager app) {
		super(app);
	}

	public void update() {
		int x = MouseInput.getX();
		int y = MouseInput.getY();

		if (MouseInput.isPressed(1)){
			System.out.println("Mouse pressed.");
			app.currentScene = "title screen";
		}
	}

}