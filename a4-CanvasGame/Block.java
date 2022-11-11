import javafx.scene.image.Image;

public class Block{

	private int xLoc;
	private int yLoc;
	private int xSpeed;
	private int ySpeed;
	
	private int size;
	
	private Image image;
	
	public Block(int xSpeedIn, int ySpeedIn, int sizeIn, Image imageIn) {
	
		xLoc = 0;
		yLoc = 0;
		xSpeed = xSpeedIn;
		ySpeed = ySpeedIn;
		size = sizeIn;
		image = imageIn;
		
	}
	
	//Getters/////////////////////////////////////////////////////////////

	public int getXLoc() {
		
		return xLoc;
		
	}
	
	public int getYLoc() {
		
		return yLoc;
		
	}
	
	public int getSize() {
		
		return size;
		
	}	
	
	public Image getImage() {
		
		return image;
		
	}
	
	//Setters/////////////////////////////////////////////////////////////
	
	public void setXSpeed(int xSpeedIn) {
		
		xSpeed = xSpeedIn;
		
	}
	
	public void setYSpeed(int ySpeedIn) {
		
		ySpeed = ySpeedIn;
		
	}
	
	public void setLoc(int xIn, int yIn) {
		
		xLoc = xIn;
		yLoc = yIn;
		
	}

	//General Methods/////////////////////////////////////////////////////
	
	public void move() {
		
		setLoc( (xLoc + xSpeed), (yLoc + ySpeed) );
		
	}
	
	//Returns true if "this" and blockIn are overlapping
	public boolean checkCollision(Block blockIn) {
		
		boolean result = false;
		
		//Specifically tests that the blockIn is NOT overlapping "this" Block by checking every side
		if(
			!(
				( ( (getXLoc() + getSize()) < blockIn.getXLoc() ) || (getXLoc() > (blockIn.getXLoc() + blockIn.getSize()) )  ) ||
				( ( (getYLoc() + getSize()) < blockIn.getYLoc() ) || (getYLoc() > (blockIn.getYLoc() + blockIn.getSize()) )  )
			)
			
		  ) {
			  
			result = true;
			
		}
		
		return result;
		
	}
}