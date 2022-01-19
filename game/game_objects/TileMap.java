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
	private int left, top, right, bottom;
	private int[][] map;  // Will be depricated.
	private int[][] ground;
	private int[][] background;
	private int[][] foreground;
	private final int scale = 1;
	public ArrayList<ArrayList<Object>> connections = new ArrayList<>();
	public ArrayList<Collider> walls = new ArrayList<>();
	public ArrayList<Collider> doors = new ArrayList<>();

	public TileMap() {
		int RECT_NUMBER = (int) (Math.random() * NUMBER_OF_RECTS) + 1;

		rects = new Rect[RECT_NUMBER];

		for (int i = 0; i < RECT_NUMBER; i++) {
			rects[i] = generateRect();
		}

		Integer[] lowestXCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			lowestXCoordinates[i] = rects[i].getLeft();
		left = Collections.min(Arrays.asList(lowestXCoordinates));

		Integer[] lowestYCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			lowestYCoordinates[i] = rects[i].getTop();
		top = Collections.min(Arrays.asList(lowestYCoordinates));

		Integer[] highestXCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			highestXCoordinates[i] = rects[i].getRight();
		right = Collections.max(Arrays.asList(highestXCoordinates));

		Integer[] highestYCoordinates = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++)
			highestYCoordinates[i] = rects[i].getBottom();
		bottom = Collections.max(Arrays.asList(highestYCoordinates));

		removeWhiteSpace();

		map = generateBorders();

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
		final int MIN_SIZE = 3;
		final int MAX_SIZE = TileMap.SIZE - 5;

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

	private void removeWhiteSpace() {
		for (Rect rect : rects) {
			rect.setTopLeft(rect.getX() - left + 1, rect.getY() - top + 1);
		}

		right -= left - 1;
		bottom -= top - 1;
		left = top = 1;
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
		for (ArrayList<Object> list : connections) {
			if (tilemap == list.get(0)) {
				return true;
			}
		}

		return false;
	}

	public int[][] generateBorders() {
		map = new int[bottom + 2][right + 2];

		for (int y = 0; y < map.length; y++) {
			int[] row = new int[right + 2];

			for (int x = 0; x < row.length; x++) {

				int[] position = { x * TILE_SIZE * scale, y * TILE_SIZE * scale };
				int width, height;
				width = height = TILE_SIZE * scale;

				if (contains(new int[]{x, y})) { // inner
					row[x] = 0;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y + 1}))) { // top left inward
					row[x] = 1;
					walls.add(new Collider(position[0], position[1], width, height));
				} else if (contains(new int[]{x - 1, y - 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y - 1}))) { // bottom right inward
					row[x] = 2;
					walls.add(new Collider(position[0], position[1], width, height));
				} else if (contains(new int[]{x - 1, y + 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y + 1}))) { // top tight inward
					row[x] = 3;
					walls.add(new Collider(position[0], position[1], width, height));
				} else if (contains(new int[]{x + 1, y - 1}) &&
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y - 1}))) { // bottom left inward
					row[x] = 4;
					walls.add(new Collider(position[0], position[1], width, height));
				} else if (contains(new int[]{x - 1, y - 1}) &&
						contains(new int[]{x - 1, y}) && contains(new int[]{x, y - 1})) { // top left outward
					row[x] = 5;
					walls.add(new Collider(position[0], position[1] + height / 2, width, height / 2));
				} else if (contains(new int[]{x + 1, y - 1}) &&
						contains(new int[]{x + 1, y}) && contains(new int[]{x, y - 1})) { // top right outward
					row[x] = 6;
					walls.add(new Collider(position[0], position[1] + height / 2, width, height / 2));
				} else if (contains(new int[]{x + 1, y + 1}) && 
					contains(new int[]{x + 1, y}) && contains(new int[]{x, y + 1})) { // bottom right outward
					row[x] = 7;
					walls.add(new Collider(position[0], position[1], width, height / 2));
				} else if (contains(new int[]{x - 1, y + 1}) && 
					contains(new int[]{x - 1, y}) && contains(new int[]{x, y + 1})) { // bottom left outward
					row[x] = 8;
					walls.add(new Collider(position[0], position[1], width, height / 2));
				} else if (contains(new int[]{x, y + 1})) { // top
					row[x] = 9;
					walls.add(new Collider(position[0], position[1], width, height / 2));
				} else if (contains(new int[]{x - 1, y})) { // right
					row[x] = 10;
					walls.add(new Collider(position[0], position[1], width, height));
				} else if (contains(new int[]{x, y - 1})) { // bottom
					row[x] = 11;
					walls.add(new Collider(position[0], position[1] + height / 2, width, height / 2));
				} else if (contains(new int[]{x + 1, y})) { // left
					row[x] = 12;
					walls.add(new Collider(position[0], position[1], width, height));
				} else {
					row[x] = 13;
				}
			}
			map[y] = row;
		}

		return map;
	}

	public void render(Graphics2D g) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int tile = map[y][x];

				// int scale = 1; // Set to 3 later

				AffineTransform transform = new AffineTransform();
				transform.translate(x * TILE_SIZE * scale, y * TILE_SIZE * scale);
				transform.scale(scale, scale);

