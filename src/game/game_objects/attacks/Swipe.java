package game.game_objects.attacks;

import java.awt.Graphics2D;

import game.engine.components.Rect;
import game.engine.components.Animation;
import game.engine.util.Positioning.Direction;

/**
 * A type of attack. It points in the direction of the cursor and is used by clicking the mouse.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Swipe extends Attack {

	/**
	 * The constructor for the Swipe attack
	 *
	 * @param damage The amount of damage the attack does.
	 * @param attacker The user of the attack.
	 */
	public Swipe(int damage, Rect attacker) {
		super(damage, attacker);

		ATTACK_COOL_DOWN = 20;
        addAnimation(new Animation("./assets/swipe", 15));
	}

	/**
	 * A method which updates the state of the attack when called.
	 */
	public void update(Direction direction) { super.update(direction); }

	/**
	 * A method which renders the image of the attack when called.
	 *
	 * @param g The Graphics2D object used to draw images to the screen.
	 * @param translation How much the image needs to be translated.
	 * @param scale How much the image should be scaled.
	 */
	public void render(Graphics2D g, int[] translation, double scale) {
		super.render(g, translation, scale);
	}

}