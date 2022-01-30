package game.game_objects;

import java.util.HashMap;

import game.game_objects.TileMap;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @version 1.1
 * @since 1.1
 */
public class Level {
    
    public static final int NUMBER_OF_ROOMS = 5;
    public static final int MAX_CONNECTIONS = 2;

    public TileMap[] rooms;
    public TileMap currentRoom;

    private int totalEnemies = 0;

    public Level() {
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
                        * Positioning.directions.length);

                    Direction direction = Positioning.directions[randomDirectionIndex];

                    room.addEntrance(direction, rooms[randomRoom]);
                    rooms[randomRoom].addEntrance(
                        Positioning.oppositeDirections.get(direction), room);
                }

            }
        }

        for (TileMap room : rooms) {
            room.createColliders();

            // Generate a random number of enemies in each room.
            int numberOfEnemies = (int) (Math.random() * 3) + 3;
            totalEnemies += numberOfEnemies;

            for (int i = 0; i < numberOfEnemies; i++) {
                int[] position = Positioning.generateRandomPositionWithin(room);

                int x = position[0];
                int y = position[1];

                room.enemies.add(new Enemy(position[0], position[1]));
            }
        }

        currentRoom = rooms[0];
    }

    public void killEntity(Entity entity) {
        currentRoom.enemies.remove(entity);
        totalEnemies--;
    }

    public int getTotalEnemies() { return totalEnemies; }

}