package game.engine;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.RenderingHints;

import game.engine.util.KeyboardInput;
import game.engine.util.MouseInput;

/**
 * The class which draws stuff to the screen and contains the game loop.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Application extends Canvas {

	private AppManager app;

	private final MouseInput mouse = new MouseInput();
	private final KeyboardInput keyboard = new KeyboardInput();

	/**
	 * The constructor for the Application. It adds all the listeners needed for the app.
	 */
	public Application() {
		app = new AppManager();

		this.addKeyListener(keyboard);
		this.addMouseMotionListener(mouse);
		this.addMouseListener(mouse);
	}

	/**
	 * A method executed each frame used to update the states of all the objects.
	 */
	private void update() { app.update(); }

	/**
	 * A method executed each frame used to render all the objects in the game.
	 */
	private void render() {
		BufferStrategy buffStrat = this.getBufferStrategy();
		if (buffStrat == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) buffStrat.getDrawGraphics();
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
						System.out.println("FPS - " + frames);
						time += 1000;
						frames = 0;
				}

				mouse.reset();
				keyboard.reset();
			}
		}
	}

}

// Source: https://www.youtube.com/watch?v=1gir2R7G9ws
