package game.game_objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * An enum which stores all the images of the tiles that can be found in the levels.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public enum Tiles {
    GROUND ("./assets/Ground.png"),
    TOP_WALL ("./assets/Top.png"),
    BOTTOM_WALL ("./assets/Bottom.png"),
    LEFT_WALL ("./assets/LeftSide.png"),
    RIGHT_WALL ("./assets/RightSide.png"),
    LARGE_TOP_LEFT_CORNER ("./assets/TopLeftOuterCorner.png"),
    LARGE_TOP_RIGHT_CORNER ("./assets/TopRightOuterCorner.png"),
    LARGE_BOTTOM_LEFT_CORNER ("./assets/BottomLeftOuterCorner.png"),
    LARGE_BOTTOM_RIGHT_CORNER ("./assets/BottomRightOuterCorner.png"),
    SMALL_TOP_LEFT_CORNER ("./assets/TopLeftInnerCorner.png"),
    SMALL_TOP_RIGHT_CORNER ("./assets/TopRightInnerCorner.png"),
    SMALL_BOTTOM_LEFT_CORNER ("./assets/BottomLeftInnerCorner.png"),
    SMALL_BOTTOM_RIGHT_CORNER ("./assets/BottomRightInnerCorner.png"),
    TOP_DOOR ("./assets/TopDoor.png"),
    BOTTOM_DOOR ("./assets/BottomDoor.png"),
    RIGHT_DOOR ("./assets/RightDoor.png"),
    LEFT_DOOR ("./assets/LeftDoor.png");

    private BufferedImage image;

    /**
     * The constructor for the constants.
     *
     * @param pathName The paths to the images of the tiles.
     */
    Tiles(String pathName) {
        try {
            image = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A getter method for the image of the tile.
     *
     * @return The image of the tile.
     */
    public BufferedImage getImage() { return image; }

}
