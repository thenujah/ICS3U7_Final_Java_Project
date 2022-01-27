package game.game_objects;

import java.util.ArrayList;

import game.engine.components.Rect;
import game.engine.components.Collider;
import game.engine.util.KeyboardInput;
import game.engine.util.Positioning;
import game.game_objects.TileMap;
import game.game_objects.Player;
import game.game_objects.Entity;

public class Enemy extends Entity {

	private int range = 100;
	private int[] targetPosition = new int[2];
	
	public Enemy(int x, int y) {
		super("./assets/Enemy.png");

		speed = 1;
		height = width = 16;

		sprite = new Rect(x, y, width, height);

		collider = new Collider(sprite.getX(), sprite.getY(), width, height);
		collider.addSprite(sprite);
	}
	
	public void movement(Player player, TileMap room, ArrayList<Collider> enemies) {
		if (withinRangeOf(player.sprite.getCenter())) {
			active(room, player.sprite.getTopLeft());
		} else {
			idle(room);
		}

		// collider.xCollision(collider.getCollisions(enemies));
		// collider.yCollision(collider.getCollisions(enemies));
	}

	public boolean withinRangeOf(int[] position) {
		double distance = Math.sqrt(
			Math.pow(position[0] - sprite.getCenter()[0], 2) 
			+ Math.pow(position[1] - sprite.getCenter()[1], 2));

		return distance <= range;
	}

	private void idle(TileMap room) {
		int xPosition = sprite.getX();
		int yPosition = sprite.getY();

		boolean resetTarget = false;

		if (((xPosition - speed) <= targetPosition[0] && targetPosition[0] <= (xPosition + speed))
			&& ((yPosition - speed) <= targetPosition[1] && targetPosition[1] <= (yPosition + speed))
			|| targetPosition[0] == 0 && targetPosition[1] == 0) {

			targetPosition = Positioning.generateRandomPositionWithin(room);
		}

		int x = targetPosition[0];
		int y = targetPosition[1];

		if (x > sprite.getX()) {
			updateXPosition(speed);
		} else if (x < sprite.getX()) {
			updateXPosition(-speed);
		}

		ArrayList<Collider> hits = collider.getCollisions(room.walls);

		if (hits.size() > 0) {
			resetTarget = true;
			collider.xCollision(hits);
		}

		collider.xCollision(collider.getCollisions(room.walls));

		if (y > sprite.getY()) {
			updateYPosition(speed);
		} else if (y < sprite.getY()) {
			updateYPosition(-speed);
		}

		hits = collider.getCollisions(room.walls);

		if (hits.size() > 0) {
			resetTarget = true;
			collider.yCollision(hits);
		}

		if (resetTarget) targetPosition = Positioning.generateRandomPositionWithin(room);
	}

	// TODO: Make the enemies avoid each other; don't allow them to stack.

	private void active(TileMap room, int[] playerPosition) {
		int x = playerPosition[0];
		int y = playerPosition[1];
		
		if (x > sprite.getX()) {
			updateXPosition(speed);
		} else if (x < sprite.getX()) {
			updateXPosition(-speed);
		}

		collider.xCollision(collider.getCollisions(room.walls));
		
		if (y > sprite.getY()) {
			updateYPosition(speed);
		} else if (y < sprite.getY()) {
			updateYPosition(-speed);
		}

		collider.yCollision(collider.getCollisions(room.walls));
	}

}

