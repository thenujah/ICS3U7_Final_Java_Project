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

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @version 1.1
 * @since 1.0
 */
public class TileMap {

	public static final int SIZE = 16;
	public static final int TILE_SIZE = 32;
	public static final int NUMBER_OF_RECTS = 3;

	private Rect[] rects;
	private final int scale = 1;

	public int[][] map;

	public ArrayList<Collider> walls = new ArrayList<>();
	public ArrayList<Object[]> entrances = new ArrayList<>();

	public TileMap() {
		int RECT_NUMBER = (int) (Math.random() * NUMBER_OF_RECTS) + 1;

		rects = new Rect[RECT_NUMBER];

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

		map = generateBorders(right, bottom);

		// for (int y = 0; y < map.length; y++) {
		// 	for (int x = 0; x < map[y].length; x++) {
		// 		System.out.printf("%-3d", map[y][x]);
		// 	}
		// 	System.out.println();
		// }
	}

	/**
	 * A method which generates random Rects used to form the rooms.
	 *
	 * @return A randomly generated Rect object
	 */
	private Rect randomRect() {
		final int MIN_SIZE = 4;
		final int MAX_SIZE = TileMap.SIZE - 4;

		int width = (int) (Math.random() * (MAX_SIZE - MIN_SIZE)) + MIN_SIZE;
		int height = (int) (Math.random() * (MAX_SIZE - MIN_SIZE)) + MIN_SIZE;
		int x = (int) (Math.random() * (MAX_SIZE - width + 1));
		int y = (int) (Math.random() * (MAX_SIZE - height + 1));

		return new Rect(x, y, width, height);
	}