//				BufferedImage image = switch (tile) {
//					case 0 -> Tiles.GROUND.getImage();
//					case 1 -> Tiles.LARGE_TOP_LEFT_CORNER.getImage();
//					case 2 -> Tiles.LARGE_BOTTOM_RIGHT_CORNER.getImage();
//					case 3 -> Tiles.LARGE_TOP_RIGHT_CORNER.getImage();
//					case 4 -> Tiles.LARGE_BOTTOM_LEFT_CORNER.getImage();
//					case 5 -> Tiles.SMALL_BOTTOM_RIGHT_CORNER.getImage();
//					case 6 -> Tiles.SMALL_BOTTOM_LEFT_CORNER.getImage();
//					case 7 -> Tiles.SMALL_TOP_LEFT_CORNER.getImage();
//					case 8 -> Tiles.SMALL_TOP_RIGHT_CORNER.getImage();
//					case 9 -> Tiles.TOP_WALL.getImage();
//					case 10 -> Tiles.RIGHT_WALL.getImage();
//					case 11 -> Tiles.BOTTOM_WALL.getImage();
//					case 12 -> Tiles.LEFT_WALL.getImage();
//					default -> null;
//				};

				g.drawImage(null, transform, null);
			}
		}

		for (Collider tile : walls) {
			tile.debug(g);
		}
	}

	public void addEntrance(String direction) {
		int maxPosition;
		boolean positionSet = false;

		if (direction.equals("up") || direction.equals("down")) maxPosition = map[0].length - 2;
		else maxPosition = map.length - 2;

		// System.out.println("DIRECTION " + direction);
		// System.out.println("MAX POSITION " + maxPosition);
		// System.out.println("VERTICAL MAX POSITION " + map.length);
		// System.out.println("HORIZONAL MAX POSITION " + map[0].length);

		// TODO: Store the locations of the entrances so that special colliders can be added to them.
		do {
			int position = (int) (Math.random() * maxPosition) + 1;
			// System.out.println(position);

			switch (direction) {
				case "up":
					for (int y = 0; y < map.length; y++) {
						if (map[y][position] == 9) {
							map[y][position] = 13;
							positionSet = true;
						}
					}
					break;
				case "down":
					for (int y = 0; y < map.length; y++) {
						if (map[y][position] == 11) {
							map[y][position] = 14;
							positionSet = true;
						}
					}
					break;
				case "right":
					for (int x = 0; x < map[position].length; x++) {
						if (map[position][x] == 10) {
							map[position][x] = 15;
							positionSet = true;
						}
					}
					break;
				case "left":
					for (int x = 0; x < map[position].length; x++) {
						if (map[position][x] == 12) {
							map[position][x] = 16;
							positionSet = true;
						}
					}
					break;
			}

		} while (!positionSet);
	}

}