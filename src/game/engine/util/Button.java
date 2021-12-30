package game.engine.util;

import java.awt.Graphics;
import java.awt.Color;

import game.engine.components.Rect;
import game.engine.util.MouseInput;

public class Button {

	private Rect rect;
	private Color color = new Color(0, 0, 0);
	private String text = "";
	
	public Button(int x, int y, int width, int height) {
		rect = new Rect(x, y, width, height);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.drawString(text, rect.x, rect.bottom);
	}

	public boolean isClicked() {
		int[] mousePosition = { MouseInput.getX(), MouseInput.getY() };

		if (MouseInput.isPressed(1) && rect.contains(mousePosition)) {
			return true; 
		} else {
			return false;
		}
	}

}