package game.game_objects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Collections;

import game.engine.components.Rect;
import game.game_objects.Tiles;

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

	private final Rect[] rects;
	private int topX, topY, botX, botY;
	public int[][] map;
	public ArrayList<ArrayList<Object>> connections = new ArrayList<>();

	public TileMap() {
		int RECT_NUMBER = (int) (Math.random() * NUMBER_OF_RECTS) + 1;

		rects = new Rect[RECT_NUMBER];

		for (int i = 0; i < RECT_NUMBER; i++) {
			rects[i] = generateRect();
		}

		Integer[] txCoords = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++) txCoords[i] = rects[i].getLeft();
		topX = Collections.min(Arrays.asList(txCoords));

		Integer[] tyCoords = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++) tyCoords[i] = rects[i].getTop();
		topY = Collections.min(Arrays.asList(tyCoords));

		Integer[] bxCoords = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++) bxCoords[i] = rects[i].getRight();
		botX = Collections.max(Arrays.asList(bxCoords));

		Integer[] byCoords = new Integer[rects.length];
		for (int i = 0; i < rects.length; i++) byCoords[i] = rects[i].getBottom();
		botY = Collections.max(Arrays.asList(byCoords));

		removeWhiteSpace();

		map = generateBorders();

		// for (int y = 0; y < map.length; y++) {
		// 	for (int x = 0; x < map[y].length; x++) {
		// 		System.out.printf("%-3d", map[y][x]);
		// 	}
		// 	System.out.println();
		// }
	}

	private static Rect randomRect() {
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
			return TileMap.randomRect();
		}

		while (true) {
			Rect newRect = TileMap.randomRect();
		
			boolean overlaps = false;
			boolean within = false;

			for (Rect rect : rects) {
				if (rect != null) {
					if (newRect.isWithin(rect) || rect.isWithin(newRect)) {
						within = true;
						break;
					}

					if (rect.contains(newRect.getTopLeft()) || rect.contains(newRect.getTopRight()) ||
							rect.contains(newRect.getBottomLeft()) || rect.contains(newRect.getBottomRight())) {

						overlaps = true;
						break;
					}
				}
			}
	
			if (overlaps && !within) {
				return newRect;
			}
		}
	}

	private void removeWhiteSpace() {
		for (Rect rect : rects) {
			rect.setTopLeft(rect.getX() - topX + 1, rect.getY() - topY + 1);
		}

		botX -= topX - 1;
		botY -= topY - 1;
		topX = topY = 1;
	}

	public boolean contains(int[] pos) {
		for (Rect rect : rects) {
			if (rect.contains(pos)) {
				return true;
			}
		}
		return false;
	}

	public int[][] generateBorders() {
		map = new int[botY + 2][botX + 2];

		for (int y = 0; y < map.length; y++) {
			int[] row = new int[botX + 2];

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

	public void render(Graphics2D g) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int tile = map[y][x];

				int scale = 1; // Set to 3 later

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
		while (true) {
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

			if (positionSet) {
				break;
			}
		}
	}

	public boolean has(TileMap tilemap) {
		for (ArrayList<Object> list : connections) {
			if (tilemap == list.get(0)) {
				return true;
			}
		}

		return false;
	}

}