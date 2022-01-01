package game.engine.util;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import game.engine.components.Rect;
import game.engine.util.MouseInput;

public class Button {

	public Rect rect;
	private Rect textRect;
	private String text = "";
	private Font font;
	private Color backgroundColor = new Color(0, 0, 0);
	private Color fontColor = new Color(0, 0, 0);
	private Color borderColor = new Color(0, 0, 0);
	private boolean hasBorder = false;
	private int borderWidth = 0;
	private int borderRadius = 0;
	
	public Button(int x, int y, int width, int height) {
		rect = new Rect(x, y, width, height);
	}

	public Button(int width, int height) {
		this(0, 0, width, height);
	}

	public void setFont(Font font) { this.font = font; }
	public void setText(String text) { this.text = text; }
	public void setBackgroundColor(Color color) { this.backgroundColor = color; }
	public void setFontColor(Color color) { this.fontColor = color; }
	public void setBorderRadius(int radius) { if (radius > 0) borderRadius = radius; }
	public void setCenter(int x, int y) { rect.setCenter(x, y); }

	private void setTextRect(Graphics2D g) {
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getAscent();

		textRect = new Rect(0, 0, width, height);
		textRect.setCenter(rect.getCenter());
	}

	public void setBorder(int width, Color color) {
		hasBorder = true;
		if (width > 0) borderWidth = width;
		borderColor = color;
	}

	public void render(Graphics2D g) {

		// Background
		g.setColor(backgroundColor);
		if (borderRadius > 0) {
			g.fillRoundRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), 
				borderRadius * 2, borderRadius * 2);
		} else {
			g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}

		// Border
		if (hasBorder) {
			g.setColor(borderColor);
			g.setStroke(new BasicStroke(borderWidth));

			if (borderRadius > 0) {
				g.drawRoundRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), 
					borderRadius * 2, borderRadius * 2);
			} else {
				g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
			}
		}

		// Text
		if (!text.isEmpty() && font != null) {
			g.setColor(fontColor);
			g.setFont(font);
			setTextRect(g);
			g.drawString(text, textRect.getX(), textRect.getBottom() - textRect.getHeight() / 6);
		}

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