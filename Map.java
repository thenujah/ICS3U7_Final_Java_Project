package game.game_objects;

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

			if (room.doors.size() < MAX_CONNECTIONS) {
				boolean makeConnection = false;
				int randomRoom = 0;

				for (int j = 0; j < 10; j++)  {
					randomRoom = (int) (Math.random() * rooms.length);

					if (rooms[randomRoom].doors.size() < MAX_CONNECTIONS
						&& randomRoom != i
						&& !room.has(rooms[randomRoom])) {

						makeConnection = true;
						break;
					}
				}

				if (makeConnection) {
					int randomDirectionIndex = (int) (Math.random() * directions.size());
					String direction = String.valueOf(directions.keySet().toArray()[randomDirectionIndex]);
					
					System.out.println(direction);

					rooms[randomRoom].addEntrance(directions.get(direction), room);
					room.addEntrance(direction, rooms[randomRoom]);
				}

			}
		}

		for (TileMap room : rooms) {
			room.createColliders();

			for (int y = 0; y < room.map.length; y++) {
				for (int x = 0; x < room.map[y].length; x++) {
					System.out.printf("%-3d", room.map[y][x]);
				}
				System.out.println();
			}
		}

		currentRoom = rooms[0];
	}

}