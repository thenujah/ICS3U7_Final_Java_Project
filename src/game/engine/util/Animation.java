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

/**
 * A class which stores frames of an animation.
 */
public class Animation {

	public static int FPS = 60;

	private int fps;
	private ArrayList<BufferedImage> frames = new ArrayList<>();
	private int interval;

	private int duration = 0;
	private int currentFrame = 0;
	private boolean isPlaying = false;

	private Rect rect;

    /**
	 * The constructor for the Animation class.
	 * 
	 * @param path The path to the folder with the frames of the animation.
	 * @param fps The frames per second the animation plays at.
	 */
	public Animation(String path, int fps) {
		this.fps = fps;
		interval = FPS / fps;

		File folder = new File(path);
		File[] files = folder.listFiles();
		Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        // Getting all the frames from the folder and putting them in an ArrayList.
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().endsWith(".png")) {
				try {
					String imagePath = files[i].getPath();
					System.out.println(imagePath);
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
	 * A method used to allow the animation to play.
	 */
	public void play() { isPlaying = true; }

    /**
	 * A getter method for the Rect of the animation.
	 */
	public Rect getRect() { return rect; }

    /**
	 * A method which determines which frame of the animation gets shown.
	 */
	private void updateFrame() {
		currentFrame = (int) Math.round((double) duration / interval);

		if (duration > (frames.size() - 1) * interval) {
			duration = 0;
			isPlaying = false;
		} else {
			duration += 1;
		}
	}

    /**
	 * A method which renders the current frame of the animation.
	 */
	public void render(Graphics2D g, int[] translation, double scale) {
		if (isPlaying) {
			updateFrame();
		
			AffineTransform transform = new AffineTransform();
			transform.translate(rect.getX() * scale - translation[0],
								rect.getY() * scale - translation[1]);
			transform.scale(scale, scale);
	
			g.drawImage(frames.get(currentFrame), transform, null);
		}
	}

}
