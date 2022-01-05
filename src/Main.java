import game.engine.Window;
import game.engine.Application;

/**
 * The class which runs the game.
 *
 * @version 1.0
 * @since 1.0
 */
class Main {
	public static void main(String[] args) {
		Window window = new Window(1280, 800, "Game");
		Application app = new Application();

		window.add(app);
		window.setVisible(true);
		app.start();
		
	}
}