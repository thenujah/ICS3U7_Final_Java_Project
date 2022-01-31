package game.engine.util;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class which reads the high score of the game from a .txt file and stores it in a variable.
 * The high score in this file can be overwritten as well.
 *
 * @author Monica Damyanova & Thenujah Ketheeswaran
 * @since Jan 30 2021
 */
public class Memory {

    private final String pathToFile;
    private int highScore;
    
    /**
     * The constructor for the Memory object.
     * 
     * @param path The path to the file containing the high score.
     */
    public Memory(String path) {
        pathToFile = path;

        // Trying to read the file.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));

            String line = reader.readLine();
            highScore = Integer.parseInt(line);
            
            reader.close();
        }
        
        catch(IOException e) {

            // If the file doesn't exist create a new file.
            try {
                File file = new File(path);
                file.createNewFile();
                highScore = 0;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * A getter method for the high score of the game.
     * 
     * @return The high score.
     */
    public int getHighScore() { return highScore; }

    /**
     * A method which saves the high score to a .txt file.
     * 
     * @param highScore The high score.
     */
    public void saveScore(int highScore) {
        this.highScore = highScore;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));
            writer.write(String.valueOf(highScore));
            writer.newLine();
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}