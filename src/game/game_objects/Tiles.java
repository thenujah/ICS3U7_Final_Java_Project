package game.game_objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum Tiles {
    GROUND ("./assets/ground.png"),
    TOP_WALL ("./assets/top_wall.png"),
    BOTTOM_WALL ("./assets/bottom_wall.png"),
    LEFT_WALL ("./assets/left_wall.png"),
    RIGHT_WALL ("./assets/right_wall.png"),
    LARGE_TOP_LEFT_CORNER ("./assets/large_top_left_corner.png"),
    LARGE_TOP_RIGHT_CORNER ("./assets/large_top_right_corner.png"),
    LARGE_BOTTOM_LEFT_CORNER ("./assets/large_bottom_left_corner.png"),
    LARGE_BOTTOM_RIGHT_CORNER ("./assets/large_bottom_right_corner.png"),
    SMALL_TOP_LEFT_CORNER ("./assets/small_top_left_corner.png"),
    SMALL_TOP_RIGHT_CORNER ("./assets/small_top_right_corner.png"),
    SMALL_BOTTOM_LEFT_CORNER ("./assets/small_bottom_left_corner.png"),
    SMALL_BOTTOM_RIGHT_CORNER ("./assets/small_bottom_right_corner.png");

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
