package game.game_objects;

import java.util.ArrayList;
import java.util.HashMap;

import game.game_objects.TileMap;

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @version 1.1
 * @since 1.1
 */
public class Map {
	
	public static final int NUMBER_OF_ROOMS = 5;
	public static final int MAX_CONNECTIONS = 2;

	public TileMap[] rooms;
	public TileMap currentRoom;

	public Map() {
		int ROOM_COUNT = (int) (Math.random() * NUMBER_OF_ROOMS) + 1;
		rooms = new TileMap[ROOM_COUNT];

		for (int i = 0; i < ROOM_COUNT; i++) {
			rooms[i] = new TileMap();
		}

		HashMap<String, String> directions = new HashMap<>();
		directions.put("up", "down");
		directions.put("down", "up");
		directions.put("left", "right");
		directions.put("right", "left");

		for (int i = 0; i < rooms.length; i++) {
			TileMap room = rooms[i];

			if (room.connections.size() < MAX_CONNECTIONS) {
				boolean makeConnection = false;
				int randomRoom = 0;

				for (int j = 0; j < 10; j++) {
					randomRoom = (int) (Math.random() * rooms.length);

					if (rooms[randomRoom].connections.size() < MAX_CONNECTIONS
						&& randomRoom != i
						&& !room.has(rooms[randomRoom])) {

						makeConnection = true;
						break;
					}
				}

				if (makeConnection) {
					int randomDirection = (int) (Math.random() * directions.size());
					String direction = String.valueOf(directions.keySet().toArray()[randomDirection]);

					ArrayList<Object> currentRoomArray = new ArrayList<>();
					currentRoomArray.add(rooms[randomRoom]);
					currentRoomArray.add(direction);

					ArrayList<Object> connectedRoomArray = new ArrayList<>();
					connectedRoomArray.add(room);
					connectedRoomArray.add(directions.get(direction));

					rooms[randomRoom].addEntrance(directions.get(direction));
					room.addEntrance(direction);

					room.connections.add(currentRoomArray);
					rooms[randomRoom].connections.add(connectedRoomArray);
				}

			}
		}

		currentRoom = rooms[0];
	}

}