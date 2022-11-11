import javafx.scene.image.Image;

public class Point extends Block {

	private static final int CANVAS_WIDTH = 720;
	private static final int CANVAS_HEIGHT = 480;

	//speed of point will always remain 0.
	
	public Point(int xSpeedIn, int ySpeedIn, int sizeIn, Image imageIn) {
		
		super(xSpeedIn, ySpeedIn, sizeIn, imageIn);
		
	}
	
	//The point should be placed away from the player
	public void setNewPlacement(Player player) {
		
		int randX = (int)(Math.random()*(CANVAS_WIDTH-getSize()));
		int randY = (int)(Math.random()*(CANVAS_HEIGHT-getSize()));
		setLoc(randX, randY);
		
		//A for loop was used to sacrifice the point possibly hitting the player compared to using a while loop that might be infite
		for(int i = 0; i < 5; i++) {
			
			//Checks to see if the random location overlaps player, if it does, generate new number else setLoc and break
			if(this.checkCollision((Block)player)) {

				randX = (int)(Math.random()*(CANVAS_WIDTH-getSize()));
				randY = (int)(Math.random()*(CANVAS_HEIGHT-getSize()));
				setLoc(randX, randY);
				
			}else{

				setLoc(randX, randY);
				break;
				
			}
			
		}		
		
	}
}