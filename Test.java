package game.scenes;

import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.Color;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.KeyboardInput;
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

	Player player;

	public Test(AppManager app) {
		super(app);

		player = new Player();

		long startTime = System.currentTimeMillis();
		level = new Map();
		long endTime = System.currentTimeMillis();

		long duration = (endTime - startTime);

		System.out.println(duration);
		
		player.setTranslation(level.currentRoom.tanslation);

		// System.out.println(Arrays.toString(level.rooms));

	}

	public void update() {
		player.movement(level.currentRoom, level);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 800);
		level.currentRoom.render(g);

		player.render(g);
	}

}

/**
 * Copy of Player for testing the colliders.
 */
class Player {

	private Rect rect;
	private int speed = 3;
	private Collider collider;
	private int[] tanslation = {0, 0};

	public Player() {

		rect = new Rect(0, 0, 24, 24);
		rect.setCenter(100, 100);

		collider = new Collider(rect.getX(), rect.getY(), 24, 24);
		collider.addSprite(rect);

	}
	
	public void setTranslation(int[] tanslation) {
		this.tanslation = tanslation;
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
		HashMap<String, String> directions = new HashMap<>();
		directions.put("up", "down");
		directions.put("down", "up");
		directions.put("left", "right");
		directions.put("right", "left");

		for (Object[] entrance : tilemap.entrances) {
			Collider entranceCollider = (Collider) entrance[0];
			TileMap connectedRoom = (TileMap) entrance[1];
			String entranceDirection = (String) entrance[2];

			if (this.collider.collision(entranceCollider.rect)) {
				level.currentRoom = connectedRoom;
				setTranslation(level.currentRoom.tanslation);

				Collider connectedRoomEntranceCollider = null;
				String connectedRoomEntranceDirection = "";

				for (Object[] connectedRoomEntrance : connectedRoom.entrances) {

					TileMap maybeTheCurrentRoom = (TileMap) connectedRoomEntrance[1];
					connectedRoomEntranceDirection = (String) connectedRoomEntrance[2];

					if (maybeTheCurrentRoom == tilemap 
							&& connectedRoomEntranceDirection.equals(directions.get(entranceDirection))) {

						connectedRoomEntranceCollider = (Collider) connectedRoomEntrance[0];
						break;
					}

				}

//				System.out.println("Initial side: " + entranceDirection);
//				System.out.println("Final side: " + connectedRoomEntranceDirection);
//				System.out.println("----");

				switch (connectedRoomEntranceDirection) {
				case "up":
					this.rect.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
					this.collider.rect.setMidTop(connectedRoomEntranceCollider.rect.getMidBottom());
					break;
				case "down":
					this.rect.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
					this.collider.rect.setMidBottom(connectedRoomEntranceCollider.rect.getMidTop());
					break;
				case "right":
					this.rect.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
					this.collider.rect.setMidRight(connectedRoomEntranceCollider.rect.getMidLeft());
					break;
				case "left":
					this.rect.setMidLeft(connectedRoomEntranceCollider.rect.getMidRight());
					this.collider.rect.setMidLeft(connectedRoomEntranceCollider.rect.getMidRight());
					break;
				}

			}
		}

	}
	
	public void updateXPosition(int diff) {
		rect.setX(rect.getX() + diff);
		collider.rect.setX(collider.rect.getX() + diff);
	}
	
	public void updateYPosition(int diff) {
		rect.setY(rect.getY() + diff);
		collider.rect.setY(collider.rect.getY() + diff);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(rect.getX() + tanslation[0], rect.getY() + tanslation[1], rect.getWidth(), rect.getHeight());
	}

}