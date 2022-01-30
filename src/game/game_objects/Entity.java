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

    protected final double FRICTION = 0.5;
    protected double xFriction = 0;
    protected double yFriction = 0;
    protected int[] force = new int[2];
    protected int knockbackTimer;

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
    public Direction getDirectionFacing() { return facing; }
    public Collider getCollider() { return collider; }

    public ArrayList<Collider> getCollisions(ArrayList<Collider> colliders) { 
        return collider.getCollisions(colliders);
    }

    public ArrayList<Entity> getEntityCollisions(ArrayList<Entity> colliders) { 
        return collider.getEntityCollisions(colliders);
    }

    public boolean collidedWith(Entity entity) { 
        return collider.collision(entity.getCollider());
    }

    protected void updateXPosition(int diff) {

        if (force[0] != 0) {
            xFriction += FRICTION;

            if (force[0] > 1) {
                force[0] = Positioning.clamp(force[0] - (int) xFriction, force[0], 0);
            } else if (force[0] < 1) {
                force[0] = Positioning.clamp(force[0] + (int) xFriction, 0, force[0]);
            }
        } else {
            force[0] = force[1] = 0;
            xFriction = 0;
        }

        sprite.setX(sprite.getX() + diff + force[0]);
        collider.rect.setX(collider.rect.getX() + diff + force[0]);
    }
    
    protected void updateYPosition(int diff) {
        if (force[1] != 0) {
            yFriction += FRICTION;

            if (force[1] > 1) {
                force[1] = Positioning.clamp(force[1] - (int) yFriction, force[1], 0);
            } else if (force[1] < 1) {
                force[1] = Positioning.clamp(force[1] + (int) yFriction, 0, force[1]);
            }
        } else {
            force[0] = force[1] = 0;
            yFriction = 0;
        }

        sprite.setY(sprite.getY() + diff + this.force[1]);
        collider.rect.setY(collider.rect.getY() + diff + this.force[1]);
    }

    public void push(Direction direction, int force) {
        int randomDeviation = (int) (Math.random() * 30) - 15;

        switch (direction) {
            case UP -> {
                this.force[1] = -force;
                this.force[0] = randomDeviation;
            }
            case DOWN -> {
                this.force[1] = force;
                this.force[0] = randomDeviation;
            }
            case LEFT -> {
                this.force[0] = -force;
                this.force[1] = randomDeviation;
            }
            case RIGHT -> {
                this.force[0] = force;
                this.force[1] = randomDeviation;
            }
        }
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