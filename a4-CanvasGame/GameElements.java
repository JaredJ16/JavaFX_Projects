import javafx.scene.image.Image;
import javafx.scene.text.Text;

public class GameElements {
	
	/*
	 * GameElements is a class whose objects will store most of the useful
	 * information for the game such as the score and the 3 main componets 
	 * in the game(player, point, enemy). All of the 3 main componets of the
	 * game are accesible from a GameElements object.
	 *
	 */
	
	private static final int NUM_ENEMIES = 10;
	private static final int CANVAS_WIDTH = 720;
	private static final int CANVAS_HEIGHT = 480;
	
	private static final int LEFT_SIDE = 0;
	private static final int BOTTOM_SIDE = 1;
	private static final int RIGHT_SIDE = 2;
	private static final int TOP_SIDE = 3;
	
	private Player player;
	private Point point;
	private Block[] enemies = new Block[NUM_ENEMIES];
	
	private int score;
	private int highScore;
	
	private Text scoreText;
	private Text highScoreText;
	
	public GameElements(Player playerIn, Point pointIn) {
	
		player = playerIn;
		point = pointIn;
		
		for(int i = 0; i < NUM_ENEMIES; i++) {
			
			enemies[i] = new Block(2, 2, 40, new Image("/Enemy.png"));
			
		}
		
		score = 0;
		highScore = 0;
		scoreText = new Text("Score: 0");
		highScoreText = new Text("High Score: 0");
		
	}
	
	//Getters/////////////////////////////////////////////////////////////
	
	public Player getPlayer() {
		
		return player;
		
	}
	
	public Point getPoint() {
		
		return point;
		
	}
	
	public int getScore() {
		
		return score;
		
	}
	
	public int getHighScore() {
		
		return highScore;
		
	}
	
	public Text getScoreText() {
		
		return scoreText;
		
	}
	
	public Text getHighScoreText() {
		
		return highScoreText;
		
	}
	
	public Block[] getEnemies() {
		
		return enemies;
		
	}
	
	//Setters/////////////////////////////////////////////////////////////
	
	public void setScore(int scoreIn) {
		
		score = scoreIn;
		
	}
	
	public void setHighScore(int scoreIn) {
		
		highScore = scoreIn;
		
	}
	
	public void updateScoreText() {
		
		scoreText.setText("Score: " + score);
		
	}
	
	public void updateHighScoreText() {
		
		highScoreText.setText("High Score: " + highScore);
		
	}
	
	//Enemy Methods///////////////////////////////////////////////////////
	
	public void setEnemyStartPositions() {
		
		for(int i = 0; i < NUM_ENEMIES; i++) {
			
			spawnEnemy(enemies[i]);

		}
		
	}
	
	//spawnEnemy calls setRandomSpawnSide() and also sets the random speeds so that enemies must enter the canvas.
	public void spawnEnemy(Block enemy) {
		
		int enterSide = setRandomSpawnSide(enemy);
		
		if(enterSide == LEFT_SIDE) {
			
			enemy.setXSpeed(Math.abs(generateRandomSpeed()));
			enemy.setYSpeed(generateRandomSpeed());
			
		}else if(enterSide == BOTTOM_SIDE){
			
			enemy.setXSpeed(generateRandomSpeed());
			enemy.setYSpeed(-Math.abs(generateRandomSpeed()));
			
		}else if(enterSide == RIGHT_SIDE){
			
			enemy.setXSpeed(-Math.abs(generateRandomSpeed()));
			enemy.setYSpeed(generateRandomSpeed());
			
		}else if(enterSide == TOP_SIDE){
			
			enemy.setXSpeed(generateRandomSpeed());
			enemy.setYSpeed(Math.abs(generateRandomSpeed()));
			
		}
		
	}
	
	//The side for the new spawn is randomly chosen, and the random starting positions are chosen based on the side.
	public int setRandomSpawnSide(Block enemy) {
		
		int randSide = (int)(Math.random() * 4);
		
		//The enemy will never be spawned in a corner
		int randomHeight = (int)(Math.random() * (CANVAS_HEIGHT - (2 * enemy.getSize() )) ) + enemy.getSize();
		int randomWidth = (int)(Math.random() * (CANVAS_WIDTH - (2 * enemy.getSize() )) ) + enemy.getSize();
		
	
		if(randSide == LEFT_SIDE) {
			
			enemy.setLoc(-enemy.getSize(), randomHeight);
			
		}else if(randSide == BOTTOM_SIDE) {
			
			enemy.setLoc(randomWidth, CANVAS_HEIGHT);
		
		}else if(randSide == RIGHT_SIDE) {
			
			enemy.setLoc(CANVAS_WIDTH, randomHeight);
		
		}else if(randSide == TOP_SIDE) {
			
			enemy.setLoc(randomWidth, -enemy.getSize());
			
		}
		
		return randSide;
		
	}
	
	//Generate random speed that cannot be directly vertical or horizontal
	public int generateRandomSpeed() {
		
		int negative = 1;
		
		if(Math.random() >= .5) {
			
			negative = -1;
			
		}
		
		//Speed range [-4, -2], [2, 4]
		return negative * ((int)(Math.random() * 2) + 2);
		
	}
	
	//Update enemy positions
	public void moveEnemies() {
		
		for(int i = 0; i < NUM_ENEMIES; i++) {
			
			enemies[i].move();
			
		}
		
	}
	
	//Once an enemy fully leaves the canvas, it is respawned.
	public void checkEnemyOutOfBounds() {
	
		for(int i = 0; i < NUM_ENEMIES; i++) {
			
			if( (enemies[i].getXLoc() < (-enemies[i].getSize())) || 
			    (enemies[i].getXLoc() > CANVAS_WIDTH) ||
				(enemies[i].getYLoc() < (-enemies[i].getSize())) ||
				(enemies[i].getYLoc() > CANVAS_HEIGHT)
			  ) {
				  
				spawnEnemy(enemies[i]);
				
			}
			
		}
		
	}
	
	//Returns whether or not a enemy/player collsion has occured yet wiht any enemies.
	public boolean checkPlayerCollision(Player player) {
		
		boolean result = false;
		
		for(int i = 0; i < NUM_ENEMIES; i++) {
			
			if(enemies[i].checkCollision((Block)player)) {
				
				result = true;
				
			}
		}
		
		return result;
		
	}
}