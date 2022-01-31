package game.game_objects.attacks;

import java.util.ArrayList;
import java.awt.Graphics2D;

import game.engine.components.Rect;
import game.engine.components.Collider;
import game.engine.components.Animation;
import game.engine.util.MouseInput;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;
import game.game_objects.Entity;

/**
 * The base class for attacks. It allows for animations to be added, and for the attacks to
 * be aimed.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public abstract class Attack {

    protected int ATTACK_COOL_DOWN;

    protected Animation animation = null;

    protected Rect rect;
    protected Rect attacker;
    protected Collider collider;

    protected Direction direction = Direction.DOWN;
    
    protected boolean isAttacking = false;
    protected int damage;
    protected int coolDown = 0;

    /**
     * The constructor for an Attack.
     *
     * @param damage The amount of damage the attack does.
     * @param attacker The user of the attack.
     */
    public Attack(int damage, Rect attacker) {
        this.damage = damage;
        this.attacker = attacker;
    }

    /**
     * A method that adds an animation ot the attack.
     *
     * @param animation The animation for the attack.
     */
    protected void addAnimation(Animation animation) {
        this.animation = animation;
        rect = animation.getRect();
        collider = new Collider(rect);
    }

    /**
     * A method which adds a rect to the attack. It uses the rect to initialize a collider.
     * 
     * @param rect The rect of the attack.
     */
    protected void addRect(Rect rect) {
        this.rect = rect;
        collider = new Collider(rect);
    }

    /**
     * A method which says if the current attack is active.
     * 
     * @return A boolean representing the active status of the attack.
     */
    public boolean isAttacking() { return isAttacking; }

    /**
     * A method which generates a semi-random number for the damage dealt by the attack.
     * 
     * @return The damage an entity will receive.
     */
    public int damage() {
        if (coolDown == 0) {
            coolDown = ATTACK_COOL_DOWN;
            return (int) (Math.random() * 5) + damage; 
        } else {
            coolDown--;
            return 0;
        }
    }

    /**
     * A method which deals damage to multiple entities if it hits them.
     * 
     * @param entities The entities to be attacked.
     * @return The Entities hit by the attack.
     */
    public ArrayList<Entity> attack(ArrayList<Entity> entities) {
        ArrayList<Entity> hits = collider.getEntityCollisions(entities);

        if (isAttacking) {
            for (Entity entity : hits) {
                entity.damage(damage());
            }
        }

        return hits;
    }

    /**
     * A method which deals damage to one entity.
     * 
     * @param entity The entity to be attacked.
     */
    public void attack(Entity entity) { entity.damage(damage()); }

    /**
     * A method which updates the cool down and position of an attack.
     */
    protected void update() {
        if (MouseInput.isClicked(1) && coolDown == 0) {
            coolDown = ATTACK_COOL_DOWN;
            isAttacking = true;
        } else if (coolDown == 0) {
            isAttacking = false;
        }

        coolDown = Positioning.clamp(coolDown - 1, ATTACK_COOL_DOWN, 0);

        collider.rect.update(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    /**
     * A method which updates the cool down, position and frames of an animated attack that can be
     * aimed.
     * 
     * @param direction The direction the player is aiming.
     */
    protected void update(Direction direction) {
        if (MouseInput.isClicked(1) && coolDown == 0) {
            coolDown = ATTACK_COOL_DOWN;
            isAttacking = true;
            animation.play();
        } else if (!animation.isPlaying()) {
            isAttacking = false;
        }

        coolDown = Positioning.clamp(coolDown - 1, ATTACK_COOL_DOWN, 0);

        animation.updateFrame();
        animation.updatePosition(direction, attacker);
        this.direction = direction;

        collider.rect.update(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    /**
     * A method which renders the current frame of the attack animation if the attacker is
     * attacking.
     * 
     * @param g The Graphics2D object used to draw images to the screen.
     * @param translation How much the image needs to be translated.
     * @param scale How much the image should be scaled.
     */
    protected void render(Graphics2D g, int[] translation, double scale) {
        if (animation.isPlaying()) {
            animation.render(g, translation, scale);
        }
    }

}