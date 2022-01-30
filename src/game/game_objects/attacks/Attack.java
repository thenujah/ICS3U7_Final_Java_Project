package game.game_objects.attacks;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.components.Rect;
import game.engine.components.Collider;
import game.engine.util.Animation;
import game.engine.util.MouseInput;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;
import game.game_objects.Entity;

public abstract class Attack {

    protected int ATTACK_COOLDOWN;

    protected Animation animation = null;

    protected Rect rect;
    protected Rect attacker;
    protected Collider collider;

    protected Direction direction = Direction.DOWN;
    
    protected boolean isAttacking = false;
    protected int damage;
    protected int coolDown = 0;
    
    public Attack(int damage, Rect attacker) {
        this.damage = damage;
        this.attacker = attacker;
    }

    protected void addAnimation(Animation animation) {
        this.animation = animation;
        rect = animation.getRect();
        collider = new Collider(rect);
    }

    protected void addRect(Rect rect) {
        this.rect = rect;
        collider = new Collider(rect);
    }

    public int getCoolDown() { return coolDown; }

    public Collider getCollider() { return collider; }

    public boolean isAttacking() { return isAttacking; }

    public int damage() {
        if (coolDown == 0) {
            coolDown = ATTACK_COOLDOWN;
            return (int) (Math.random() * damage * 2) + damage; 
        } else {
            coolDown--;
            return 0;
        }
    }

    public ArrayList<Entity> attack(ArrayList<Entity> entities) {
        ArrayList<Entity> hits = collider.getEntityCollisions(entities);

        if (isAttacking) {
            for (Entity entity : hits) {
                entity.damage(damage());
            }
        }

        return hits;
    }

    public void attack(Entity entity) { entity.damage(damage()); }

    protected void update() {
        if (MouseInput.isClicked(1) && coolDown == 0) {
            coolDown = ATTACK_COOLDOWN;
            isAttacking = true;
        } else if (coolDown == 0) {
            isAttacking = false;
        }

        coolDown = Positioning.clamp(coolDown - 1, ATTACK_COOLDOWN, 0);

        collider.rect.update(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    protected void update(Direction direction) {
        if (MouseInput.isClicked(1) && coolDown == 0) {
            coolDown = ATTACK_COOLDOWN;
            isAttacking = true;
            animation.play();
        } else if (!animation.isPlaying()) {
            isAttacking = false;
        }

        coolDown = Positioning.clamp(coolDown - 1, ATTACK_COOLDOWN, 0);

        animation.updateFrame();
        animation.updatePosition(direction, attacker);
        this.direction = direction;

        collider.rect.update(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    protected void render(Graphics2D g, int[] translation, double scale) {
        if (animation.isPlaying()) {
            animation.render(g, translation, scale);
        }
    }

}