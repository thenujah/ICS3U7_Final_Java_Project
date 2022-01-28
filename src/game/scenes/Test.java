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
	private Animation animation;
	// private Enemy[] enemies;
	// private ArrayList<Collider> entityColliders = new ArrayList<>();

	public Test(AppManager app) {
		super(app);

		camera = new Camera(2);
		player = new Player();
		level = new Map();

		// entityColliders.add(player.getCollider());

		// // Generate a random number of enemies in each room.
		// for (TileMap room : level.rooms) {
		// 	int numberOfEnemies = (int) (Math.random() * 6) + 3;
		// 	room.enemies = new Enemy[numberOfEnemies];

		// 	for (int i = 0; i < numberOfEnemies; i++) {
		// 		int[] position = Positioning.generateRandomPositionWithin(room);

		// 		int x = position[0];
		// 		int y = position[1];

		// 		room.enemies[i] = new Enemy(position[0], position[1]);
		// 		// entityColliders.add(room.enemies[i].getCollider());
		// 	}
		// }
	}

	public void update() {
		player.update(level.currentRoom, level);
		camera.update(player.getSprite());
		player.updateDirection(camera.getTranslation(), camera.getScale());

		// for (Enemy enemy : level.currentRoom.enemies) {
		// 	enemy.movement(player, level.currentRoom);
		// }
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 800);
		level.currentRoom.renderGround(g, camera.getTranslation(), camera.getScale());
		level.currentRoom.renderBackground(g, camera.getTranslation(), camera.getScale());

		// for (Enemy enemy : level.currentRoom.enemies) {
		// 	enemy.render(g, camera.getTranslation(), camera.getScale());
		// }

		player.render(g, camera.getTranslation(), camera.getScale());

		level.currentRoom.renderForeground(g, camera.getTranslation(), camera.getScale());
	}

}