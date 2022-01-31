package game.engine;

import javax.swing.JFrame;

/**
 * The class which creates the window for the game.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Window extends JFrame {

	public Window(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

}