	private Rect generateRect() {
		if (Arrays.stream(rects).allMatch(Objects::isNull)) {
			return randomRect();
		}

		while (true) {
			Rect newRect = randomRect();
		
			boolean overlaps = false;

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

	public boolean has(TileMap tilemap) {
		for (Object[] list : entrances) {
			if (tilemap == list[1]) {
				return true;
			}
		}

		return false;
	}

	public int[][] generateBorders(int rightBound, int bottomBound) {
		map = new int[bottomBound + 2][rightBound + 2];

		for (int y = 0; y < map.length; y++) {
			int[] row = new int[rightBound + 2];

			for (int x = 0; x < row.length; x++) {

				if (contains(new int[]{x, y})) { // inner
					row[x] = 0;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y + 1}))) { // top left inward
					row[x] = 1;
				} else if (contains(new int[]{x - 1, y - 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y - 1}))) { // bottom right inward
					row[x] = 2;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y + 1}))) { // top tight inward
					row[x] = 3;
				} else if (contains(new int[]{x + 1, y - 1}) &&
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y - 1}))) { // bottom left inward
					row[x] = 4;
				} else if (contains(new int[]{x - 1, y - 1}) &&
						contains(new int[]{x - 1, y}) && contains(new int[]{x, y - 1})) { // top left outward
					row[x] = 5;
				} else if (contains(new int[]{x + 1, y - 1}) &&
						contains(new int[]{x + 1, y}) && contains(new int[]{x, y - 1})) { // top right outward
					row[x] = 6;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					contains(new int[]{x + 1, y}) && contains(new int[]{x, y + 1})) { // bottom right outward
					row[x] = 7;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					contains(new int[]{x - 1, y}) && contains(new int[]{x, y + 1})) { // bottom left outward
					row[x] = 8;
				} else if (contains(new int[]{x, y + 1})) { // top
					row[x] = 9;
				} else if (contains(new int[]{x - 1, y})) { // right
					row[x] = 10;
				} else if (contains(new int[]{x, y - 1})) { // bottom
					row[x] = 11;
				} else if (contains(new int[]{x + 1, y})) { // left
					row[x] = 12;
				} else {
					row[x] = 13;
				}
			}
			map[y] = row;
		}

		return map;
	}

	public void addEntrance(String direction, TileMap connectingRoom) {
		System.out.println(direction);

		int maxPosition, xPos, yPos;
		boolean positionSet = false;
		xPos = yPos = 0;

		if (direction.equals("up") || direction.equals("down")) maxPosition = map[0].length - 2;
		else maxPosition = map.length - 2;

		do {
			int position = (int) (Math.random() * maxPosition) + 1;

			xPos = yPos = 0;

			switch (direction) {
				case "up":
					xPos = position;
					for (int y = 0; y < map.length; y++) {
						if (map[y][position] == 9) {
							map[y][position] = 14;
							positionSet = true;
							yPos = y;
							break;
						}
					}
					break;
				case "down":
					xPos = position;
					for (int y = 0; y < map.length; y++) {
						if (map[y][position] == 11) {
							map[y][position] = 15;
							positionSet = true;
							yPos = y;
							break;
						}
					}
					break;
				case "right":
					yPos = position;
					for (int x = 0; x < map[position].length; x++) {
						if (map[position][x] == 10) {
							map[position][x] = 16;
							positionSet = true;
							xPos = x;
							break;
						}
					}
					break;
				case "left":
					yPos = position;
					for (int x = 0; x < map[position].length; x++) {
						if (map[position][x] == 12) {
							map[position][x] = 17;
							positionSet = true;
							xPos = x;
							break;
						}
					}
					break;
			}

			int[] coordinates = { xPos * TILE_SIZE * scale, yPos * TILE_SIZE * scale };
			int width, height;
			width = height = TILE_SIZE * scale;

			if (positionSet)
				entrances.add(new Object[]{
						new Collider(coordinates[0], coordinates[1], width, height),
						connectingRoom,
						direction });

		} while (!positionSet);
	}

	public void createColliders() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {

				int tile = map[y][x];

				int[] position = { x * TILE_SIZE * scale, y * TILE_SIZE * scale };
				int width, height;
				width = height = TILE_SIZE * scale;

				switch (tile) {
					case 1, 2, 3, 4, 10, 12 -> walls.add(new Collider(position[0], position[1], width, height));
					case 5, 6, 11 -> walls.add(new Collider(position[0], position[1] + height / 2, width, height / 2));
					case 7, 8, 9 -> walls.add(new Collider(position[0], position[1], width, height / 2));
				}
			}
		}

	}

	public void render(Graphics2D g) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int tile = map[y][x];

				AffineTransform transform = new AffineTransform();
				transform.translate(x * TILE_SIZE * scale, y * TILE_SIZE * scale);
				transform.scale(scale, scale);

				BufferedImage image = switch (tile) {
					case 0 -> Tiles.GROUND.getImage();
					case 1 -> Tiles.LARGE_TOP_LEFT_CORNER.getImage();
					case 2 -> Tiles.LARGE_BOTTOM_RIGHT_CORNER.getImage();
					case 3 -> Tiles.LARGE_TOP_RIGHT_CORNER.getImage();
					case 4 -> Tiles.LARGE_BOTTOM_LEFT_CORNER.getImage();
					case 5 -> Tiles.SMALL_BOTTOM_RIGHT_CORNER.getImage();
					case 6 -> Tiles.SMALL_BOTTOM_LEFT_CORNER.getImage();
					case 7 -> Tiles.SMALL_TOP_LEFT_CORNER.getImage();
					case 8 -> Tiles.SMALL_TOP_RIGHT_CORNER.getImage();
					case 9 -> Tiles.TOP_WALL.getImage();
					case 10 -> Tiles.RIGHT_WALL.getImage();
					case 11 -> Tiles.BOTTOM_WALL.getImage();
					case 12 -> Tiles.LEFT_WALL.getImage();
					default -> null;
				};
			
				g.drawImage(image, transform, null);
			}
		}

		for (Collider tile : walls) {
			tile.debug(g);
		}

		for (Object[] entranceInfo : entrances) {
			Collider collider = (Collider) entranceInfo[0];
			collider.debug(g);
		}
	}

}