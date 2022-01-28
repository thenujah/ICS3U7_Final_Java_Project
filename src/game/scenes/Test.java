package game.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.Color;

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
import game.game_objects.Player;
import game.game_objects.Enemy;
import game.game_objects.Entity;

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
	private int totalEnemies = 0;

	public Test(AppManager app) {
		super(app);

		camera = new Camera(2);
		player = new Player();
		level = new Map();

		// Generate a random number of enemies in each room.
		for (TileMap room : level.rooms) {
			int numberOfEnemies = (int) (Math.random() * 6) + 3;
			totalEnemies += numberOfEnemies;

			for (int i = 0; i < numberOfEnemies; i++) {
				int[] position = Positioning.generateRandomPositionWithin(room);

				int x = position[0];
				int y = position[1];

				room.enemies.add(new Enemy(position[0], position[1]));
			}
		}
	}

	public void update() {
		player.update(level.currentRoom, level);
		camera.update(player.getSprite());
		player.updateDirection(camera.getTranslation(), camera.getScale());

		for (Entity entity : level.currentRoom.enemies) {
			Enemy enemy = (Enemy) entity;
			enemy.movement(player, level.currentRoom);
		}

		ArrayList<Entity> attackHits = player.swipe.collider.getEntityCollisions(level.currentRoom.enemies);
		player.swipe.attack(attackHits);

		for (Entity enemy : attackHits) {
			if (enemy.getHealth() == 0) {
				level.currentRoom.enemies.remove(enemy);
				totalEnemies--;
			}
		}

		ArrayList<Entity> playerHits = player.getCollider().getEntityCollisions(level.currentRoom.enemies);
		for (Entity entity : playerHits) {
			Enemy enemy = (Enemy) entity;
			player.damage(enemy.getDamage());
			System.out.println("owie");

			if (player.getHealth() == 0) {
				System.out.println("ded");
				System.exit(0);
			}
		}

		if (totalEnemies == 0) {
			System.out.println("u win");
			System.exit(0);
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 800);
		level.currentRoom.renderGround(g, camera.getTranslation(), camera.getScale());
		level.currentRoom.renderBackground(g, camera.getTranslation(), camera.getScale());

		for (Entity enemy : level.currentRoom.enemies) {
			enemy.render(g, camera.getTranslation(), camera.getScale());
		}

		player.render(g, camera.getTranslation(), camera.getScale());

		level.currentRoom.renderForeground(g, camera.getTranslation(), camera.getScale());
	}

}