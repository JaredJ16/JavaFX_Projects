import javafx.scene.image.Image;


public class Player extends Block
{
	
	private static final int CANVAS_WIDTH = 720;
	private static final int CANVAS_HEIGHT = 480;
	
	private static final int UP_KEY = 0;
	private static final int DOWN_KEY = 1;
	private static final int RIGHT_KEY = 2;
	private static final int LEFT_KEY = 3;
	
	private static final int PLAYER_SPEED = 2;
	
	private boolean[] keys = {false, false, false, false};
	
	public Player(int xSpeedIn, int ySpeedIn, int sizeIn, Image imageIn) {
	
		super(xSpeedIn, ySpeedIn, sizeIn, imageIn);
		
	}
	
	//Getters/////////////////////////////////////////////////////////////
	
		public boolean getKey(int indexIn) {
		
		return keys[indexIn];
		
	}

	//Setters/////////////////////////////////////////////////////////////
	
	public void setKeyTrue(int indexIn) {
		
		keys[indexIn] = true;
		
	}
	
	public void setKeyFalse(int indexIn) {
		
		keys[indexIn] = false;
		
	}
	
	//General Methods/////////////////////////////////////////////////////
	
	
	//Player Speed is changed by viewing the keys matrix to see which direction the player should move.
	public void changePlayerSpeed() {

		if(getKey(UP_KEY) && getKey(DOWN_KEY)) {
			
			setYSpeed(0);			
		
		}else if(getKey(UP_KEY)) {
		
			setYSpeed(-PLAYER_SPEED);

		}else if(getKey(DOWN_KEY)) {
			
			setYSpeed(PLAYER_SPEED);
			
		}
		
		if(getKey(RIGHT_KEY) && getKey(LEFT_KEY)) {
			
			setXSpeed(0);
			
		}else if(getKey(RIGHT_KEY)) {
			
			setXSpeed(PLAYER_SPEED);
			
		}else if(getKey(LEFT_KEY)) {
			
			setXSpeed(-PLAYER_SPEED);
			
		}
		
		if(!getKey(UP_KEY) && !getKey(DOWN_KEY)) {
			
			setYSpeed(0);
			
		}
		if(!getKey(RIGHT_KEY) && !getKey(LEFT_KEY)) {
			
			setXSpeed(0);
			
		}
		
	}
	
	//If player's newly updated position is outside of the canvas, place the player back into the canvas.
	public void checkPlayerOutOfBounds() {
		
		//left
		if(getXLoc() < 0) {
			
			setLoc(0, getYLoc());
			
		}
		
		//right
		if(getXLoc() > (CANVAS_WIDTH - getSize())) {
			
			setLoc((CANVAS_WIDTH - getSize()), getYLoc());
			
		}
		
		//top
		if(getYLoc() < 0) {
			
			setLoc(getXLoc(), 0);
			
		}
		
		//bottom
		if(getYLoc() > (CANVAS_HEIGHT - getSize())) {
			
			setLoc(getXLoc(), (CANVAS_HEIGHT - getSize()));
			
		}		
		
	}
	
}