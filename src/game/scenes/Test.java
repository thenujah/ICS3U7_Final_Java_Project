package game.scenes;

import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.KeyboardInput;
import game.engine.util.Positioning;
import game.engine.util.Camera;
import game.engine.components.Rect;
import game.engine.components.Collider;
import game.game_objects.TileMap;
import game.game_objects.Map;

/**
 * A class which controls the game scene of the game.
 *
 * @version 2.0
 * @since 1.0
 */
public class Test extends Scene {

	private Map level;
	private Camera camera;
	private Player player;

	public Test(AppManager app) {
		super(app);

		camera = new Camera(1.2);

		player = new Player();

		// long startTime = System.currentTimeMillis();
		level = new Map();
		// long endTime = System.currentTimeMillis();

		// long duration = (endTime - startTime);

		// System.out.println("Time taken to initialize the level: " + duration + "ms");
	}

	public void update() {
		player.movement(level.currentRoom, level);
		camera.update(player.sprite);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 800);
		level.currentRoom.renderGround(g, camera.getTranslation(), camera.getScale());
		level.currentRoom.renderBackground(g, camera.getTranslation(), camera.getScale());

		player.render(g, camera.getTranslation(), camera.getScale());

		level.currentRoom.renderForeground(g, camera.getTranslation(), camera.getScale());
	}

}

/**
 * Copy of Player for testing the colliders.
 */
class Player {

	public Rect sprite;  // Image of the player.
	private Collider collider;  // Player collider.
	private int speed = 3;

	public Player() {

		sprite = new Rect(24, 24);
		sprite.setCenter(100, 100);

		collider = new Collider(sprite.getX(), sprite.getY(), 24, 24);
		collider.addSprite(sprite);

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
	
	public void updateXPosition(int diff) {
		sprite.setX(sprite.getX() + diff);
		collider.rect.setX(collider.rect.getX() + diff);
	}
	
	public void updateYPosition(int diff) {
		sprite.setY(sprite.getY() + diff);
		collider.rect.setY(collider.rect.getY() + diff);
	}

	public void render(Graphics2D g, int[] translation, double scale) {
		g.setColor(Color.red);
		g.fillRect((int) (sprite.getX() * scale - translation[0]), 
				   (int) (sprite.getY() * scale - translation[1]),
				   (int) (sprite.getWidth() * scale), 
				   (int) (sprite.getHeight() * scale));

		// g.drawRect(sprite.getX(), 
		// 		   sprite.getY(),
		// 		   sprite.getWidth(), 
		// 		   sprite.getHeight());
	}

}