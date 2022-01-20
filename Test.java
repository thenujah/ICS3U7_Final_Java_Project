package game.scenes;

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

	public Player() {

		rect = new Rect(0, 0, 24, 24);
		rect.setCenter(100, 100);

		collider = new Collider(rect.getX(), rect.getY(), 24, 24);
		collider.addSprite(rect);

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
		for (Object[] entranceInfo : tilemap.doors) {
			Collider collider = (Collider) entranceInfo[0];
			TileMap connectedRoom = (TileMap) entranceInfo[1];

			if (this.collider.collision(collider.rect)) {
				level.currentRoom = connectedRoom;

				Collider otherDoor = null;
				String direction = "";

				for (Object[] info : connectedRoom.doors) {
					TileMap newTileMap = (TileMap) info[1];
					if (newTileMap.equals(tilemap))
						otherDoor = (Collider) info[0];
						direction = (String) info[2];
				}

//				System.out.println(collider + " " + otherDoor);
				System.out.println(direction);

				switch (direction) {
				case "up":
					this.rect.setMidTop(otherDoor.rect.getMidBottom());
					this.collider.rect.setMidTop(otherDoor.rect.getMidBottom());
					break;
				case "down":
					this.rect.setMidBottom(otherDoor.rect.getMidTop());
					this.collider.rect.setMidBottom(otherDoor.rect.getMidTop());
					break;
				case "right":
					this.rect.setMidRight(otherDoor.rect.getMidLeft());
					this.collider.rect.setMidRight(otherDoor.rect.getMidLeft());
					break;
				case "left":
					this.rect.setMidLeft(otherDoor.rect.getMidRight());
					this.collider.rect.setMidLeft(otherDoor.rect.getMidRight());
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
		g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

}