package game.engine;

import java.util.HashMap;
import java.awt.Graphics2D;

import game.engine.util.Memory;
import game.scenes.TitleScreen;
import game.scenes.Game;
import game.scenes.InstructionsPage;
import game.scenes.MainMenu;

/**
 * A class which handles the changing of the scenes.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class AppManager {

    public String currentScene;
    private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    private Memory save = new Memory("./save.txt");

    /**
     * The constructor for the AppManager. It initializes all the scenes of the app.
     */
    public AppManager() {
        scenes.put("title screen", new TitleScreen(this));
        scenes.put("main menu", new MainMenu(this));
        scenes.put("game", new Game(this));
        scenes.put("instructions", new InstructionsPage(this));

        currentScene = "title screen";
    }

    /**
     * Executed each frame.
     */
    public void update() { scenes.get(currentScene).update(); }

    /**
     * Executed each frame.
     */
    public void render(Graphics2D g) { scenes.get(currentScene).render(g); }

    /**
     * A getter method for the current high score.
     *
     * @return The current high score.
     */
    public int getHighScore() { return save.getHighScore(); }

    /**
     * A setter method for the high score.
     *
     * @param highScore The new high score.
     */
    public void setHighScore(int highScore) { save.saveScore(highScore); }

}