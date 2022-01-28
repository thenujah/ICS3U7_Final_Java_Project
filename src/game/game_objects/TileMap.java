package game.game_objects;

import java.awt.Color;
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
import game.game_objects.Entity;

/**
 * The TileMap class creates randomly generated rooms for each level.
 *
 * @version 3.0
 * @since 1.0
 */
public class TileMap {

	private static final int MAX_NUMBER_OF_RECTS = 3;

	private Rect[] rects;
	public Rect rect;

	public int[][] map;

	public ArrayList<Collider> walls = new ArrayList<>();
	public ArrayList<Object[]> entrances = new ArrayList<>();

	public ArrayList<Entity> enemies = new ArrayList<>();

	public TileMap() {
		int RECT_NUMBER = (int) (Math.random() * MAX_NUMBER_OF_RECTS) + 1;

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

	// TODO: Fix the bug which causes a 1 by X row/column to form in the tilemap and mess up the 
	// 	 border generation.

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

	public boolean connectedTo(TileMap tilemap) { // TODO: Refactor to 'connectedTo'
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
					row[x] = 1;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y + 1}))) { // top left inward
					row[x] = 2;
				} else if (contains(new int[]{x - 1, y - 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y - 1}))) { // bottom right inward
					row[x] = 3;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					!(contains(new int[]{x - 1, y}) || contains(new int[]{x, y + 1}))) { // top tight inward
					row[x] = 4;
				} else if (contains(new int[]{x + 1, y - 1}) &&
					!(contains(new int[]{x + 1, y}) || contains(new int[]{x, y - 1}))) { // bottom left inward
					row[x] = 5;
				} else if (contains(new int[]{x - 1, y - 1}) &&
						contains(new int[]{x - 1, y}) && contains(new int[]{x, y - 1})) { // top left outward
					row[x] = 6;
				} else if (contains(new int[]{x + 1, y - 1}) &&
						contains(new int[]{x + 1, y}) && contains(new int[]{x, y - 1})) { // top right outward
					row[x] = 7;
				} else if (contains(new int[]{x + 1, y + 1}) && 
					contains(new int[]{x + 1, y}) && contains(new int[]{x, y + 1})) { // bottom right outward
					row[x] = 8;
				} else if (contains(new int[]{x - 1, y + 1}) && 
					contains(new int[]{x - 1, y}) && contains(new int[]{x, y + 1})) { // bottom left outward
					row[x] = 9;
				} else if (contains(new int[]{x, y + 1})) { // up
					row[x] = 10;
				} else if (contains(new int[]{x - 1, y})) { // right
					row[x] = 11;
				} else if (contains(new int[]{x, y - 1})) { // down
					row[x] = 12;
				} else if (contains(new int[]{x + 1, y})) { // left
					row[x] = 13;
				} else {
					row[x] = 0;
				}
			}
			map[y] = row;
		}

		return map;
	}

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

	// TODO: Convert the switch cases to a HashMap or something similar.

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

	public void debug(Graphics2D g) {
		for (Collider tile : walls) {
			tile.debug(g);
		}

		for (Object[] entranceInfo : entrances) {
			Collider collider = (Collider) entranceInfo[0];
			collider.debug(g);
		}

		g.drawRect(rect.getX(), 
				   rect.getY(),
				   rect.getWidth(), 
				   rect.getHeight());
	}

}