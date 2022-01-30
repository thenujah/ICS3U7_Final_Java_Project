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

public class Player extends Entity {

	public Swipe swipe;

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

	public boolean isAttacking() { return swipe.isAttacking(); }
	public ArrayList<Entity> attack(ArrayList<Entity> entities) { return swipe.attack(entities); }

	public void updatePosition(TileMap tilemap, Level level) {
		movement(tilemap.walls);
		doorCollisions(tilemap, level);
	}

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
	
	public void doorCollisions(TileMap tilemap, Level level) {
		for (Object[] entrance : tilemap.entrances) {
			Collider entranceCollider = (Collider) entrance[0];
			TileMap connectedRoom = (TileMap) entrance[1];
			Direction entranceDirection = (Direction) entrance[2];

			if (collider.collision(entranceCollider)) {
				level.currentRoom = connectedRoom;

				Collider connectedRoomEntranceCollider = null;
				Direction connectedRoomEntranceDirection = Direction.UP;

				for (Object[] connectedRoomEntrance : connectedRoom.entrances) {

					TileMap maybeTheCurrentRoom = (TileMap) connectedRoomEntrance[1];
					connectedRoomEntranceDirection = (Direction) connectedRoomEntrance[2];

					if (maybeTheCurrentRoom == tilemap && connectedRoomEntranceDirection.equals(
							Positioning.oppositeDirections.get(entranceDirection))) {

						connectedRoomEntranceCollider = (Collider) connectedRoomEntrance[0];
						break;
					}

				}

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

	public void render(Graphics2D g, int[] translation, double scale) {
		super.render(g, translation, scale);
		swipe.render(g, translation, scale);
	}

}
