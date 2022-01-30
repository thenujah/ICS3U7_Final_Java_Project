package game.game_objects.attacks;

import game.engine.components.Rect;
import game.game_objects.attacks.Attack;

public class Push extends Attack {
	
	public Push(int damage, Rect attacker) {
		super(damage, attacker);

		ATTACK_COOLDOWN = 5;
		addRect(attacker);
	}

	public void update() {
		super.update();
	}

}