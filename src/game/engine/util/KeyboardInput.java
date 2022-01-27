package game.engine.util;

import java.util.HashMap;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A class used to handle keyboard input.
 * 
 * @version 1.0
 * @since 1.0
 */
public class KeyboardInput extends KeyAdapter {

	private static HashMap<String, Boolean> keysPressed = new HashMap<String, Boolean>();

	/**
	 * The constructor is only used to initalize the HashMap which will store the states of each 
	 * key.
	 */
	public KeyboardInput() {
		String[] keys = { "w", "a", "s", "d", "space", "esc" };
		for (String key : keys) keysPressed.put(key, false);
	}

	/**
	 * The method which will be called upon whenever a key is pressed. It will take the code of
	 * the key pressed and set it to true in the HashMap.
	 * 
	 * @param e The KeyEvent.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
			case 27 -> keysPressed.put("esc", true);
			case 32 -> keysPressed.put("space", true);
			case 65 -> keysPressed.put("a", true);
			case 68 -> keysPressed.put("d", true);
			case 83 -> keysPressed.put("s", true);
			case 87 -> keysPressed.put("w", true);
		}
	}

	/**
	 * The method which will be called upon whenever a key is released. It will take the code of
	 * the key pressed and set it to false in the HashMap.
	 * 
	 * @param e The KeyEvent.
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
			case 27 -> keysPressed.put("esc", true);
			case 32 -> keysPressed.put("space", false);
			case 65 -> keysPressed.put("a", false);
			case 68 -> keysPressed.put("d", false);
			case 83 -> keysPressed.put("s", false);
			case 87 -> keysPressed.put("w", false);
		}
	}

	/**
	 * A method which returns if a key is currently being pressed on.
	 * 
	 * @param key The specified key.
	 * @return A boolean representing if the specified key is pressed.
	 */
	public static boolean isPressed(String key) {
		return keysPressed.get(key);
	}

}