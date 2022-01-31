package game.game_objects;

import java.util.ArrayList;
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
 * A class which all Entities inherit. It includes support for attacks, colliders and movement.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
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

    /**
     * A method which applies damage to the entity.
     * 
     * @param damage The damage dealt to the entity.
     */
    public void damage(int damage) {
        currentHealth = Positioning.clamp(currentHealth - damage, totalHealth, 0);
    }

    /**
     * A getter method for the health of the Entity.
     *
     * @return The Entity's health.
     */
    public int getHealth() { return currentHealth; }

    /**
     * A getter method for the sprite of the Entity.
     *
     * @return The Entity's sprite.
     */
    public Rect getSprite() { return sprite; }

    /**
     * A getter method for the direction the Entity is facing.
     *
     * @return The direction the Entity is facing.
     */
    public Direction getDirectionFacing() { return facing; }

    /**
     * A getter method for the Collider of the Entity.
     *
     * @return The Entity's collider.
     */
    public Collider getCollider() { return collider; }

    /**
     * A method which determines which other Entities an Entity has collided with.
     *
     * @param entities The Entities that can be collided with.
     * @return The Entities that were collided with.
     */
    public ArrayList<Entity> getEntityCollisions(ArrayList<Entity> entities) {
        return collider.getEntityCollisions(entities);
    }

    /**
     * A method which checks if an entity collided with another entity.
     * 
     * @param entity The entity which might have been collided with.
     */
    public boolean collidedWith(Entity entity) { 
        return collider.collidedWith(entity.getCollider());
    }

    /**
     * A method which updates the x position of the entity.
     * 
     * @param diff The change in the x-axis.
     */
    protected void updateXPosition(int diff) {

        if (force[0] != 0) {
            xFriction += FRICTION;

            if (force[0] > 1) {
                force[0] = Positioning.clamp(force[0] - (int) xFriction, force[0], 0);
            } else if (force[0] < 1) {
                force[0] = Positioning.clamp(force[0] + (int) xFriction, 0, force[0]);
            }
        } else {
            force[1] = 0;
            xFriction = 0;
        }

        sprite.setX(sprite.getX() + diff + force[0]);
        collider.rect.setX(collider.rect.getX() + diff + force[0]);
    }
    
    /**
     * A method which updates the y position of the entity.
     * 
     * @param diff The change in the y-axis.
     */
    protected void updateYPosition(int diff) {
        if (force[1] != 0) {
            yFriction += FRICTION;

            if (force[1] > 1) {
                force[1] = Positioning.clamp(force[1] - (int) yFriction, force[1], 0);
            } else if (force[1] < 1) {
                force[1] = Positioning.clamp(force[1] + (int) yFriction, 0, force[1]);
            }
        } else {
            force[0] = 0;
            yFriction = 0;
        }

        sprite.setY(sprite.getY() + diff + this.force[1]);
        collider.rect.setY(collider.rect.getY() + diff + this.force[1]);
    }

    /**
     * A method which applies a force to the entity, pushing it in the specified direction.
     * 
     * @param direction The direction the entity will be pushed in.
     * @param force The amount of force applied to the entity.
     */
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

     /**
     * A method which renders the image of the entity.
     * 
     * @param g The Graphics2D object used to draw images to the screen.
     * @param translation How much the image needs to be translated.
     * @param scale How much the image should be scaled.
     */
    public void render(Graphics2D g, int[] translation, double scale) {
        AffineTransform transform = new AffineTransform();
        transform.translate(sprite.getX() * scale - translation[0],
                            sprite.getY() * scale - translation[1]);
        transform.scale(scale, scale);

        g.drawImage(image, transform, null);
    }

}