package game.engine.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A class used to handle mouse input.
 * 
 * @version 1.1
 * @since 1.0
 */
public class MouseInput extends MouseAdapter {
	
	private static int x, y;

	private static boolean button1Pressed = false;
	private static boolean button2Pressed = false;
	private static boolean button3Pressed = false;

	private static boolean button1Clicked = false;
	private static boolean button2Clicked = false;
	private static boolean button3Clicked = false;

	/**
	 * The method which will be called upon whenever a mouse button is pressed. It will set the
	 * corresponding variable to the pressed mouse button to true.
	 * 
	 * @param e The MouseEvent.
	 */
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			button1Pressed = true;
			button1Clicked = true;
			break;
		case MouseEvent.BUTTON2:
			button2Pressed = true;
			button2Clicked = true;
			break;
		case MouseEvent.BUTTON3:
			button3Pressed = true;
			button3Clicked = true;
			break;
		}
	}

	/**
	 * The method which will be called upon whenever a mouse button is released. It will set the
	 * corresponding variable to the pressed mouse button to false.
	 * 
	 * @param e The MouseEvent.
	 */
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			button1Pressed = false;
			break;
		case MouseEvent.BUTTON2:
			button2Pressed = false;
			break;
		case MouseEvent.BUTTON3:
			button3Pressed = false;
			break;
		}
	}

	/**
	 * The method which will be called upon whenever the mouse is dragged. It will get the position
	 * of the mouse while it's dragged.
	 * 
	 * @param e The MouseEvent.
	 */
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	/**
	 * The method which will be called upon whenever the mouse is moved. It will get the position
	 * of the mouse while it's moving.
	 * 
	 * @param e The MouseEvent.
	 */
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void reset() {
		button1Clicked = false;
		button2Clicked = false;
		button3Clicked = false;
	}

	/**
	 * 
	 */
	public static boolean isPressed(int button) {
		switch (button) {
		case 1:
			return button1Pressed;
		case 2:
			return button2Pressed;
		case 3:
			return button3Pressed;
		}
		return false;
	}

	/**
	 *
	 */
	public static boolean isClicked(int button) {
		switch (button) {
			case 1:
				return button1Clicked;
			case 2:
				return button2Clicked;
			case 3:
				return button3Clicked;
		}
		return false;
	}

	public static int getX() { return x; }
	public static int getY() { return y; }

}
