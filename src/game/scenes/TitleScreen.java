package game.scenes;

import java.awt.Graphics;

import game.engine.AppManager;
import game.engine.Scene;

public class TitleScreen extends Scene {

	public TitleScreen(AppManager app) {
		super(app);
	}

	public void render(Graphics g) {
		g.drawString("title", 200, 200);
	}

}