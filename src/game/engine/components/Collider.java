package game.engine.components;

import java.util.ArrayList;

import game.game_objects.Entity;

/**
 * A class which creates colliders. These colliders are used to detect and handle collisions
 * between Entities and other objects which have colliders.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Collider {

    public Rect rect;
    public Rect entitySprite;

    /**
     * The constructor for the Collider.
     *
     * @param rect The rect of the Entity that this Collider belongs to.
     */
    public Collider(Rect rect) {
        entitySprite = rect;
        this.rect = new Rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    /**
     * An alternate constructor for the Collider. It allows you to make colliders which don't
     * belong to any other object
     *
     * @param x The x position of the Collider.
     * @param y The y position of the Collider.
     * @param width The width of the Collider.
     * @param height The height of the Collider.
     *
     * @see #Collider(Rect)
     */
    public Collider(int x, int y, int width, int height) { rect = new Rect(x, y, width, height); }

    /**
     * A method which specifies the sprite the Collider belongs to.
     *
     * @param rect The Rect of the object that this Collider belongs to.
     */
    public void addSprite(Rect rect) { entitySprite = rect; }

    /**
     * A method which sets the center of the Collider.
     *
     * @param x The x position of the center of the collider.
     * @param y The y position of the center of the collider.
     */
    public void setCenter(int x, int y) { rect.setCenter(x, y); }

    /**
     * A method which determines if a Collider collided with a specified Rect on its right side.
     *
     * @param rect The Rect.
     * @return A boolean saying if the Collider is colliding with another Rect.
     */
    public boolean rightCollision(Rect rect) { 
        return this.rect.getRight() > rect.getLeft() && this.rect.getRight() < rect.getRight(); 
    }

    /**
     * A method which determines if a Collider collided with a specified Rect on its left side.
     *
     * @param rect The Rect.
     * @return A boolean saying if the Collider is colliding with another Rect.
     */
    public boolean leftCollision(Rect rect) {
        return this.rect.getLeft() < rect.getRight() && this.rect.getLeft() > rect.getLeft(); 
    }

    /**
     * A method which determines if a Collider collided with a specified Rect on its top side.
     *
     * @param rect The Rect.
     * @return A boolean saying if the Collider is colliding with another Rect.
     */
    public boolean topCollision(Rect rect) {
        return this.rect.getTop() < rect.getBottom() && this.rect.getTop() > rect.getTop(); 
    }

    /**
     * A method which determines if a Collider collided with a specified Rect on its bottom side.
     *
     * @param rect The Rect.
     * @return A boolean saying if the Collider is colliding with another Rect.
     */
    public boolean bottomCollision(Rect rect) {
        return this.rect.getBottom() > rect.getTop() && this.rect.getBottom() < rect.getBottom(); 
    }

    /**
     * A method which determines if a Collider collided with a specified Rect.
     *
     * @param rect The Rect.
     * @return A boolean saying if the Collider is colliding with the other Rect.
     */
    public boolean collidedWith(Rect rect) {
        return this.rect.overlaps(rect) || rect.overlaps(this.rect);
    }

    /**
     * A method which determines if the Collider collided with a different Collider.
     *
     * @param collider The Collider.
     * @return A boolean saying if the Collider is colliding with the other Collider.
     */
    public boolean collidedWith(Collider collider) {
        return rect.overlaps(collider.rect) || collider.rect.overlaps(rect);
    }

    /**
     * A method which returns an ArrayList of the Colliders in another ArrayList that were hit by
     * the current Collider instance.
     *
     * @param colliders The Colliders that have potentially collided with the current instance.
     * @return An ArrayList with all the hit Colliders
     */
    public ArrayList<Collider> getCollisions(ArrayList<Collider> colliders) {
        ArrayList<Collider> collisions = new ArrayList<>();
        for (Collider collider : colliders) {
            if (collidedWith(collider.rect)) {
                collisions.add(collider);
            }
        }
        
        return collisions;
    }

    /**
     * A method which returns an ArrayList of the Entities in another ArrayList that were hit by
     * the current Collider instance.
     *
     * @param colliders The Entities that have potentially collided with the current instance.
     * @return An ArrayList with all the hit Entities.
     */
    public ArrayList<Entity> getEntityCollisions(ArrayList<Entity> colliders) {
        ArrayList<Entity> collisions = new ArrayList<>();
        for (Entity entity : colliders) {
            if (collidedWith(entity.getCollider().rect)) {
                collisions.add(entity);
            }
        }
        
        return collisions;
    }

    /**
     * A method which checks if the colliding Colliders are colliding with the current Collider
     * instance on its left or right. It also corrects the position of the current Collider so that
     * it isn't colliding with the other Colliders anymore.
     *
     * @param hits The colliders hit by the current Collider instance.
     */
    public void xCollision(ArrayList<Collider> hits) {
        for (Collider instance : hits) {
            if (instance == this) continue;
            Rect rect = instance.rect;

            if (rightCollision(rect)) { 
                this.rect.setRight(rect.getLeft());
                this.entitySprite.setRight(rect.getLeft());
            } else if (leftCollision(rect)) {
                this.rect.setLeft(rect.getRight());
                this.entitySprite.setLeft(rect.getRight());
            }
        }
    }

    /**
     * A method which checks if the colliding Colliders are colliding with the current Collider
     * instance on its top or bottom. It also corrects the position of the current Collider so that
     * it isn't colliding with the other Colliders anymore.
     *
     * @param hits The colliders hit by the current Collider instance.
     */
    public void yCollision(ArrayList<Collider> hits) {
        for (Collider instance : hits) {
            if (instance == this) continue;
            Rect rect = instance.rect;

            if (topCollision(rect)) {
                this.rect.setTop(rect.getBottom());
                this.entitySprite.setTop(rect.getBottom());
            } else if (bottomCollision(rect)) {
                this.rect.setBottom(rect.getTop());
                this.entitySprite.setBottom(rect.getTop());
            }
        }
    }

}