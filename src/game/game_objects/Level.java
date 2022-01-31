package game.game_objects;

import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Level {
    
    public static final int MAX_CONNECTIONS = 2;

    public TileMap[] rooms;
    public TileMap currentRoom;

    private int totalEnemies = 0;

    /**
     * The constructor for the game levels.
     *
     * @param difficulty The difficulty of the level.
     */
    public Level(int difficulty) {
        // Generate a random number of rooms.
        int ROOM_COUNT = (int) (Math.random() * difficulty) + 1;
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

                // Find a random room that the current room can be connected to.
                for (int j = 0; j < 10; j++)  {
                    randomRoom = (int) (Math.random() * rooms.length);

                    if (rooms[randomRoom].entrances.size() < MAX_CONNECTIONS
                        && randomRoom != i
                        && !room.connectedTo(rooms[randomRoom])) {

                        makeConnection = true;
                        break;
                    }
                }

                // Connect the current room with the random room.
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
            int numberOfEnemies = (int) (Math.random() * difficulty) + difficulty / 5 + 1;
            totalEnemies += numberOfEnemies;

            for (int i = 0; i < numberOfEnemies; i++) {
                int[] position = Positioning.generateRandomPositionWithin(room);
                room.enemies.add(new Enemy(position[0], position[1]));
            }
        }

        currentRoom = rooms[0];
    }

    /**
     * A method which removes an enemy from the list of enemies in a tilemap and thus decrements 
     * the total number of enemies.
     * 
     * @param entity The enemy which was killed.
     */
    public void killEntity(Entity entity) {
        currentRoom.enemies.remove(entity);
        totalEnemies--;
    }

    /**
     * A getter method for the total number of enemies in the level.
     */
    public int getTotalEnemies() { return totalEnemies; }

}