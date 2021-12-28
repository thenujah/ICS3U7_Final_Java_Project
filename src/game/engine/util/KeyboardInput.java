package game.engine.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends KeyAdapter {

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println(key);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}

}