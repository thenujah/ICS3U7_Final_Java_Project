package game.game_objects;

import java.util.ArrayList;
import java.awt.Graphics2D;

import game.engine.util.KeyboardInput;
import game.engine.util.MouseInput;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;
import game.engine.components.Rect;
import game.engine.components.Collider;
import game.game_objects.attacks.Swipe;

/**
 * A class which creates players. Players are entities controlled by the user.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Player extends Entity {

    public Swipe swipe;

    /**
     * The constructor for the Player.
     */
    public Player() {
        super("./assets/Player.png");

        speed = 3;
        height = width = 24;
        damage = 15;

        totalHealth = currentHealth = 1000;
        
        sprite = new Rect(width, height);
        sprite.setCenter(200, 200);

        collider = new Collider(sprite.getX(), sprite.getY(), width, height);
        collider.addSprite(sprite);

        swipe = new Swipe(damage, sprite);
    }

    /**
     * A method used to reset the health of the player.
     */
    public void resetHealth() { currentHealth = totalHealth; }

    /**
     * A method which returns true if the player is attacking.
     *
     * @return A boolean stating if the player is attacking.
     */
    public boolean isAttacking() { return swipe.isAttacking(); }

    /**
     * A method which deals damage to multiple entities if it hits them.
     *
     * @param entities The entities to be attacked.
     * @return The Entities hit by the attack.
     */
    public ArrayList<Entity> attack(ArrayList<Entity> entities) { return swipe.attack(entities); }

    /**
     * A method which sets the center of the Player.
     *
     * @param x The new x position of the Player's center.
     * @param y The new y position of the Player's center.
     */
    public void setCenter(int x, int y) {
        sprite.setCenter(x, y);
        collider.setCenter(x, y);
    }

    /**
     * A method used to update the position of the Player.
     *
     * @param tilemap The current tilemap the Player is on.
     * @param level The current level the Player is in.
     */
    public void updatePosition(TileMap tilemap, Level level) {
        movement(tilemap.walls);
        doorCollisions(tilemap, level);
    }

    /**
     * A method which updates the direction the player is facing based off the position of the
     * cursor. It does this by checking which side of the player it is closest to.
     * 
     * @param translation How much the camera is offsetting the images.
     * @param scale How much the camera is scaling the images.
     */
    public void updateDirection(int[] translation, double scale) {
        double mouseX = MouseInput.getX();
        double mouseY = MouseInput.getY();

        double left = (sprite.getLeft() * scale - translation[0]) - mouseX;
        double right = mouseX - (sprite.getRight() * scale - translation[0]);
        double up = (sprite.getTop() * scale - translation[1]) - mouseY;
        double down = mouseY - (sprite.getBottom() * scale - translation[1]);

        if (mouseX < sprite.getLeft() * scale - translation[0] && left > up && left > down) {
            facing = Direction.LEFT;
        } else if (mouseX > sprite.getRight() * scale - translation[0] && right > up && right > down) {
            facing = Direction.RIGHT;
        } else if (mouseY < sprite.getTop() * scale - translation[1] && up > left && up > right) {
            facing = Direction.UP;
        } else if (mouseY > sprite.getBottom() * scale - translation[1] && down > left && down > right) {
            facing = Direction.DOWN;
        }

        swipe.update(facing);
    }
    
    /**
     * A method which moves the player to a new position based on which keys on the keyboard are
     * currently being pressed.
     * 
     * @param walls The walls of the current room the player is inside.
     */
    private void movement(ArrayList<Collider> walls) {
        if (KeyboardInput.isPressed("a")) {
            updateXPosition(-speed);
        }
        if (KeyboardInput.isPressed("d")) {
            updateXPosition(speed);
        }

        collider.xCollision(collider.getCollisions(walls));
        
        if (KeyboardInput.isPressed("w")) {
            updateYPosition(-speed);
        }
        if (KeyboardInput.isPressed("s")) {
            updateYPosition(speed);
        }
        
        collider.yCollision(collider.getCollisions(walls));
    }
    
    /**
     * A method which determines if the player has collided with an entrance. If they did the 
     * current room will be changed along with the position of the player.
     * 
     * @param tilemap The current room the player is within.
     * @param level The current level the player is in.
     */
    public void doorCollisions(TileMap tilemap, Level level) {
        for (Object[] entrance : tilemap.entrances) {

            Collider entranceCollider = (Collider) entrance[0];
            TileMap connectedRoom = (TileMap) entrance[1];
            Direction entranceDirection = (Direction) entrance[2];

            if (this.collider.collidedWith(entranceCollider)) {
                level.currentRoom = connectedRoom;

                Collider connectedRoomEntranceCollider = null;
                Direction connectedRoomEntranceDirection = Direction.UP;

                // Finding the entrance in the connected room that corresponds to the one the
                // player entered.
                for (Object[] connectedRoomEntrance : connectedRoom.entrances) {

                    TileMap maybeTheCurrentRoom = (TileMap) connectedRoomEntrance[1];
                    connectedRoomEntranceDirection = (Direction) connectedRoomEntrance[2];

                    // If the current entrance being checked leads to the room the player is
                    // currently in, the collider for it is stored in a variable.
                    if (maybeTheCurrentRoom == tilemap && connectedRoomEntranceDirection.equals(
                            Positioning.oppositeDirections.get(entranceDirection))) {

                        connectedRoomEntranceCollider = (Collider) connectedRoomEntrance[0];
                        break;
                    }

                }

                // Set the player's position to that of the entrance they will exit from.
                switch (connectedRoomEntranceDirection) {
                case UP:
                    sprite.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
                    collider.rect.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
                    break;
                case DOWN:
                    sprite.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
                    collider.rect.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
                    break;
                case RIGHT:
                    sprite.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
                    collider.rect.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
                    break;
                case LEFT:
                    sprite.setMidLeft(connectedRoomEntranceCollider.rect.getMidRight());
                    collider.rect.setMidLeft(connectedRoomEntranceCollider.rect.getMidRight());
                    break;
                }

            }
        }
    }

    /**
     * A method which renders the Player and its attack.
     *
     * @param g The Graphics2D object used to draw images to the screen.
     * @param translation How much the image needs to be translated.
     * @param scale How much the image should be scaled.
     */
    public void render(Graphics2D g, int[] translation, double scale) {
        super.render(g, translation, scale);
        swipe.render(g, translation, scale);
    }

}
