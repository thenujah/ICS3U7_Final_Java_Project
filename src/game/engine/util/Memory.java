package game.engine.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import game.engine.components.Rect;

public class Memory {

    private String pathToFile;
    private int highscore;
    
    public Memory(String path) {
        pathToFile = path;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));

            String line = reader.readLine();
            highscore = Integer.parseInt(line);
            
            reader.close();
        }
        
        catch(IOException e) {
            try {
                File file = new File(path);
                file.createNewFile();
                highscore = 0;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public int getHighscore() { return highscore; }

    public void saveScore(int highscore) {
        this.highscore = highscore;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));

            writer.write(String.valueOf(highscore));
            writer.newLine();
            
            writer.close();

        } catch(IOException e) {
            System.out.println(e);
        }
    }

}