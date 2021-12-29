package game.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game.engine.AppManager;
import game.engine.util.KeyboardInput;
import game.engine.util.MouseInput;

public class Application extends Canvas {

	private AppManager app;

	public Application() {
		app = new AppManager();

		MouseInput mouse = new MouseInput();

		this.addKeyListener(new KeyboardInput());
		this.addMouseMotionListener(mouse);
		this.addMouseListener(mouse);
		
	}

	// Executed each frame.
	private void update() {
		app.update();
	}

	// Executed each frame.
	private void render() {
		BufferStrategy buffStrat = this.getBufferStrategy();
		if (buffStrat == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffStrat.getDrawGraphics();

		app.render();

		g.dispose();
		buffStrat.show();
	}

	// Notch's Game Loop :D
	public void start() {

		long lastime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double time = System.currentTimeMillis();
		
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastime) / ns;
			lastime = now;
			
			if (delta >= 1) {
				update();
				render();
				
				delta--;
				frames++;

				if (System.currentTimeMillis() - time >= 1000) {
						System.out.println("FPS: " + frames + " | Current scene: " + app.currentScene);
						time += 1000;
						frames = 0;
				}
			}
		}
	}

}

// edit
