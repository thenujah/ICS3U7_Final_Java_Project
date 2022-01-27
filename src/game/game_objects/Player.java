package game.game_objects;

import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.KeyboardInput;
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

	Attack swipe;

	public Player() {
		super("./assets/Player.png");

		speed = 3;
		height = width = 24;
		
		sprite = new Rect(width, height);
		sprite.setCenter(200, 200); // TODO: Set the player at the center of the tilemap.

		collider = new Collider(sprite.getX(), sprite.getY(), width, height);
		collider.addSprite(sprite);

		swipe = new Attack(this.sprite);
		System.out.println("here");
		swipe.addAnimation(new Animation("./assets/swipe", 10));
	}

	public void update(TileMap tilemap, Map level) {
		movement(tilemap, level);
		swipe.update(sprite.getX(), sprite.getY());
	}
	
	public void movement(TileMap tilemap, Map level) {
		if (KeyboardInput.isPressed("a")) {
			updateXPosition(-speed);
		}
		if (KeyboardInput.isPressed("d")) {
			updateXPosition(speed);
		}

		collider.xCollision(collider.getCollisions(tilemap.walls));
		
		if (KeyboardInput.isPressed("w")) {
			updateYPosition(-speed);
		}
		if (KeyboardInput.isPressed("s")) {
			updateYPosition(speed);
		}
		
		collider.yCollision(collider.getCollisions(tilemap.walls));

		doorCollisions(tilemap, level);
	}
	
	// Large :/
	public void doorCollisions(TileMap tilemap, Map level) {
		for (Object[] entrance : tilemap.entrances) {
			Collider entranceCollider = (Collider) entrance[0];
			TileMap connectedRoom = (TileMap) entrance[1];
			String entranceDirection = (String) entrance[2];

			if (this.collider.collision(entranceCollider.rect)) {
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
					this.sprite.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
					this.collider.rect.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
					break;
				case "down":
					this.sprite.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
					this.collider.rect.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
					break;
				case "right":
					this.sprite.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
					this.collider.rect.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
					break;
				case "left":
					this.sprite.setMidLeft(connectedRoomEntranceCollider.rect.getMidRight());
					this.collider.rect.setMidLeft(connectedRoomEntranceCollider.rect.getMidRight());
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
