CanvasGame README


CanvasGame.java:

	Overview of program: 
	-The three main components of the game are the blue player, the red enemies, and the green point. The user
     moves the player using the arrow keys while trying to dodge the enemies and collect points.	

	Outline of code:
	
	-Basic setting up of the program is done up until the setUpScene() method.

	-In the setUpScene() method, the objects neccesary for the game(player, point, gameElements,and canvas) are instantiated.

	-Then, setUpScene() method calls setUpScoreSection() which just aligns the scoring Texts using a StackPane.

	-Next, setUpScene() calls addEventHandlers() which creates two event handlers(key pressed/ key released) to update the 
	 booleans for each arrow key(left, up, right, down) stored in the keys array(which is in the Player object). After all the keyboard event
     handeling is done, the keys array holds the current status of all the arrow keys(pressed or released), so this information can be used later 
	 in moving the player. (in the keys array, keyPressed->true and keyReleased->false)

	-Then, setUpScene() calls initStatuses() which sets the position of the player to the middle, sets the starting outside position and speed of the enemies,
     sets a new random placement for the point, sets the score to 0, and updates the scoring Texts.

	-Finally, the gameLoop() method is called. The gameLoop() method follows the general logic of clearing the canvas, updating(update()) the positions of all 
	 the game elements, and then redrawing the game elements.
	 
	-In the update() method, the player's speed is updated according to the current state of the keys array. Next, the new position of the player and all of the enemies
	 is calculated by adding the x and y speeds to the previous x and y coordinates. Then, conditions are checked such as: the player attempting to exit the canvas area,
	 adding a point if there is a player/point collison, and checking for a player/enemy collison. After a player/enemy collision occurs, the initStatuses() method is 
	 called, and the game resets.
	 
	Certain Features:
	
	-The point block should always be spawned away from the player's location whenever the game starts or when a new point position is being set.
	
	-When the user presses down on both the right and left arrows, the player does not move horizontally, and when the user presses down on both the
	 up and down arrows, the player does not move vertically.
	 
	-The enemy blocks can move faster than the player.
	 
Block.java:

	-A Block holds a location, speed, size, and image. Also, the collision detection method is stored in the block class, so if neccesary, each Block can determine if it
	 is overlapping another Block.
	 
	-Block is the parent of the Player and Point class.

Player.java:

	-The Player class is a Block, but with the addition of the method that checks if the player is out of bounds and the method that changes the player speed based on the keys array.
	-The keys array holds the current status of each arrow key when it is pressed(true) or released(false). The indicies of the keys array are kept track of with the use of the 
	 constants UP_KEY, DOWN_KEY, RIGHT_KEY, and LEFT_KEY.

Point.java:

	-A Point holds a location, speed(always zero), and image. The point class contains a single method: setNewPlacement().
	-setNewPlacement() gives the point a new random location that should be away from the player.


GameElements.java:

	-GameElements holds a player, a point, the enemies, and all of the score information.
	-In GameElements, the enemies array is an array of Block objects that holds NUM_ENEMIES.
	-GameElements also holds the methods responsible for managing the enemies array.
	
	-Enemy Logic: The enemies are randomly spawned on any side of the canvas and given random speeds so that they must enter the canvas(Example: an enemy 
	 spawning on the left side must be given a positive xSpeed so that it enter the canvas). When an the enemy's entire image exits the canvas, it 
	 is respawned.
	
Possible Improvements:

-Add measures to check to see if everything loads correctly(Example: loading images)
-Add a start and reset button
-Add a time delay between deaths
-Add an easy, medium, and hard mode


Random Notes:

-I realized half way through that the Block class could have been replaced
 by the Rectangle class already in the javaFX library.
-I am not quite sure if it is a good practice to make a class such as GameElements
 which just holds a collection objects, variables, and methods.
 
 
Notes about Canvas class:

-The origin(0,0) is located in the upper left
-Positive X and Y Speeds will move objects to the right and down
-Any portion of an object outside of the canvas width and height will not be painted onto the canvas.