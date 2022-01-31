package game.game_objects.attacks;

import game.engine.components.Rect;

/**
 * A type of attack. It has no animation and damages an entity when the attacker collides with it.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Push extends Attack {
	
	/**
	 * The constructor for the Push attack
	 * 
	 * @param damage The amount of damage the attack does.
	 * @param attacker The user of the attack.
	 */
	public Push(int damage, Rect attacker) {
		super(damage, attacker);

		ATTACK_COOL_DOWN = 5;
		addRect(attacker);
	}

	/**
	 * A method which updates the state of the attack when called.
	 */
	public void update() {
		super.update();
	}

}