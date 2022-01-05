package game.gameObjects;

import java.util.Arrays;
import java.util.Objects;

import game.engine.components.Rect;

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @version 1.0
 * @since 1.0
 */
public class TileMap {
	
	public static final int SIZE = 16;
	public static final int NUMBER_OF_RECTS = 3;

	public Rect[] rects;
	public int topX, topY, botX, botY;
	public int[][] map;

	public TileMap() {
		int RECT_NUMBER = (int) (Math.random() * NUMBER_OF_RECTS) + 1;

		rects = new Rect[RECT_NUMBER];

		for (int i = 0; i < RECT_NUMBER; i++) {
			rects[i] = generateRect();
		}

		// TODO: Add the side variables and map.
	}

	private static Rect randomRect() {
		final int MIN_SIZE = 3;
		final int MAX_SIZE = TileMap.SIZE - 5;

		int width = (int) (Math.random() * (MAX_SIZE - MIN_SIZE)) + MIN_SIZE;
		int height = (int) (Math.random() * (MAX_SIZE - MIN_SIZE)) + MIN_SIZE;
		int x = (int) (Math.random() * (MAX_SIZE - width));
		int y = (int) (Math.random() * (MAX_SIZE - height));

		return new Rect(x, y, width, height);
	}

	private Rect generateRect() {
		if (Arrays.stream(rects).allMatch(Objects::nonNull)) {
			return TileMap.randomRect();
		}

		while (true) {
			Rect newRect = TileMap.randomRect();
		
			boolean overlaps = false;
			boolean within = false;
	
			for (Rect rect : rects) {
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
	
			if (overlaps && !within) {
				return newRect;
			}
		}
	}

	private void removeWhiteSpace() {
		for (Rect rect : rects) {
			rect.update(rect.getX() - topX + 1, rect.getY() - topY + 1, rect.getWidth(), rect.getHeight());
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
				if (contains(new int[]{x, y})) {
					row[x] = 0;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y + 1}))) {
					row[x] = 1;
				} else if (contains(new int[]{x - 1, y - 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y - 1}))) {
					row[x] = 2;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y + 1}))) {
					row[x] = 3;
				} else if (contains(new int[]{x + 1, y - 1}) && 
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y - 1}))) {
					row[x] = 4;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					contains(new int[]{x + 1, y}) && contains(new int[]{x, y + 1})) {
					row[x] = 5;
				} else if (contains(new int[]{x - 1, y - 1}) && 
					contains(new int[]{x - 1, y}) && contains(new int[]{x, y - 1})) {
					row[x] = 6;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					contains(new int[]{x - 1, y}) && contains(new int[]{x, y + 1})) {
					row[x] = 7;
				} else if (contains(new int[]{x + 1, y - 1}) && 
					contains(new int[]{x + 1, y}) && contains(new int[]{x, y - 1})) {
					row[x] = 8;
				} else if (contains(new int[]{x, y + 1})) {
					row[x] = 9;
				} else if (contains(new int[]{x, y - 1})) {
					row[x] = 10;
				} else if (contains(new int[]{x + 1, y})) {
					row[x] = 11;
				} else if (contains(new int[]{x + 1, y})) {
					row[x] = 12;
				} else {
					row[x] = 13;
				}
			}

			map[y] = row;
		}

		return map;
	}

}