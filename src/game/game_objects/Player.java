package game.game_objects;

import java.util.ArrayList;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.KeyboardInput;
import game.engine.util.MouseInput;
import game.engine.util.Positioning;
import game.engine.util.Camera;
import game.engine.util.Animation;
import game.engine.components.Rect;
import game.engine.components.Collider;
import game.game_objects.TileMap;
import game.game_objects.Map;
import game.game_objects.Entity;
import game.game_objects.Attack;

public class Player extends Entity {

	public Attack swipe;

	public Player() {
		super("./assets/Player.png");

		speed = 3;
		height = width = 24;
		
		sprite = new Rect(width, height);
		sprite.setCenter(200, 200); // TODO: Set the player at the center of the tilemap.

		collider = new Collider(sprite.getX(), sprite.getY(), width, height);
		collider.addSprite(sprite);

		swipe = new Attack(sprite);
		swipe.addAnimation(new Animation("./assets/swipe", 15));
	}

	public void update(TileMap tilemap, Map level) {
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
			facing = "left";
		} else if (mouseX > sprite.getRight() * scale - translation[0] && right > up && right > down) {
			facing = "right";
		} else if (mouseY < sprite.getTop() * scale - translation[1] && up > left && up > right) {
			facing = "up";
		} else if (mouseY > sprite.getBottom() * scale - translation[1] && down > left && down > right) {
			facing = "down";
		}

		// System.out.println(facing);

		swipe.update(facing);
	}
	
	public void movement(ArrayList<Collider> walls) {
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
	
	public void doorCollisions(TileMap tilemap, Map level) {
		for (Object[] entrance : tilemap.entrances) {
			Collider entranceCollider = (Collider) entrance[0];
			TileMap connectedRoom = (TileMap) entrance[1];
			String entranceDirection = (String) entrance[2];

			if (collider.collision(entranceCollider.rect)) {
				level.currentRoom = connectedRoom;

				Collider connectedRoomEntranceCollider = null;
				String connectedRoomEntranceDirection = "";

				for (Object[] connectedRoomEntrance : connectedRoom.entrances) {

					TileMap maybeTheCurrentRoom = (TileMap) connectedRoomEntrance[1];
					connectedRoomEntranceDirection = (String) connectedRoomEntrance[2];

					if (maybeTheCurrentRoom == tilemap && connectedRoomEntranceDirection.equals(
							Positioning.oppositeDirections.get(entranceDirection))) {

						connectedRoomEntranceCollider = (Collider) connectedRoomEntrance[0];
						break;
					}

				}

				switch (connectedRoomEntranceDirection) {
				case "up":
					sprite.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
					collider.rect.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
					break;
				case "down":
					sprite.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
					collider.rect.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
					break;
				case "right":
					sprite.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
					collider.rect.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
					break;
				case "left":
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
		swipe.debug(g, translation, scale);
	}

}
