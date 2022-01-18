package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.engine.AppManager;
import game.engine.Scene;
import game.engine.util.Button;
import game.game_objects.Player;
import game.game_objects.Enemy;

import game.engine.util.KeyboardInput;

/**
 * A class which controls the game scene of the game.
 *
 * @version 1.0
 * @since 1.0
 */
public class Game extends Scene {

	public Player play;
	public Enemy attack;
	private int numberOfEnemies;
	private Enemy[] enemies;
	private Button backButton;
	Player player;

	public Game(AppManager app) {
		super(app);
		
		//Formatting for the 'Back' button on the game, so the user can go back to main menu when needed.
		Font subtitleFont = new Font("DialogInput", Font.PLAIN, 20);
		Color BLUE = new Color(75, 80, 105);
		
		//Back Button
		backButton = new Button(200, 50);
		backButton.setCenter(625, 380);
		backButton.backgroundColor = BLUE;
		backButton.font = subtitleFont;
		backButton.text = "Back";
		
		//Enemy instance variables 
		play = new Player();
		
		//Random number of enemies the player has to beat
		int numberOfEnemies = (int) (Math.random() * (9) + 1);

		enemies = new Enemy [numberOfEnemies];
		
		for (int i = 0 ; i < numberOfEnemies; i++) {
			enemies[i] = new Enemy();
		}

		}

	public void update() {
		play.movement();
		
		for (int i = 0 ; i <= numberOfEnemies; i++) {
			enemies[i].movement(play.rect.getTopLeft());
		}
		
		if (backButton.isClicked()) {
			app.currentScene = "main menu";
		}
		
	}

	public void render(Graphics2D g) {
		play.render(g);
		
		for (int i = 0 ; i <= numberOfEnemies; i++) {
			enemies[i].render(g);
		}
		
		backButton.render(g);
	}
}