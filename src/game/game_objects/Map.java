package game.game_objects;

import java.util.HashMap;

import game.game_objects.TileMap;
import game.engine.util.Positioning;

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

		// Connect the rooms with each other.
		for (int i = 0; i < rooms.length; i++) {
			TileMap room = rooms[i];

			if (room.entrances.size() < MAX_CONNECTIONS) {
				boolean makeConnection = false;
				int randomRoom = 0;

				for (int j = 0; j < 10; j++)  {
					randomRoom = (int) (Math.random() * rooms.length);

					if (rooms[randomRoom].entrances.size() < MAX_CONNECTIONS
						&& randomRoom != i
						&& !room.connectedTo(rooms[randomRoom])) {

						makeConnection = true;
						break;
					}
				}

				if (makeConnection) {
					int randomDirectionIndex = (int) (Math.random() 
						* Positioning.directions.size());

					String direction = Positioning.directions.get(randomDirectionIndex);

					room.addEntrance(direction, rooms[randomRoom]);
					rooms[randomRoom].addEntrance(
						Positioning.oppositeDirections.get(direction), room);
				}

			}
		}

		for (TileMap room : rooms) {
			room.createColliders();
		}

		currentRoom = rooms[0];
	}

}