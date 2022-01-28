package game.game_objects;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.engine.components.Collider;
import game.engine.components.Rect;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;

/**
 * A class which all Entities inherit.
 */
public abstract class Entity {

    protected BufferedImage image;
    protected Rect sprite;
    protected Collider collider;

    protected int speed;
    protected int width;
    protected int height;

    protected Direction facing = Direction.DOWN;

    protected int totalHealth;
    protected int currentHealth;
    protected int damage;

    /**
     * The constructor for the Entity class.
     * 
     * @param imagePath The path to the image of the Entity.
     */
    public Entity(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void damage(int damage) { 
        currentHealth = Positioning.clamp(currentHealth - damage, totalHealth, 0);
    }

    public int getHealth() { return currentHealth; }

    public Rect getSprite() { return sprite; }
    public Collider getCollider() { return collider; }

    protected void updateXPosition(int diff) {
        sprite.setX(sprite.getX() + diff);
        collider.rect.setX(collider.rect.getX() + diff);
    }
    
    protected void updateYPosition(int diff) {
        sprite.setY(sprite.getY() + diff);
        collider.rect.setY(collider.rect.getY() + diff);
    }

    public void render(Graphics2D g, int[] translation, double scale) {
        AffineTransform transform = new AffineTransform();
        transform.translate(sprite.getX() * scale - translation[0],
                            sprite.getY() * scale - translation[1]);
        transform.scale(scale, scale);

        g.drawImage(image, transform, null);
    }

    public void debug(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

}