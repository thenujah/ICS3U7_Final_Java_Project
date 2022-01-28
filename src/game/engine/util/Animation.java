package game.engine.util;

// TODO: Move to components.

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.engine.components.Rect;
import game.engine.util.Positioning;
import game.engine.util.Positioning.Direction;


/**
 * A class which stores frames of an animation.
 */
public class Animation {

    public static int FPS = 60;

    private final ArrayList<BufferedImage> frames = new ArrayList<>();
    private final int interval;

    private int duration = 0;
    private int currentFrame = 0;
    private boolean isPlaying = false;
    private Direction direction = Direction.DOWN;

    private final Rect rect;

    /**
     * The constructor for the Animation class.
     * 
     * @param path The path to the folder with the frames of the animation.
     * @param fps The frames per second the animation plays at.
     */
    public Animation(String path, int fps) {
        interval = FPS / fps;

        File folder = new File(path);
        File[] files = folder.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        // Getting all the frames from the folder and putting them in an ArrayList.
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                try {
                    String imagePath = file.getPath();
                    frames.add(ImageIO.read(new File(imagePath)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Generate a Rect of the same size as the images.
        rect = new Rect(frames.get(0).getWidth(), frames.get(0).getHeight());
    }


    /**
     * A getter method for play state of the animation.
     */
    public boolean isPlaying() { return isPlaying; }

    /**
     * A getter method for the Rect of the animation.
     */
    public Rect getRect() { return rect; }

    /**
     * A method which restarts the animation.
     */
    public void play() { 
        isPlaying = true;
        duration = 0;
        currentFrame = 0;
    }

    /**
     * A method which determines which frame of the animation gets shown.
     */
    public void updateFrame() {
        currentFrame = (int) Math.round((double) duration / interval);

        if (duration > (frames.size() - 1) * interval) {
            duration = 0;
            isPlaying = false;
        } else {
            duration += 1;
        }
    }

    /**
     * A method which updates the direction of the animation.
     */
    public void updatePosition(Direction direction, Rect attacker) {
        final int OFFSET = 5;

        if (this.direction == Direction.RIGHT || this.direction == Direction.LEFT) {
            rect.rotate();
        }

        this.direction = direction;

        switch (this.direction) {
            case UP -> {
                rect.setCenter(attacker.getCenter()[0], attacker.getTop() - OFFSET);
            }
            case DOWN -> {
                rect.setCenter(attacker.getCenter()[0], attacker.getBottom() + OFFSET);
            }
            case LEFT -> {
                rect.rotate();
                rect.setCenter(attacker.getLeft() - OFFSET, attacker.getCenter()[1]);
            }
            case RIGHT -> {
                rect.rotate();
                rect.setCenter(attacker.getRight() + OFFSET, attacker.getCenter()[1]);
            }
        }
    }

    /**
     * A method which renders the current frame of the animation.
     * 
     * @param g The Graphics2D object used to draw images to the screen.
     * @param translation How much the image needs to be translated.
     * @param scale How much the image should be scaled.
     */
    public void render(Graphics2D g, int[] translation, double scale) {
        if (isPlaying) {
            int[] rotationOffset = new int[2];

            switch (direction) {
                case UP -> rotationOffset = new int[]{ 0, 0 };
                case DOWN -> rotationOffset = new int[]{ (int)(rect.getWidth() * scale), 
                    (int)(rect.getHeight() * scale) };
                case RIGHT -> rotationOffset = new int[]{ (int)(rect.getWidth() * scale), 0 };
                case LEFT -> rotationOffset = new int[]{ 0, (int)(rect.getHeight() * scale) };
            };
        
            AffineTransform transform = new AffineTransform();
            transform.translate(rect.getX() * scale - translation[0] + rotationOffset[0],
                                rect.getY() * scale - translation[1] + rotationOffset[1]);
            transform.scale(scale, scale);
            transform.rotate(direction.rotation() * (Math.PI / 180));
    
            g.drawImage(frames.get(currentFrame), transform, null);
        }
    }

}
