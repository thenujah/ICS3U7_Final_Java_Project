package game.engine.util;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.engine.components.Rect;

/**
 * A class which creates buttons.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Button {

    public Rect rect;
    private Rect textRect;
    public String text = "";
    public Font font;
    private BufferedImage image;
    public Color backgroundColor = new Color(0, 0, 0);
    public Color fontColor = new Color(0, 0, 0);
    public Color borderColor = new Color(0, 0, 0);
    private boolean hasBorder = false;
    private int borderWidth = 0;
    private int borderRadius = 0;
    
    /**
     * The constructor which initializes the rect of the button.
     * 
     * @param x The x position of the button.
     * @param y The y position of the button.
     * @param width The width of the button.
     * @param height The height of the button.
     */
    public Button(int x, int y, int width, int height) { rect = new Rect(x, y, width, height); }

    /**
     * An alternate constructor for the Button class which doesn't include a position.
     * 
     * @see #Button(int, int, int, int)
     */
    public Button(int width, int height) { this(0, 0, width, height); }

    /**
     * An alternate constructor for the Button class which allows you to add an image.
     * 
     * @param path The path to the image.
     */
    public Button(String path, double scale) { 
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rect = new Rect(0, 0, (int) (image.getWidth() * scale),
                       (int) (image.getHeight() * scale));
    }

    /**
     * A setter method for the image of the button.
     */
    public void setImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A setter method for the border radius.
     * 
     * @param radius The new value for the radius.
     * 
     * @throws IllegalArgumentException if the radius is less than 0.
     */
    public void setBorderRadius(int radius) throws IllegalArgumentException { 
        if (radius >= 0) borderRadius = radius;
        else throw new IllegalArgumentException("button radius too small");
    }

    /**
     * A setter method for the border of the button.
     * 
     * @param width The width of the border.
     * @param color The color of the border.
     * 
     * @throws IllegalArgumentException if the width is less than 0.
     */
    public void setBorder(int width, Color color) throws IllegalArgumentException {
        hasBorder = true;
        if (width >= 0) borderWidth = width;
        else throw new IllegalArgumentException("border width too small");
        borderColor = color;
    }

    /**
     * A setter method to set the center of the button. This can be done by using the rect of the
     * button directly, but this is a shortcut.
     * 
     * @param x The new x position.
     * @param y The new y position.
     */
    public void setCenter(int x, int y) { rect.setCenter(x, y); }

    /**
     * A setter method to set the position of the top left corner of the button. This can be done 
     * by using the rect of the button directly, but this is a shortcut.
     * 
     * @param x The new x position.
     * @param y The new y position.
     */
    public void setTopLeft(int x, int y) { rect.setTopLeft(x, y); }

    /**
     * A setter method for the rect of the text. It's meant to be used to position the text.
     * 
     * @param g A Graphics2D object used to get the width and height of the text.
     */
    private void setTextRect(Graphics2D g) {
        int width = g.getFontMetrics().stringWidth(text);
        int height = g.getFontMetrics().getAscent();

        textRect = new Rect(0, 0, width, height);
        textRect.setCenter(rect.getCenter());
    }

    /**
     * The method which is called to render the button.
     * 
     * @param g A Graphics2D object used to draw the button to the screen.
     */
    public void render(Graphics2D g) {

        // Background
        g.setColor(backgroundColor);
        if (borderRadius > 0) {
            g.fillRoundRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), borderRadius * 2, borderRadius * 2);
        } 
        else {
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

    /** The method which is called to render a button with an image.
     * 
     * @param g A Graphics2D object used to draw the button to the screen.
     * @param transform The transformation of the image.
     */
    public void render(Graphics2D g, AffineTransform transform) {
        g.drawImage(image, transform, null);
    }

    /**
     * A method which checks if the button is being pressed.
     * 
     * @return A boolean representing if the button was clicked.
     */
    public boolean isClicked() {
        int[] mousePosition = { MouseInput.getX(), MouseInput.getY() };

        return MouseInput.isClicked(1) && rect.contains(mousePosition);
    }

}