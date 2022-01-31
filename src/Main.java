import game.engine.Window;
import game.engine.Application;
import game.engine.util.Positioning;

/**
 * The class which runs the game.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
class Main {
	public static void main(String[] args) {
		Window window = new Window(Positioning.SCREEN_WIDTH, Positioning.SCREEN_HEIGHT, "Game");
		Application app = new Application();

		window.add(app);
		window.setVisible(true);
		app.start();
		
	}
}