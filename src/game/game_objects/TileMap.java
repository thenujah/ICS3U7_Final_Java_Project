package game.game_objects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Collections;

import game.engine.components.Rect;
import game.engine.components.Collider;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class TileMap {

	private static final int MAX_NUMBER_OF_RECTS = 3;

	private final Rect[] rects;
	public Rect rect;

	public int[][] map;

	public ArrayList<Collider> walls = new ArrayList<>();
	public ArrayList<Object[]> entrances = new ArrayList<>();
	public ArrayList<Entity> enemies = new ArrayList<>();

	/**
	 * The constructor for the TileMap. It generates random Rects that overlap each other to create
	 * random room shapes.
	 */
	public TileMap() {
		int RECT_NUMBER = (int) (Math.random() * MAX_NUMBER_OF_RECTS) + 1;

		rects = new Rect[RECT_NUMBER];

		// Generating the Rects used to give the room a random shape.
		for (int i = 0; i < RECT_NUMBER; i++) {
			rects[i] = generateRect();
		}

		// Finding the bounds of the entire tilemap.
		Integer[] lowestXCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			lowestXCoordinates[i] = rects[i].getLeft();
		int left = Collections.min(Arrays.asList(lowestXCoordinates));

		Integer[] lowestYCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			lowestYCoordinates[i] = rects[i].getTop();
		int top = Collections.min(Arrays.asList(lowestYCoordinates));

		Integer[] highestXCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			highestXCoordinates[i] = rects[i].getRight();
		int right = Collections.max(Arrays.asList(highestXCoordinates));

		Integer[] highestYCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			highestYCoordinates[i] = rects[i].getBottom();
		int bottom = Collections.max(Arrays.asList(highestYCoordinates));

		// Moving the room to the top right corner to remove any whitespace.
		for (Rect rect : rects) {
			rect.setTopLeft(rect.getX() - left + 1, rect.getY() - top + 1);
		}

		right -= left - 1;
		bottom -= top - 1;
		left = top = 1;

		rect = new Rect(top * Positioning.TILE_SIZE, left * Positioning.TILE_SIZE,
			(right - left) * Positioning.TILE_SIZE, (bottom - top) * Positioning.TILE_SIZE);

		map = generateBorders(right, bottom);
	}

	/**
	 * A method which generates random Rects used to form the rooms.
	 *
	 * @return A randomly generated Rect object
	 */
	private Rect randomRect() {
		final int MIN_SIZE = 4;
		final int MAX_SIZE = 12;

		int width = (int) (Math.random() * (MAX_SIZE - MIN_SIZE)) + MIN_SIZE;
		int height = (int) (Math.random() * (MAX_SIZE - MIN_SIZE)) + MIN_SIZE;
		int x = (int) (Math.random() * (MAX_SIZE - width + 1));
		int y = (int) (Math.random() * (MAX_SIZE - height + 1));

		return new Rect(x, y, width, height);
	}

	/**
	 * A method which generates a random Rect that overlaps any pre-existing Rects in the room.
	 *
	 * @return The randomly generated Rect.
	 */
	private Rect generateRect() {
		if (Arrays.stream(rects).allMatch(Objects::isNull)) {
			return randomRect();
		}

		while (true) {
			Rect newRect = randomRect();
		
			boolean overlaps = false;

			// Check if the Rect generated overlaps with the other Rects that already exist.
			for (Rect rect : rects) {
				if (rect != null) {

					if (newRect.isWithin(rect) || rect.isWithin(newRect)) break;

					if (rect.contains(newRect.getTopLeft())
						|| rect.contains(newRect.getTopRight())
						|| rect.contains(newRect.getBottomLeft())
						|| rect.contains(newRect.getBottomRight())) {

						overlaps = true;
						break;
					}

				}
			}
	
			if (overlaps) {
				return newRect;
			}
		}
	}

	/**
	 * A method which checks if a position is within the tile map.
	 *
	 * @param pos The point.
	 * @return A boolean representing if the point is contained within the borders of the map.
	 */
	public boolean contains(int[] pos) {
		for (Rect rect : rects) {
			if (rect.contains(pos)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method which checks if this TileMap is connected to another TileMap by an entrance.
	 *
	 * @param tilemap The other TileMap.
	 * @return A boolean stating if the TileMaps are connected.
	 */
	public boolean connectedTo(TileMap tilemap) {
		for (Object[] list : entrances) {
			if (tilemap == list[1]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * A method which generates the borders of the TileMap based on the randomly generates Rects.
	 *
	 * @param rightBound The highest x value a tile can have.
	 * @param bottomBound The highest y value a tile can have.
	 * @return The map generated.
	 */
	private int[][] generateBorders(int rightBound, int bottomBound) {
		map = new int[bottomBound + 2][rightBound + 2];

		for (int y = 0; y < map.length; y++) {
			int[] row = new int[rightBound + 2];

			for (int x = 0; x < row.length; x++) {

				if (contains(new int[]{x, y})) { // Ground tile
					row[x] = 1;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y + 1}))) { // top left inward tile
					row[x] = 2;
				} else if (contains(new int[]{x - 1, y - 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y - 1}))) { // bottom right inward tile
					row[x] = 3;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y + 1}))) { // top tight inward tile
					row[x] = 4;
				} else if (contains(new int[]{x + 1, y - 1}) &&
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y - 1}))) { // bottom left inward tile
					row[x] = 5;
				} else if (contains(new int[]{x - 1, y - 1}) &&
						contains(new int[]{x - 1, y}) && contains(new int[]{x, y - 1})) { // top left outward tile
					row[x] = 6;
				} else if (contains(new int[]{x + 1, y - 1}) &&
						contains(new int[]{x + 1, y}) && contains(new int[]{x, y - 1})) { // top right outward tile
					row[x] = 7;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					contains(new int[]{x + 1, y}) && contains(new int[]{x, y + 1})) { // bottom right outward tile
					row[x] = 8;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					contains(new int[]{x - 1, y}) && contains(new int[]{x, y + 1})) { // bottom left outward tile
					row[x] = 9;
				} else if (contains(new int[]{x, y + 1})) { // top tile
					row[x] = 10;
				} else if (contains(new int[]{x - 1, y})) { // right tile
					row[x] = 11;
				} else if (contains(new int[]{x, y - 1})) { // bottom tile
					row[x] = 12;
				} else if (contains(new int[]{x + 1, y})) { // left tile
					row[x] = 13;
				} else {
					row[x] = 0;
				}
			}
			map[y] = row;
		}

		return map;
	}

	/**
	 * A method used to add entrances from one TileMap that lead to another.
	 *
	 * @param direction Side of the tilemap that the entrance can be located on.
	 * @param connectingRoom The room on the other side of the entrance.
	 */
	public void addEntrance(Direction direction, TileMap connectingRoom) {
		int maxPosition, xPos, yPos;
		boolean positionSet = false;
		xPos = yPos = 0;

		if (direction == Direction.UP || direction == Direction.DOWN) maxPosition = map[0].length - 2;
		else maxPosition = map.length - 2;

		do {
			int position = (int) (Math.random() * maxPosition) + 1;

			int width, height;
			width = height = Positioning.TILE_SIZE;

			switch (direction) {
				case UP:
					xPos = position * Positioning.TILE_SIZE;
					for (int y = 0; y < map.length; y++) {
						if (map[y][position] == 10) {
							map[y][position] = 14;
							positionSet = true;
							yPos = y * Positioning.TILE_SIZE;
							height /= 2;
							break;
						}
					}
					break;
				case DOWN:
					xPos = position * Positioning.TILE_SIZE;
					for (int y = 0; y < map.length; y++) {
						if (map[y][position] == 12) {
							map[y][position] = 15;
							positionSet = true;
							yPos = y * Positioning.TILE_SIZE + height / 2;
							height /= 2;
							break;
						}
					}
					break;
				case RIGHT:
					yPos = position * Positioning.TILE_SIZE;
					for (int x = 0; x < map[position].length; x++) {
						if (map[position][x] == 11) {
							map[position][x] = 16;
							positionSet = true;
							xPos = x * Positioning.TILE_SIZE;
							break;
						}
					}
					break;
				case LEFT:
					yPos = position * Positioning.TILE_SIZE;
					for (int x = 0; x < map[position].length; x++) {
						if (map[position][x] == 13) {
							map[position][x] = 17;
							positionSet = true;
							xPos = x * Positioning.TILE_SIZE;
							break;
						}
					}
					break;
			}

			if (positionSet)
				entrances.add(new Object[]{
						new Collider(xPos, yPos, width, height),
						connectingRoom,
						direction });

		} while (!positionSet);
	}

	/**
	 * A method which generates the colliders for the borders of the room.
	 */
	public void createColliders() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {

				int tile = map[y][x];

				int[] position = { x * Positioning.TILE_SIZE, y * Positioning.TILE_SIZE };
				int width, height;
				width = height = Positioning.TILE_SIZE;

				switch (tile) {
					case 2, 3, 4, 5, 11, 13 -> walls.add(new Collider(position[0], position[1], width, height));
					case 6, 7, 12 -> walls.add(new Collider(position[0], position[1] + height / 2, width, height / 2));
					case 8, 9, 10 -> walls.add(new Collider(position[0], position[1], width, height / 2));
				}
			}
		}

	}

	/**
	 * A method which renders all the ground tiles of the TileMap.
	 *
	 * @param g The Graphics2D object used to draw the images to the screen.
	 * @param translation How much each image needs to be translated.
	 * @param scale How much each image should be scaled.
	 */
	public void renderGround(Graphics2D g, int[] translation, double scale) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int tile = map[y][x];
				
				BufferedImage image = switch (tile) {
					case 0 -> null;
					default -> Tiles.GROUND.getImage();
				};
			
				if (image != null) {
					AffineTransform transform = new AffineTransform();
					transform.translate(x * Positioning.TILE_SIZE * scale - translation[0],
										y * Positioning.TILE_SIZE * scale - translation[1]);
					transform.scale(scale, scale);
					g.drawImage(image, transform, null);
				}

			}
		}
	}

	/**
	 * A method which renders all tiles which should appear behind the Player.
	 *
	 * @param g The Graphics2D object used to draw the images to the screen.
	 * @param translation How much each image needs to be translated.
	 * @param scale How much each image should be scaled.
	 */
	public void renderBackground(Graphics2D g, int[] translation, double scale) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int tile = map[y][x];
				
				BufferedImage image = switch (tile) {
					case 2 -> Tiles.LARGE_TOP_LEFT_CORNER.getImage();
					case 4 -> Tiles.LARGE_TOP_RIGHT_CORNER.getImage();
					case 8 -> Tiles.SMALL_TOP_RIGHT_CORNER.getImage();
					case 9 -> Tiles.SMALL_TOP_LEFT_CORNER.getImage();
					case 10 -> Tiles.TOP_WALL.getImage();
					case 11 -> Tiles.RIGHT_WALL.getImage();
					case 13 -> Tiles.LEFT_WALL.getImage();
					case 14 -> Tiles.TOP_DOOR.getImage();
					case 16 -> Tiles.RIGHT_DOOR.getImage();
					case 17 -> Tiles.LEFT_DOOR.getImage();
					default -> null;
				};

				
				if (image != null) {
					AffineTransform transform = new AffineTransform();
					transform.translate(x * Positioning.TILE_SIZE * scale - translation[0],
										y * Positioning.TILE_SIZE * scale - translation[1]);
					transform.scale(scale, scale);
					g.drawImage(image, transform, null);
				}
			}
		}
	}

	/**
	 * A method which renders all tiles which should appear in front the Player.
	 *
	 * @param g The Graphics2D object used to draw the images to the screen.
	 * @param translation How much each image needs to be translated.
	 * @param scale How much each image should be scaled.
	 */
	public void renderForeground(Graphics2D g, int[] translation, double scale) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int tile = map[y][x];

				BufferedImage image = switch (tile) {
					case 3 -> Tiles.LARGE_BOTTOM_RIGHT_CORNER.getImage();
					case 5 -> Tiles.LARGE_BOTTOM_LEFT_CORNER.getImage();
					case 6 -> Tiles.SMALL_BOTTOM_LEFT_CORNER.getImage();
					case 7 -> Tiles.SMALL_BOTTOM_RIGHT_CORNER.getImage();
					case 12 -> Tiles.BOTTOM_WALL.getImage();
					case 15 -> Tiles.BOTTOM_DOOR.getImage();
					default -> null;
				};

				if (image != null) {
					AffineTransform transform = new AffineTransform();
					transform.translate(x * Positioning.TILE_SIZE * scale - translation[0],
							y * Positioning.TILE_SIZE * scale - translation[1]);
					transform.scale(scale, scale);
					g.drawImage(image, transform, null);
				}
			}
		}
	}

}