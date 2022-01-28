package game.game_objects;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.components.Rect;
import game.engine.components.Collider;
import game.engine.util.Animation;
import game.engine.util.MouseInput;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;

public class Attack {

    private Animation animation;
    private Rect rect;
    private Rect owner;
    public Collider collider;
    private Direction direction = Direction.DOWN;
    private boolean isAttacking = false;
    private double rotation;
    private int damage;
    
    public Attack(int damage, Rect owner) {
        this.damage = damage;
        this.owner = owner;
        this.animation = new Animation("./assets/swipe", 15);
        rect = animation.getRect();
        collider = new Collider(rect);
    }

    public void update(Direction direction) {
        if (MouseInput.isClicked(1)) {
            isAttacking = true;
            animation.play();
        } else if (!animation.isPlaying()) {
            isAttacking = false;
        }

        animation.updateFrame();
        animation.updatePosition(direction, owner);
        this.direction = direction;

        collider.rect.update(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    // TODO: Move the rotations to the Animaiton class.

    public void attack(ArrayList<Entity> hits) {
        if (isAttacking) {
            for (Entity entity : hits) {
                entity.damage(damage);
            }
        }
    }

    public void render(Graphics2D g, int[] translation, double scale) {
        if (animation.isPlaying()) {
            animation.render(g, translation, scale);
        }
    }

    public void debug(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int)(rect.getX()),
                   (int)(rect.getY()),
                   (int)(rect.getWidth()),
                   (int)(rect.getHeight()));
    }

}