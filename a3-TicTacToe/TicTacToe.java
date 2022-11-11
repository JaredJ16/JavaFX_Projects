//Jared Jordan Directed Study
/*
Tic-Tac-Toe game where players get to type
in their names. During play, the first turn is decided
at random and players alternate picking squares. Once
a player had won or if there is a tie, the board is cleared
and the players can continue playing. The score is keep, but
there is no winning score, so players can continue playing.
*/

/*

Compile and Run

cd C:\Users\Jared\Documents\Programming\Projects\2018\Directed Study\a3-TicTacToe
javac TicTacToe.java GameState.java
java TicTacToe

*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//random note: I do my spacing of lines of code a little weird, but it makes it easier to read for me

public class TicTacToe extends Application {
	
	private static final String TITLE = "TicTacToe";
	private static final int WINDOW_HEIGHT = 540;
	private static final int WINDOW_WIDTH = 470;
	
	private static final int DISPLAY_SECTION_HEIGHT = 70;
	
	private static final int P1SPACE = 1;
	private static final int P2SPACE = 2;
	private static final int NULLSPACE = 0;
	
	private static final int MATRIX_LENGTH = 3;
	
	
    @Override
    public void start(Stage primaryStage) {
		
		//Holds varibles and objects used in both menuScene and gameScene
		GameState gameState = new GameState();
		
		StackPane menuRoot = new StackPane();
		VBox gameRoot = new VBox();
		
		//menuScene and gameScene are the two main scenes used:
		//menuScene is where the players type their names and hit a button to begin play
		//gameScene is where the game board, score, and the turn is diplayed
		//The user can switch between the menuScene and the gameScene
		Scene menuScene = new Scene(menuRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene gameScene = new Scene(gameRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//Stage starts out set to the menuScene
		primaryStage.setScene(menuScene); 
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
		
		//Initializes all components of menuScene and gameScene 
		setUpScenes(menuScene, gameScene, menuRoot, gameRoot, gameState, primaryStage);
		
    }
	
    public static void main(String[] args) {
		
        Application.launch(args);
		
	}	
	
	public static void setUpScenes(Scene mScene, Scene gScene, StackPane mRoot, VBox gRoot,  GameState gState, Stage stage) {
		
		//Macro level method setting up both scenes
		//opposite scenes sent, so event handlers can be created to switch between the two
		setUpMenu(gScene, mRoot, gState, stage);
		setUpGame(mScene, gRoot, gState, stage);
		
	}
	
	public static void setUpMenu(Scene gScene, StackPane mRoot, GameState gState, Stage stage) {
		
		Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GHOSTWHITE);
		
		
		Text gameLabel = new Text("Tic-Tac-Toe");
		gameLabel.setFont(new Font(40));
		
		//Label for TextField to designate which TextField goes with which player
		Text playerOneLabel = new Text("Player One");
		Text playerTwoLabel = new Text("Player Two");
		
		playerOneLabel.setFont(new Font(20));
		playerTwoLabel.setFont(new Font(20));
		
		//TextFields where players will enter their names
		TextField playerOneEntry = new TextField("P1");
		TextField playerTwoEntry = new TextField("P2");
		
		VBox playerOneArea = new VBox();
		playerOneArea.getChildren().addAll(playerOneLabel, playerOneEntry);
		
		VBox playerTwoArea = new VBox();
		playerTwoArea.getChildren().addAll(playerTwoLabel, playerTwoEntry);
		
		//A HBox stores the two "playerAreas" so they are horizontally side by side,
		//VBoxes are used to store the "playerAreas" so the text is labelled veritcally above
		//the textField
		HBox nameEntryArea = new HBox(50); //50 pixel spacing between elements
		nameEntryArea.getChildren().addAll(playerOneArea, playerTwoArea);
		
		Button playButton = new Button("Play");
		playButton.setFont(new Font(18));
		
		
		playerOneArea.setAlignment(Pos.CENTER);
		playerTwoArea.setAlignment(Pos.CENTER);
		nameEntryArea.setAlignment(Pos.CENTER);
		mRoot.setAlignment(gameLabel, Pos.TOP_CENTER);
		mRoot.setAlignment(playButton, Pos.BOTTOM_CENTER);
		
		//Event Handler for clicked playButton:
		//names modified if neccesary and the scene is switched 
		EventHandler<MouseEvent> playButtonClicked = new EventHandler<MouseEvent>() { 
		
			@Override 
			public void handle(MouseEvent event) {
				
				//Modifies names so they will be shortended if needed and so that they will be spaced relatively evenly to the Score text.
				manageNames(playerOneEntry, playerTwoEntry, gState);
				
				//Sets all the text for the names/scores (this is also updated later) 
				gState.setP1Score(("" + gState.getPlayerOneName() + " Score: " + gState.getPlayerOneScore()));
				gState.setP2Score(("" + gState.getPlayerTwoName() + " Score: " + gState.getPlayerTwoScore()));
				
				playerOneEntry.setText(gState.getPlayerOneName());
				playerTwoEntry.setText(gState.getPlayerTwoName());
				stage.setScene(gScene);
	
			} 
			
		};
		playButton.addEventFilter(MouseEvent.MOUSE_CLICKED, playButtonClicked); 
		
		mRoot.getChildren().addAll(background, gameLabel, nameEntryArea, playButton);
		
	}	
	public static void manageNames(TextField playerOneEntry, TextField playerTwoEntry, GameState gState ) {
		
		//All names that will be used will be 10 characters long, so the following
		//shortens strings if neccesary or adds addtional spaces to the end of 
		//a string if neccesary
		
		String addedSpaces = "";
		int p1NameLength = playerOneEntry.getText().length();
		int p2NameLength = playerTwoEntry.getText().length();
		int lengthDifference = 0;
			
		//Limit name size to 10 characters and enters P1 and P2 as a default. If name size is less than 10, spaces are added to make it 10.
		
		//P1
		if(p1NameLength > 10) {
			
			gState.setPlayerOneName(playerOneEntry.getText().substring(0,10));
		
		}else if(p1NameLength == 0){
			
			gState.setPlayerOneName("P1        ");
			
		}else {
		
			lengthDifference = 0;
			addedSpaces = new String("");
			lengthDifference = 10 - p1NameLength;
			
			//add extra spaces if their is a need to extend length
			for(int i = 0; i < lengthDifference; i++){
	
				addedSpaces = new String(addedSpaces + " ");
	
			}
		
			gState.setPlayerOneName(playerOneEntry.getText() + addedSpaces);
	
		}
		
		//P2
		if(p2NameLength > 10) {
	
			gState.setPlayerTwoName(playerTwoEntry.getText().substring(0,10));
	
		}else if(p2NameLength == 0){

			gState.setPlayerTwoName("P2        ");
	
		}else {
			
			lengthDifference = 0;
			addedSpaces = new String("");
			lengthDifference = 10 - p2NameLength;
	
			//add extra spaces if their is a need to extend length
			for(int i = 0; i < lengthDifference; i++){
	
				addedSpaces = new String(addedSpaces + " ");
	
			}
		
			gState.setPlayerTwoName(playerTwoEntry.getText() + addedSpaces);
	
		}
		
	}
	
	public static void setUpGame(Scene mScene, VBox gRoot, GameState gState, Stage stage) {
		
		HBox displaySection = new HBox(10);
		GridPane gameSection = new GridPane();
		
		
		//Initialize each major section: 
		//displaySection holds the score, menu button, and the current turn
		//gameSection holds all the images for the game board
		initDisplaySection(displaySection, gState, stage, mScene);
		initGameSection(gameSection, gState);
		addGameSectionEventHandlers(gameSection, gState);
		
		gRoot.getChildren().addAll(displaySection, gameSection);
		
	}
	
	public static void initDisplaySection(HBox dSection, GameState gState, Stage stage, Scene mScene) {
		
		Background displayBackground = new Background(new BackgroundFill(Color.GHOSTWHITE, null, null));
		
		Text menuText = new Text("Menu");
		menuText.setFont(new Font(15));
		//menuText will switch scenes 
		addMenuTextEventHandlers(menuText, mScene, stage);
		
		
		VBox scoreSection = new VBox(5);
		scoreSection.getChildren().addAll(gState.getP1Score(), gState.getP2Score());
		gState.getP1Score().setFill(Color.RED);
		gState.getP2Score().setFill(Color.BLUE);
		
		//Player One will be red(x) and player two will be blue(o)
	
		//Sets the text for the starting turn based on initial random starting turn
		setTurnText(gState.getTurnText(), gState);
		
		//Organize displaySection
		dSection.setBackground(displayBackground);
		dSection.setPrefHeight(DISPLAY_SECTION_HEIGHT);
		dSection.setMargin(gState.getTurnText(), new Insets(0, 0, 0, 30)); //30 pixels right margin
		
		dSection.getChildren().addAll(menuText, scoreSection, gState.getTurnText());
		
	}
	
	public static void addMenuTextEventHandlers(Text mText, Scene mScene, Stage stage) {
		
		//Switch scenes
		EventHandler<MouseEvent> menuTextClicked = new EventHandler<MouseEvent>() { 
			
			@Override 
			public void handle(MouseEvent event) {

				stage.setScene(mScene);
	
			}
			
		}; 

		//Detail the text when hovered over
		EventHandler<MouseEvent> menuTextHover = new EventHandler<MouseEvent>() { 
			
			@Override 
			public void handle(MouseEvent event) {
				
				mText.setFill(Color.BLUE);
				mText.setUnderline(true);

			}
			
		};
		
		//Undetail the text when mouse exits
		EventHandler<MouseEvent> menuTextExit = new EventHandler<MouseEvent>() { 
			
			@Override 
			public void handle(MouseEvent event) {
				
				mText.setFill(Color.BLACK);
				mText.setUnderline(false);
				
			}
			
		};
		
		mText.addEventFilter(MouseEvent.MOUSE_CLICKED, menuTextClicked);
		mText.setOnMouseMoved(menuTextHover);
		mText.setOnMouseExited(menuTextExit);
		
	}
	
	public static void setTurnText(Text tText, GameState gState) {
		
		//Player One will be red(x) and Player two will be blue(o)
		if(gState.getIsPlayerOneTurn()) {
			
			tText.setText("Red's Turn");
			tText.setFill(Color.RED);
			
		}else {
			
			tText.setText("Blue's Turn");
			tText.setFill(Color.BLUE);
			
		}
		
	}
	
	public static void initGameSection(GridPane gSection, GameState gState) {
		
		//loop through and add a ImageView to each tile in the GridPane
		for(int i = 0; i < MATRIX_LENGTH; i++) {
			
			for(int j = 0; j < MATRIX_LENGTH; j++) {
				
				gSection.setRowIndex(gState.getImageMatrix()[i][j], i);
				gSection.setColumnIndex(gState.getImageMatrix()[i][j], j);
				gSection.getChildren().add(gState.getImageMatrix()[i][j]);
				
			}
			
		}
		
		//10 pixel spacing between tiles
		gSection.setHgap(10);
		gSection.setVgap(10);
		
	}
		
	public static void addGameSectionEventHandlers(GridPane gSection, GameState gState) {
		
		//loop through each ImageView in the GridPane to add an event handler for when it is clicked
		for(int i = 0; i < MATRIX_LENGTH; i++) {
			
			for(int j = 0; j < MATRIX_LENGTH; j++) {

				addTileEventHandler(gState.getImageMatrix()[i][j], gSection, gState);
				
			}
			
		}

	}
	
	public static void addTileEventHandler(ImageView img, GridPane gSection, GameState gState) {
				
		EventHandler<MouseEvent> tileClicked = new EventHandler<MouseEvent>() { 
		
			@Override 
			public void handle(MouseEvent event) {
				
				//Only allow clicks to empty spaces to be processed
				if(img.getImage() == gState.getBlankSpace()) {
					
					//assign space
					if(gState.getIsPlayerOneTurn()) {
						
						img.setImage(gState.getXSpace());
					
					}else {
						
						img.setImage(gState.getOSpace());
					
					}
					
					//update the int matrix holding the postion of every BlankSpace/P1Space/P2Space
					updateGameStatusMatrix(img, gSection, gState);
					
					//Check for win and update score
					if(checkWin(gState)) {
						
						if(gState.getIsPlayerOneTurn()) {
							
							gState.updatePlayerOneScore();
						
						}else {
							
							gState.updatePlayerTwoScore();
						
						}
						
						//update Score labels
						gState.setP1Score(("" + gState.getPlayerOneName() + " Score: " + gState.getPlayerOneScore()));
						gState.setP2Score(("" + gState.getPlayerTwoName() + " Score: " + gState.getPlayerTwoScore()));
						
					}
					
					//flip turn (turn flips at end no matter what the result)
					changeTurn(gState.getTurnText(), gState);
					
					
					//IMPROVEMENT NOTE: Currently there is no delay after a player wins to allow the players to see the final board
					
				}
	
			}
			
		};
		img.addEventFilter(MouseEvent.MOUSE_CLICKED, tileClicked); 
		
	}	
	
	public static void updateGameStatusMatrix(ImageView img, GridPane gSection, GameState gState) {
		
		//update the specific entry in gameStatusMatrix based on the turn and the space clicked
		int row = gSection.getRowIndex(img);
		int col = gSection.getColumnIndex(img);
		
		if(img.getImage() == gState.getXSpace()) {
			
			gState.getGameStatusMatrix()[row][col] = P1SPACE;
		
		}else {
			
			gState.getGameStatusMatrix()[row][col] = P2SPACE;
		
		}
		
	}
	
	public static boolean checkWin(GameState gState) {
		
		//win false until a winning status has been found
		boolean win = false;
		
		//board[][] is used for simpilicty in naming
		int[][] board = gState.getGameStatusMatrix();
		
		//check each row
		for(int i = 0; i < MATRIX_LENGTH; i++) {
			
			int[] row = board[i];
			
			if((row[0] == row[1]) && (row[1] == row[2]) && (row[0] != NULLSPACE)) {
				
				win = true;
				
			}
			
		}
		
		//check each column
		for(int i = 0; i < MATRIX_LENGTH; i++) {
			
			int[] col = { board[0][i], board[1][i], board[2][i] };

			if( (col[0] == col[1]) && (col[1] == col[2]) && (col[0] != NULLSPACE) ) { 
			
				win = true;
				
			}
			
		}
		
		//check diagonals
		int[] upRightToBottomLeft = {board[0][2], board[1][1], board[2][0]};
		int[] upLeftToBottomRight = {board[0][0], board[1][1], board[2][2]};
		
		if( (upRightToBottomLeft[0] == upRightToBottomLeft[1]) && (upRightToBottomLeft[1] == upRightToBottomLeft[2]) && (upRightToBottomLeft[0] != NULLSPACE) ) {
			
			win = true;
		
		}
		
		if( (upLeftToBottomRight[0] == upLeftToBottomRight[1]) && (upLeftToBottomRight[1] == upLeftToBottomRight[2]) && (upLeftToBottomRight[0] != NULLSPACE) ) {
			
			win = true;
		
		}
		
		//check win or tie and clear win if either
		if(win) {
			
			clearBoard(gState);
			
		}
		if(checkTie(gState)) {
			
			clearBoard(gState);
			
		}
		
		return win;
	
	}
	
	public static boolean checkTie(GameState gState) {
		//tie is true until their is a null space
		boolean tie = true;
		
		int[][] board = gState.getGameStatusMatrix();
		
		//check for NULLSPACE continue playing if none found
		for(int i = 0; i < MATRIX_LENGTH; i++) {
			
			for(int j = 0; j < MATRIX_LENGTH; j++) {

				if(board[i][j] == NULLSPACE) {
					
					tie = false;
					
				}
				
			}
			
		}
		
		return tie;
		
	}
	
	public static void clearBoard(GameState gState) {
		
		//Set all spaces to blank spaces and set gameStatusMatrix to NULLSPACES
		for(int i = 0; i < MATRIX_LENGTH; i++) {
			
			for(int j = 0; j < MATRIX_LENGTH; j++) {

				gState.getGameStatusMatrix()[i][j] = NULLSPACE;
				gState.getImageMatrix()[i][j].setImage(gState.getBlankSpace());
				
			}
			
		}
		
	}
	
	public static void changeTurn(Text tText, GameState gState) {
		
		//switch turn and turnText
		gState.setIsPlayerOneTurn(!gState.getIsPlayerOneTurn());
		
		if(gState.getIsPlayerOneTurn()) {
			
			tText.setFill(Color.RED);
			tText.setText("Red's Turn");
			
		}else {
			
			tText.setFill(Color.BLUE);
			tText.setText("Blue's Turn");
			
		}
	}	
	
}
/*Works Cited
 *
 * JavaFX 8. Oracle JavaFX Docs, Oracle, docs.oracle.com/javase/8/javafx/api/.
 * Jenkov, Jakob. JavaFX Overview. Jenkov.com, Jenkov Aps , 17 May 2016, tutorials.jenkov.com/javafx/overview.html#stage.
 * Schildt, Herbert. Java: the Complete Reference. 9th ed., McGraw Hill, 2014.
 * Random Stack OverFlow Answers
 */