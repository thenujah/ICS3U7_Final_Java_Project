package game.scenes;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.MouseInput;
import game.engine.util.KeyboardInput;
import game.game_objects.TileMap;
import game.game_objects.Map;
import game.engine.util.Button;

/**
 * A class which controls the game scene of the game.
 *
 * @version 1.0
 * @since 1.0
 */
public class Test extends Scene {

	private Map level;
	private ArrayList<Button> buttons = new ArrayList<>();
	private ArrayList<TileMap> connectedRooms = new ArrayList<>();

	public Test(AppManager app) {
		super(app);

		long startTime = System.currentTimeMillis();
		level = new Map();
		long endTime = System.currentTimeMillis();

		long duration = (endTime - startTime);

		System.out.println(duration);

		// System.out.println(Arrays.toString(level.rooms));

		for (int i = 0; i < level.currentRoom.connections.size(); i++) {
			Button button = new Button(100, 50);
			button.font = new Font("DialogInput", Font.PLAIN, 20);
			button.text = String.valueOf(level.currentRoom.connections.get(i).get(1));
			button.backgroundColor = Color.GRAY;
			button.setCenter(700, 100 + i * 100);
			buttons.add(button);

			connectedRooms.add((TileMap) level.currentRoom.connections.get(i).get(0));
		}

	}

	public void update() {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).isClicked()) {
				level.currentRoom = connectedRooms.get(i);
				buttons.clear();
				connectedRooms.clear();

				for (int j = 0; j < level.currentRoom.connections.size(); j++) {
					Button button = new Button(100, 50);
					button.font = new Font("DialogInput", Font.PLAIN, 20);
					button.text = String.valueOf(level.currentRoom.connections.get(j).get(1));
					button.backgroundColor = Color.GRAY;
					button.setCenter(700, 100 + j * 100);
					buttons.add(button);

					connectedRooms.add((TileMap) level.currentRoom.connections.get(j).get(0));
				}
			}
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 800);
		level.currentRoom.render(g);

		for (Button button : buttons) {
			button.render(g);
		}
	}

}