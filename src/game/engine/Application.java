package game.engine;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.RenderingHints;

import game.engine.AppManager;
import game.engine.util.KeyboardInput;
import game.engine.util.MouseInput;

/**
 * The class which draws stuff to the screen and contains the game loop.
 * 
 * @version 1.0
 * @since 1.0
 */
public class Application extends Canvas {

	private AppManager app;

	public Application() {
		app = new AppManager();

		MouseInput mouse = new MouseInput();

		this.addKeyListener(new KeyboardInput());
		this.addMouseMotionListener(mouse);
		this.addMouseListener(mouse);
		
	}

	/**
	 * Executed each frame.
	 */
	private void update() {
		app.update();
	}

	/**
	 * Executed each frame.
	 */
	private void render() {
		BufferStrategy buffStrat = this.getBufferStrategy();
		if (buffStrat == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) buffStrat.getDrawGraphics(); // Returns sun.java2d.SunGraphics2D
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);

		g.clearRect(0, 0, 1280, 800);
		app.render(g);

		g.dispose();
		buffStrat.show();
	}

	/**
	 * The game loop.
	 */
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

// Source: https://www.youtube.com/watch?v=1gir2R7G9ws
