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

public class Animation {

	public static int FPS = 60;

	private int fps;
	private ArrayList<BufferedImage> frames = new ArrayList<>();
	private int interval;

	private int duration = 0;
	private int currentFrame = 0;
	private boolean isPlaying = false;

	private Rect rect;

	public Animation(String path, int fps) {
		this.fps = fps;
		interval = FPS / fps;

		File folder = new File(path);
		File[] files = folder.listFiles();
		Arrays.sort(files, Comparator.comparingLong(File::lastModified));

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
		rect = new Rect(frames.get(0).getWidth(), frames.get(0).getHeight());
	}

	public void play() { isPlaying = true; }

	public Rect getRect() { return rect; }

	private void updateFrame() {
		currentFrame = (int) Math.round((double) duration / interval);

		if (duration > (frames.size() - 1) * interval) {
			duration = 0;
			isPlaying = false;
		} else {
			duration += 1;
		}
	}

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
