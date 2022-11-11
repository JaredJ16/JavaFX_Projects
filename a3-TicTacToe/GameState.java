import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//GameState class contains various variables and objects that need to be accessed frequently
public class GameState
{
	//NULLSPACE represents spaces on the board with neither and X or and O
	private static final int NULLSPACE = 0;

	//Numeric value of scores
	private int playerOneScore;
	private int playerTwoScore;

	//Player Names
	private String playerOneName;
	private String playerTwoName;
	
	//Boolean used to recongize turns
	private boolean isPlayerOneTurn;
	
	//P1Score and P2Score hold the text version placed in the scene
	private Text P1Score;
	private Text P2Score;
	
	//Holds the display text for whoever's turn it is.
	private Text turnText;
	
	//The images to be stored in the imageMatrix
	private Image BlankSpace;
	private Image XSpace;
	private Image OSpace;
	
	//gameStatusMatrix holds the board as an array (0->NULLSPACE, 1->P1SPACE, 2->P2Space)
	//imageMatrix holds the images to be displayed
	private int[][] gameStatusMatrix;
	private ImageView[][] imageMatrix;
	
	
	public GameState() {
		
		playerOneScore = 0;
		playerTwoScore = 0;
		
		playerOneName = "";
		playerTwoName = "";
		
		//First turn randomly decided
		if(Math.random() >= .5) {
			isPlayerOneTurn = true;
		}else {
			isPlayerOneTurn = false;
		}
		
		//P1/P2 are default names
		P1Score = new Text("P1 Score: 0");
		P2Score = new Text("P2 Score: 0");
		P1Score.setFont(new Font(18));
		P2Score.setFont(new Font(18));
		
		//The String stored in turnText is decided later
		turnText = new Text("");
		turnText.setFont(new Font(30));
		
		//Gets Images from current folder
		BlankSpace = new Image("/Blank.png");
		XSpace = new Image("/XSpace.png");
		OSpace = new Image("/OSpace.png");
		
		//The Board starts out blank
		gameStatusMatrix = new int[][]{
			{NULLSPACE, NULLSPACE, NULLSPACE},
			{NULLSPACE, NULLSPACE, NULLSPACE},
			{NULLSPACE, NULLSPACE, NULLSPACE}
		};
		
		imageMatrix = new ImageView[][]{
			{new ImageView(BlankSpace), new ImageView(BlankSpace), new ImageView(BlankSpace)},
			{new ImageView(BlankSpace), new ImageView(BlankSpace), new ImageView(BlankSpace)},
			{new ImageView(BlankSpace), new ImageView(BlankSpace), new ImageView(BlankSpace)}
		};
	}
	//Get/////////////////////////////////////////////////////////////////
	
	public int getPlayerOneScore() {
		return playerOneScore;
	}
	
	public int getPlayerTwoScore() {
		return playerTwoScore;
	}
	
	
	public String getPlayerOneName() {
		return playerOneName;
	}
	
	public String getPlayerTwoName() {
		return playerTwoName;
	}
	
	
	public boolean getIsPlayerOneTurn() {
		return isPlayerOneTurn;
	}


	public Text getP1Score() {
		return P1Score;
	}
	
	public Text getP2Score() {
		return P2Score;
	}
	

	public Text getTurnText() {
		return turnText;
	}
	
	
	public Image getBlankSpace() {
		return BlankSpace;
	}
	
	public Image getXSpace() {
		return XSpace;
	}
	
	public Image getOSpace() {
		return OSpace;
	}
	
	
	public int[][] getGameStatusMatrix() {
		return gameStatusMatrix; 
	}
	
	
	public ImageView[][] getImageMatrix() {
		return imageMatrix;
	}
	
	//Set/////////////////////////////////////////////////////////////////
	
	public void updatePlayerOneScore() {
		playerOneScore++;
	}
	
	public void updatePlayerTwoScore() {
		playerTwoScore++;
	}
	
	
	public void setPlayerOneName(String nameIn) {
		playerOneName = nameIn;
	}
	
	public void setPlayerTwoName(String nameIn) {
		playerTwoName = nameIn;
	}

	
	public void setIsPlayerOneTurn(boolean boolIn) {
		isPlayerOneTurn = boolIn;
	}
	

	public void setP1Score(String scoreIn) {
		P1Score.setText(scoreIn);
	}
	
	public void setP2Score(String scoreIn) {
		P2Score.setText(scoreIn);
	}	
	
	
	public void setTurnText(String stringIn) {
		turnText.setText(stringIn);
	}
		
}