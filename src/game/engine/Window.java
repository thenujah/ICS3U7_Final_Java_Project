package game.engine;

import javax.swing.JFrame;

public class Window extends JFrame {

	public Window(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

}
