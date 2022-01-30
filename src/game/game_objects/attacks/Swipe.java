package game.game_objects.attacks;

import java.awt.Graphics2D;

import game.engine.components.Rect;
import game.engine.util.Animation;
import game.engine.util.Positioning.Direction;
import game.game_objects.attacks.Attack;

public class Swipe extends Attack {
	
	public Swipe(int damage, Rect attacker) {
		super(damage, attacker);

		ATTACK_COOLDOWN = 30;
        addAnimation(new Animation("./assets/swipe", 15));
	}

	public void update(Direction direction) {
		super.update(direction);
	}

	public void render(Graphics2D g, int[] translation, double scale) {
		super.render(g, translation, scale);
	}

}