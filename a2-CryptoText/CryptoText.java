//Jared Jordan Directed Study
/*
Program takes text input in a textArea.
Two buttons can be used to encrypt the text 
stored in the textArea. A clear button is 
available to discard the text in the textArea.
A character limit is set for the textArea.
*/

/*

Compile and Run

cd C:\Users\Jared\Documents\Programming\Directed Study\a2-CryptoText
javac CryptoText.java
java CryptoText

*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler;

import java.util.function.*;

public class CryptoText extends Application {
	
	private static final String TITLE = "CryptoText";
	private static final int WINDOW_HEIGHT = 510;
	private static final int WINDOW_WIDTH = 600;
	private static final int MAX_CHAR_LIMIT = 700;
	private static final String INSTRUCTIONS = "Enter any text and encrypt the text by using the buttons labeled E1 and E2. When you press on any of the encrypting buttons, the current text displayed in the text area will be encrypted using whichever method described. Use the clear button to clear the text area.";
    private static final int INSTRUCTION_AREA_WIDTH = 500;
	private static final int SECTION_HEIGHT = (WINDOW_HEIGHT / 3);
	private static final int BUTTON_SPACING = 30;
	private static final int E1_INDEX = 0;
	private static final int E2_INDEX = 1;
	
	//All method calls come back to this function
	public void start(Stage primaryStage) {
		
		//Three veritcal sections of the program(instruction region, input region, button region) placed in VBox
		VBox rootNode = new VBox();
		rootNode.setAlignment(Pos.TOP_CENTER);
		
		Scene mainScene = new Scene(rootNode, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//TextArea initialized here becuase it needs to be accessed across multiple regions
		TextArea textInput = new TextArea();
		
		
		//Three main regions of program in respective vertical order(intructionRegion on top)
		
		//instructionRegion-> Containes instructions for what to do
		//inputRegion-> Containes the textArea and the clear button
		//buttonRegion-> Containes buttons to encrypt the text in the textArea
		StackPane instructionRegion = new StackPane();
		StackPane inputRegion = new StackPane();
		HBox buttonRegion = new HBox(BUTTON_SPACING);
		
		
		//Establish all program elements and events
		createAllRegions(instructionRegion, inputRegion, buttonRegion, textInput);
		addButtonEventHandlers(buttonRegion, textInput);
		rootNode.getChildren().addAll(instructionRegion, inputRegion, buttonRegion);
		initStage(primaryStage, mainScene);	
		
    }
	
    public static void main(String[] args) {
		
        Application.launch(args);
		
	}	
	
	public static void initStage(Stage stage, Scene scene) {
		
		stage.setScene(scene); 
		stage.sizeToScene();
		stage.setResizable(false);
		stage.setTitle(TITLE);
		stage.show();
		
	}
	
	//Macro level funtion to call individual region creation methods
	public static void createAllRegions(StackPane instructionR, StackPane inputR, HBox buttonR, TextArea textA) {
		
		createInstructionRegion(instructionR);
		createInputRegion(inputR, textA);
		createButtonRegion(buttonR);
		
	}
	
	//Establishes instruction regions with centered text
	public static void createInstructionRegion (StackPane instructionR) {
		
		Text instructions = new Text(INSTRUCTIONS);
		
		instructionR.setPrefSize(WINDOW_WIDTH, SECTION_HEIGHT);
		instructions.setWrappingWidth(INSTRUCTION_AREA_WIDTH);
		instructions.setTextAlignment(TextAlignment.CENTER);
		
		instructionR.getChildren().add(instructions);

	}
	
	//Establishes input region with a textArea and a clear button
	public static void createInputRegion (StackPane inputR, TextArea textA) {
		
		Button clearButton = new Button("Clear");
		
		//stylelistic additions
		textA.setWrapText(true);
		textA.setPrefRowCount(6);
		textA.setMaxWidth(WINDOW_WIDTH - 110); // 55 pixels right and left
		
		inputR.setPrefSize(WINDOW_WIDTH, SECTION_HEIGHT);
		inputR.setAlignment(clearButton, Pos.BOTTOM_RIGHT);
		
		//Limit the amount of characters possible in the textArea to MAX_CHAR_LIMIT and add functionality to clear button
		limitTextSize(textA);
		addClearButtonEventHandeling(clearButton, textA);
		
		inputR.getChildren().addAll(textA, clearButton);
		
	}
	
	//Establishes buttons in buttonRegion
	public static void createButtonRegion (HBox buttonR) {

		buttonR.setPrefSize(WINDOW_WIDTH, SECTION_HEIGHT);

		Button E1 = new Button("Caesar's Cipher\nE1");
		Button E2 = new Button("Polyalphabetic\nE2");
		
		E1.setTextAlignment(TextAlignment.CENTER);
		E2.setTextAlignment(TextAlignment.CENTER);
		buttonR.setAlignment(Pos.CENTER);
		
		buttonR.getChildren().addAll(E1, E2);
		//button eventHandlers added in start() method becuase they need to access the textArea
	}
	
	//Limits textArea by looking at changes that will be added to the textArea
	public static void limitTextSize(TextArea textA) {
		
		
		//Lambda Expression for limiting amount of characters in TextArea Source:
		//https://stackoverflow.com/questions/36612545/javafx-textarea-limit
		
		UnaryOperator<Change> limitSize = change -> {
			
			if(change.getControlNewText().length() <= MAX_CHAR_LIMIT) {
				return change;
			}
			
			return null;
			
		};
		
		TextFormatter<String> manageText = new TextFormatter<String>(limitSize);
		textA.setTextFormatter(manageText);
		
	}
	
	//EventHandeling for clear button setting textArea to a null string
	public static void addClearButtonEventHandeling(Button cButton, TextArea textA) {
		
		EventHandler<MouseEvent> buttonClicked = new EventHandler<MouseEvent>() { 
		
			@Override 
			public void handle(MouseEvent event) {
				
				textA.setText("");
				
			} 
			
		};
		cButton.addEventFilter(MouseEvent.MOUSE_CLICKED, buttonClicked);
		
	}
	
	//Establishes the funtions to be called as a result of the buttons being pressed
	public static void addButtonEventHandlers(HBox buttonR, TextArea textA) {
		
		//E1 clicked -> E1Encryption called
		EventHandler<MouseEvent> E1Clicked = new EventHandler<MouseEvent>() { 
		
			@Override 
			public void handle(MouseEvent event) {
				
				E1Encryption(textA);
				
			} 
			
		};
		buttonR.getChildren().get(E1_INDEX).addEventFilter(MouseEvent.MOUSE_CLICKED, E1Clicked); 
		
		
		//E2 clicked -> E2Encryption called 
		EventHandler<MouseEvent> E2Clicked = new EventHandler<MouseEvent>() { 
		
			@Override 
			public void handle(MouseEvent event) {
				
				E2Encryption(textA);
				
			} 
			
		};
		buttonR.getChildren().get(E2_INDEX).addEventFilter(MouseEvent.MOUSE_CLICKED, E2Clicked); 
	}
	
	//E1 encryption is a Caesar's Cipher where the shift amount is determined by a random number
	//Letters move according to a pattern such as a->d and g->j. Every same letter goes to the same letter, so 
	// for example, every a will be encrypted to c.
	public static void E1Encryption(TextArea textA) {
		
		String currentText = textA.getText();
		char[] modifiedText = new char[currentText.length()];
		//number: [-5,5]
		int randomShiftAmt = ((int)(Math.random() * 11) - 5);
		
		if(randomShiftAmt == 0) {
			
			randomShiftAmt--;
			
		}
		
		//loop through and shift each letter
		for (int i = 0; i < currentText.length(); i++){
			
			modifiedText[i] = ((char)(((int)currentText.charAt(i)) + randomShiftAmt));        

		}
		
		currentText = new String(modifiedText);
		textA.setText(currentText);
	}
	
	//E2 encryption is similar to E1 becuase it uses shifts in the alphabet to determine the encrypted char, but
	// not every same letter will be translated to the same amount.
	public static void E2Encryption(TextArea textA) {
		
		String currentText = textA.getText();
		char[] modifiedText = new char[currentText.length()];
		//numbers: [-5,5]
		int[] randomShiftMatrix = {
			((int)(Math.random() * 11) - 5),
			((int)(Math.random() * 11) - 5),
			((int)(Math.random() * 11) - 5)
		};
		
		//Each charchter in consecutive threes will recieve a different (possibly the same due to randomness) substitution amount
		//Example: bbbbbbbbb->acdacdacd
		for (int i = 0; i < currentText.length(); i++){
			
			modifiedText[i] = ((char)(((int)currentText.charAt(i)) + randomShiftMatrix[(i % 3)]));        

		}
		
		currentText = new String(modifiedText);
		textA.setText(currentText);
		
	}
	
}

/*Works Cited
 *
 * JavaFX 8. Oracle JavaFX Docs, Oracle, docs.oracle.com/javase/8/javafx/api/.
 * Jenkov, Jakob. JavaFX Overview. Jenkov.com, Jenkov Aps , 17 May 2016, tutorials.jenkov.com/javafx/overview.html#stage.
 * Schildt, Herbert. Java: the Complete Reference. 9th ed., McGraw Hill, 2014.
 *
 */