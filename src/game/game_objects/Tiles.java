package game.game_objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    Tiles(String pathName) {
        try {
            image = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() { return image; }

}
