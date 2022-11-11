//Jared Jordan Directed Study
/*

Simple JavaFX program that generates a window
that has a button in it. When the user presses
the button, the background color will change to
a random color.

*/

/*

Compile and Run

cd C:\Users\Jared\Documents\Programming\Directed Study\a1-RandomBackground
javac RandomBackground.java
java RandomBackground

*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler;
import javafx.geometry.Pos;

public class RandomBackground extends Application {
	
	private static final String TITLE = "RandomBackground";
	private static final int WINDOW_SIZE = 500;
	
	//start() acts like the main method in a JavaFX program
    @Override
    public void start(Stage primaryStage) {
		
		//StackPane used so things are easy to align
		StackPane rootNode = new StackPane();
		Scene mainScene = new Scene(rootNode, WINDOW_SIZE, WINDOW_SIZE);
		
		//background will display the random colors.
		Rectangle background = new Rectangle(WINDOW_SIZE, WINDOW_SIZE, Color.WHITE);
		Button button = new Button("Change Background");
		Text rgbColorText = new Text("RGB Color: 255 255 255");
		rgbColorText.setFont(new Font(20));
		
		//Adds an event handler to change the background and text color when button clicked
		EventHandler<MouseEvent> buttonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {
				changeBackgroundAndTextColor(background, rgbColorText);
			} 
		};
		button.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonClicked);   
		
		//Add elements to the scene through rootNode
		rootNode.setAlignment(rgbColorText, Pos.BOTTOM_LEFT);
		rootNode.getChildren().add(background);
		rootNode.getChildren().add(rgbColorText);
		rootNode.getChildren().add(button);

		initStage(primaryStage, mainScene);	
		
    }
	
    public static void main(String[] args) {
		
		//Controls cycle of JavaFx program (calls start() method)
        Application.launch(args);
		
	}	
	
	//Initializes general states for the stage and presents stage
	public static void initStage(Stage stage, Scene scene) {
		
		stage.setScene(scene); 
		stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle(TITLE);
		stage.show();
		
	}
	
	
	//Generates random number 0-256 to be used as an rgb value
	public static int randColorValue() {
		
		return ((int)(Math.random()*256));
	
	}
	
	//Uses random numbers to return a random color
	//Assigns text according to the random color
	public static Color randomColor(Text text) {
		
		int[] colorValues = {randColorValue(), randColorValue(), randColorValue()};
		text.setText("RGB Color: " + colorValues[0] + " " + colorValues[1] + " " + colorValues[2]);
		return (Color.rgb(colorValues[0], colorValues[1], colorValues[2]));
		
	}
	
	//Chooses text color based off of  the average rgb value of the color
	//Black text used when the average pixel value is closest to a brighter color
	public static Color chooseTextColor(Color color) {
		
		Color returnColor = Color.BLACK;
		//valueAvg can range from 0.0(black) to 1.0(white)
		double valueAvg = ((color.getRed() + color.getBlue() + color.getGreen()) / 3);
		if(valueAvg >= 0.5) {
			returnColor = Color.BLACK;
		}else {
			returnColor = Color.WHITE;
		}
		return returnColor;
	}
	
	//Macro level method to change colors and text
	public static void changeBackgroundAndTextColor(Rectangle background, Text text) {
		
		//Color made random and assigned to the background and the color values are assigned to text
		Color currentColor = randomColor(text); //text value changed in this method becuase color.getBlue() methods return value 0.0-1.0 not rgb values
		background.setFill(currentColor);
		
		//Color created based on random color and assigned to text
		currentColor = chooseTextColor(currentColor);
		text.setFill(currentColor);
		
	}
	
}

/*Works Cited
 *
 * JavaFX 8. Oracle JavaFX Docs, Oracle, docs.oracle.com/javase/8/javafx/api/.
 * Jenkov, Jakob. JavaFX Overview. Jenkov.com, Jenkov Aps , 17 May 2016, tutorials.jenkov.com/javafx/overview.html#stage.
 * Schildt, Herbert. Java: the Complete Reference. 9th ed., McGraw Hill, 2014.
 *
 */