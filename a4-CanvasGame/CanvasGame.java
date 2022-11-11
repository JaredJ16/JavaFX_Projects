//Jared Jordan Directed Study
/*
CanvasGame uses the JavaFX canvas class to make
a game where the player moves a block around the canvas
while trying to get a green "point" and while also avoiding 
blocks that spawn outside of the canvas. Score and a High Score 
are kept.
*/

/*

Compile and Run

cd C:\Users\Jared\Documents\Programming\Projects\2018\Directed Study\a4-CanvasGame

javac --module-path "C:\Program Files\JavaFX\openjfx-11.0.2_windows-x64_bin-sdk\lib" --add-modules javafx.controls CanvasGame.java Block.java

java --module-path "C:\Program Files\JavaFX\openjfx-11.0.2_windows-x64_bin-sdk\lib" --add-modules javafx.controls CanvasGame

*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.animation.AnimationTimer;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.image.Image;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;


import javafx.geometry.Pos;

public class CanvasGame extends Application {
	
	/* Notes about Canvas class:
	 * -The origin(0,0) is located in the upper left
	 * -Positive X and Y Speeds will move objects to the right and down
	 * -Objects outside of the canvas width and height will not be painted onto the canvas.
	 */
	
	/*
	 ***************************************************
	 * See README for details regarding program stucture
	 ***************************************************
	 */
	 
	private static final String TITLE = "Canvas Game";
	
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	private static final int CANVAS_WIDTH = 720;
	private static final int CANVAS_HEIGHT = 480;
	
	private static final int UP_KEY = 0;
	private static final int DOWN_KEY = 1;
	private static final int RIGHT_KEY = 2;
	private static final int LEFT_KEY = 3;

	private static final int NUM_ENEMIES = 10;	
	
    @Override
    public void start(Stage primaryStage) {
		
		//StackPane used to center the canvas and place the scores in the Center Top easily.
		StackPane rootNode = new StackPane();
		Scene mainScene = new Scene(rootNode, WINDOW_WIDTH, WINDOW_HEIGHT);
	
		//Initialize everything
		setUpScene(mainScene, rootNode);
		primaryStage.setScene(mainScene); 
		
		//Certain aesthetic measures for stage
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
		
    }
	
    public static void main(String[] args) {
		
        Application.launch(args);
		
	}	
	
	//Everything is set up: objects instantiated, eventHadlers added, initial game statusses set, and game loop started
	public static void setUpScene(Scene scene, StackPane rNode) {
		
		Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GHOSTWHITE);
		rNode.getChildren().add(background);
		
		
		//No security is used to see if images cannot be loaded
		Player player = new Player(0, 0, 50, new Image("/Player.png"));
		Point point = new Point(0, 0, 30, new Image("/Point.png"));
		
		
		//More details in class file
		GameElements gameElements = new GameElements(player, point);
		
		
		//Canvas
		Rectangle canvasBorder = new Rectangle((CANVAS_WIDTH + 6), (CANVAS_HEIGHT + 6), Color.BLACK); //3 pixel padding on all sides of canvas
		
		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		GraphicsContext canvasGraphics = canvas.getGraphicsContext2D();
		canvasGraphics.setFill(Color.WHITE);
		canvasGraphics.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		
		rNode.getChildren().addAll(canvasBorder, canvas);
		
		
		//initialize various statuses and begin gameLoop
		setUpScoreSection(rNode, gameElements);
		addEventHandlers(scene, gameElements);
		initStatuses(gameElements);
		gameLoop(canvasGraphics, gameElements);

	}
	
	//Aligns Score and HighScore texts in the center top
	public static void setUpScoreSection(StackPane rNode, GameElements gElements) {
		
		HBox scoreLabels = new HBox(15); //15 pixel spacing
		
		gElements.getScoreText().setFont(new Font(25));
		gElements.getScoreText().setFill(Color.RED);
		
		gElements.getHighScoreText().setFont(new Font(25));
		gElements.getHighScoreText().setFill(Color.RED);
		
		scoreLabels.getChildren().addAll(gElements.getScoreText(), gElements.getHighScoreText());
		scoreLabels.setAlignment(Pos.TOP_CENTER);
		
		rNode.getChildren().add(scoreLabels);
		rNode.setAlignment(scoreLabels, Pos.TOP_CENTER);

	}
	
	//Create all event handlers so that each event handler updates the keys array in the Player class
	public static void addEventHandlers(Scene scene, GameElements gElements) {
			
		EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() { 
			@Override 
			public void handle(KeyEvent event) {
				processPressedKeyEvents(event, gElements);
			} 
		};
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressed); 
	
		EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() { 
			@Override 
			public void handle(KeyEvent event) {
				processReleasedKeyEvents(event, gElements);
			} 
		};
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleased);
		
	}
	
	//Each pressed key is set to true in keys array in Player Class
	public static void processPressedKeyEvents(KeyEvent e, GameElements gElements) {
		if(e.getCode() == KeyCode.UP) {
			gElements.getPlayer().setKeyTrue(UP_KEY);
		}
		if(e.getCode() == KeyCode.DOWN) {
			gElements.getPlayer().setKeyTrue(DOWN_KEY);
		}
		
		if(e.getCode() == KeyCode.RIGHT) {
			gElements.getPlayer().setKeyTrue(RIGHT_KEY);
		}
		if(e.getCode() == KeyCode.LEFT) {
			gElements.getPlayer().setKeyTrue(LEFT_KEY);
		}
	}
	
	//Each released key is set to false in keys array in Player Class
	public static void processReleasedKeyEvents(KeyEvent e, GameElements gElements) {

		if(e.getCode() == KeyCode.UP) {
			gElements.getPlayer().setKeyFalse(UP_KEY);
		}
		if(e.getCode() == KeyCode.DOWN) {
			gElements.getPlayer().setKeyFalse(DOWN_KEY);
		}
		if(e.getCode() == KeyCode.RIGHT) {	
			gElements.getPlayer().setKeyFalse(RIGHT_KEY);
		}
		if(e.getCode() == KeyCode.LEFT) {	
			gElements.getPlayer().setKeyFalse(LEFT_KEY);
		}
	}
	
	//Genral settings to that set the score to 0, update the score texts, set the player in the middle, 
	//set the enemies outside the canvas, and give the point a random location 
	public static void initStatuses(GameElements gElements) {
	
		gElements.getPlayer().setLoc( ((CANVAS_WIDTH / 2) - (gElements.getPlayer().getSize() / 2)), ((CANVAS_HEIGHT / 2) - (gElements.getPlayer().getSize() / 2)) );
		
		gElements.setEnemyStartPositions();
		gElements.getPoint().setNewPlacement(gElements.getPlayer());
		gElements.setScore(0);
		
		gElements.updateHighScoreText();
		gElements.updateScoreText();		

	}
	
	//gameLoop holds the frame by frame logic for the game
	public static void gameLoop(GraphicsContext cGraphics, GameElements gElements) {
		
		//Main logic behind game: clear the current status of the canvas so that 
		//certain conditions can be checked and updated, then redraw everything, and repeat
		
		//Called 60 times a second
		new AnimationTimer() {	
			public void handle(long currentTime) {
				//clear
				clearCanvas(cGraphics);
				//update positions/ look over logic
				update(cGraphics, gElements);
				//draw
				drawAll(cGraphics, gElements);
			}
		}.start();
	}
	
	//Paint canvas white so that the new drawings will go on a fresh state
	public static void clearCanvas(GraphicsContext cGraphics) {
		cGraphics.setFill(Color.WHITE);
		cGraphics.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
	}
	
	//Update all game positions and statuses before redrawing
	public static void update(GraphicsContext cGraphics, GameElements gElements) {
		//update all statuses
		gElements.getPlayer().changePlayerSpeed();
		gElements.getPlayer().move();
		gElements.moveEnemies();
		
		//check logic with newly updated statuses
		gElements.getPlayer().checkPlayerOutOfBounds();
		gElements.checkEnemyOutOfBounds();
		
		//CheckPointScored is processed before player Collision
		managePointScored(gElements);
		managePlayerCollision(gElements);
	}
		
	//When the player and point collide, the score is updated, and the point is newly placed.
	public static void managePointScored(GameElements gElements) {
		if(gElements.getPoint().checkCollision((Block)gElements.getPlayer())) {
			gElements.setScore((gElements.getScore() + 1));
			gElements.getPoint().setNewPlacement(gElements.getPlayer());
			gElements.updateScoreText();
		}
	}
	
	//When a player/enemy collision occurs, the highscore is determined and the game is reset.
	public static void managePlayerCollision(GameElements gElements) {
		if(gElements.checkPlayerCollision(gElements.getPlayer())) {
			if(gElements.getScore() > gElements.getHighScore()) {
				gElements.setHighScore(gElements.getScore());
				gElements.updateHighScoreText();
			}
			initStatuses(gElements);
		}
	}
	
	//The newly updates objects are now drawn back onto the canvas.
	public static void drawAll(GraphicsContext cGraphics, GameElements gElements) {
		
		//Order of drawing images matters in terms of overlapping
		cGraphics.drawImage(gElements.getPlayer().getImage(), gElements.getPlayer().getXLoc(), gElements.getPlayer().getYLoc());
		cGraphics.drawImage(gElements.getPoint().getImage(), gElements.getPoint().getXLoc(), gElements.getPoint().getYLoc());
		
		for(int i = 0; i < NUM_ENEMIES; i++) {
			cGraphics.drawImage(gElements.getEnemies()[i].getImage(), gElements.getEnemies()[i].getXLoc(), gElements.getEnemies()[i].getYLoc());
		}
		
	}
	
}
/*Works Cited
 *
 * JavaFX 8. Oracle JavaFX Docs, Oracle, docs.oracle.com/javase/8/javafx/api/.
 * Jenkov, Jakob. JavaFX Overview. Jenkov.com, Jenkov Aps , 17 May 2016, tutorials.jenkov.com/javafx/overview.html#stage.
 * Schildt, Herbert. Java: the Complete Reference. 9th ed., McGraw Hill, 2014.
 * https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 * Random Stack OverFlow Answers
 */
