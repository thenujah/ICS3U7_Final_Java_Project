package game.engine;

import java.util.HashMap;
import java.awt.Graphics2D;

import game.engine.util.Memory;
import game.engine.Scene;
import game.scenes.TitleScreen;
import game.scenes.Game;
import game.scenes.InstructionsPage;
import game.scenes.MainMenu;
import game.scenes.AnimationTest;

/**
 * A class which handles the changing of the scenes.
 * 
 * @version 1.0
 * @since 1.0
 */
public class AppManager {

    public String currentScene;
    private HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    private Memory save = new Memory("./save.txt");

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
    public void update() {
        scenes.get(currentScene).update();
    }

    /**
     * Executed each frame.
     */
    public void render(Graphics2D g) {
        scenes.get(currentScene).render(g);
    }

    public int getHighscore() { return save.getHighscore(); }
    public void setHighscore(int highscore) {
        save.saveScore(highscore);
    }

}