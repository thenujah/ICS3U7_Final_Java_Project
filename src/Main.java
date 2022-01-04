import game.engine.Window;
import game.engine.Application;

class Main {
	public static void main(String[] args) {
		Window window = new Window(1280, 800, "Game");
		Application app = new Application();

		window.add(app);
		window.setVisible(true);
		app.start();
		
	}
}