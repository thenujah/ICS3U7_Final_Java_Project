package game.game_objects;

import java.util.ArrayList;

import game.engine.components.Rect;
import game.engine.components.Collider;
import game.engine.util.Positioning;
import game.game_objects.attacks.Push;

/**
 * A class which creates Enemy instances. It will be inherited by all types of enemies.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Enemy extends Entity {

    private final int range = 100;
    private int[] targetPosition = new int[2];

    private Push push;
    
    /**
     * The constructor for the Enemy class. 
     *
     * @param x The x position of the enemy.
     * @param y The y position of the enemy.
     */
    public Enemy(int x, int y) {
        super("./assets/Enemy.png");

        speed = 1;
        height = width = 16;
        totalHealth = currentHealth = 50;
        damage = 2;

        sprite = new Rect(x, y, width, height);

        collider = new Collider(sprite.getX(), sprite.getY(), width, height);
        collider.addSprite(sprite);

        push = new Push(damage, sprite);
    }

    /**
     * A method which deals damage to one entity.
     *
     * @param entity The entity to be attacked.
     */
    public void attack(Entity entity) { push.attack(entity); }

    /**
     * The method which handles the movement of the enemy.
     * 
     * @param player The player. 
     * @param room The current tilemap.
     */
    public void movement(Player player, TileMap room) {
        if (withinRangeOf(player.sprite.getCenter())) {
            active(room, player.sprite.getTopLeft());
        } else {
            idle(room);
        }
    }

    /**
     * A method which checks if a position is within range of the enemy.
     * It's used to check if the player is near the enemy.
     * 
     * @param position The position which will be checked.
     * 
     * @return A boolean representing if the position is within range of the enemy.
     */
    public boolean withinRangeOf(int[] position) {
        double distance = Math.sqrt(
            Math.pow(position[0] - sprite.getCenter()[0], 2) 
            + Math.pow(position[1] - sprite.getCenter()[1], 2));

        return distance <= range;
    }

    /**
     * The idle state of the enemy. The default action of the enemy.
     * 
     * @param room The tilemap the enemy is in.
     */
    private void idle(TileMap room) {
        boolean resetTarget = false;

        // Check if the enemy has reached its target position and if it did, generate a new positon.
        if (((sprite.getX() - speed) <= targetPosition[0] && targetPosition[0] <= (sprite.getX() + speed))
            && ((sprite.getY() - speed) <= targetPosition[1] && targetPosition[1] <= (sprite.getY() + speed))
            || targetPosition[0] == 0 && targetPosition[1] == 0) {

            targetPosition = Positioning.generateRandomPositionWithin(room);
        }

        // Update the enemy's x position.
        if (targetPosition[0] > sprite.getX()) {
            updateXPosition(speed);
        } else if (targetPosition[0] < sprite.getX()) {
            updateXPosition(-speed);
        }

        // Handle collisions on the left and right of the enemy.
        ArrayList<Collider> hits = collider.getCollisions(room.walls);

        if (hits.size() > 0) {
            resetTarget = true;
            collider.xCollision(hits);
        }

        // Update the enemy's y position.
        if (targetPosition[1] > sprite.getY()) {
            updateYPosition(speed);
        } else if (targetPosition[1] < sprite.getY()) {
            updateYPosition(-speed);
        }

        // Handle the collisions on the top and bottom of the enemy.
        hits = collider.getCollisions(room.walls);

        if (hits.size() > 0) {
            resetTarget = true;
            collider.yCollision(hits);
        }

        // Reset the targetPosition if needed.
        if (resetTarget) targetPosition = Positioning.generateRandomPositionWithin(room);
    }

    /**
     * The active state of the enemy. If the player is within range, the enemy will follow them.
     *
     * @param room The tilemap the enemy is in.
     */
    private void active(TileMap room, int[] playerPosition) {
        int x = playerPosition[0];
        int y = playerPosition[1];

        // Update the enemy's x position.
        if (x > sprite.getX()) {
            updateXPosition(speed);
        } else if (x < sprite.getX()) {
            updateXPosition(-speed);
        }

        // Handle any collisions of the left or right of the enemy.
        collider.xCollision(collider.getCollisions(room.walls));
        
        // Update the enemy's y position.
        if (y > sprite.getY()) {
            updateYPosition(speed);
        } else if (y < sprite.getY()) {
            updateYPosition(-speed);
        }

        // Handle any collisions on the top or bottom of the enemy.
        collider.yCollision(collider.getCollisions(room.walls));
    }

}
