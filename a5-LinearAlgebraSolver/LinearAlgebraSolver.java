//Jared Jordan Directed Study
/*

Description of Program in README


*/

/*

Compile and Run

cd C:\Users\Jared\Documents\Programming\Projects\2018\Directed Study\a5-LinearAlgebraSolver
javac LinearAlgebraSolver.java Matrix.java MatrixInputField.java MatrixVariableCollection.java
java LinearAlgebraSolver

*/


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.geometry.Pos;

import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler;

import javafx.scene.control.TextField;

import java.util.ArrayList;



public class LinearAlgebraSolver extends Application {
	
	private static final String TITLE = "LinearAlgebraSolver";
	
	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_WIDTH = 800;
	
	private static final int MAX_MATRIX_SIZE = 5;
	private static final int BUTTON_BAR_SPACING = 10;
	
	
    @Override
    public void start(Stage primaryStage) {
		
		// Every root will be a stackPane for centering nodes easily
		// See the README for details on each scene
		StackPane menuRoot = new StackPane();
		StackPane declareMatrixRoot = new StackPane();
		StackPane determinantRoot = new StackPane();
		StackPane multiplicationRoot = new StackPane();
		
		Scene menuScene = new Scene(menuRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene declareMatrixScene = new Scene(declareMatrixRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene determinantScene = new Scene(determinantRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene multiplicationScene = new Scene(multiplicationRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// The matrixVariableCollection stores all the declareable matricies within it
		// The matrixVariableCollection is accessed across most scenes
		MatrixVariableCollection matrixVariableCollection = new MatrixVariableCollection();
		
		//Basic styling of the Stage
		primaryStage.setScene(menuScene); 
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
		

		setUpScenes(menuScene, declareMatrixScene, determinantScene, multiplicationScene, primaryStage, matrixVariableCollection);
		
    }
	
    public static void main(String[] args) {
		
        Application.launch(args);
		
	}		
	
	//Higher level method setting up all scenes
	public static void setUpScenes(Scene menuScene, Scene declareMatrixScene, Scene determinantScene, Scene multiplicationScene, Stage stage, MatrixVariableCollection matrixVariableCollection) {
		
		//multiple scenes sent, so event handlers can be created to switch between the menu and the other scenes
		setUpMenuScene(menuScene, declareMatrixScene, determinantScene, multiplicationScene, stage);
		
		setUpDeclareMatrixScene(declareMatrixScene, menuScene, stage, matrixVariableCollection);
		setUpDeterminantScene(determinantScene, menuScene, stage, matrixVariableCollection);
		setUpMultiplicationScene(multiplicationScene, menuScene, stage, matrixVariableCollection);
		
	}
	
	//The menu is the scene where the user can branch out to the declareMatrixScene, determinantScene, and multiplicationScene
	//Every scene can return back to the menu
	public static void setUpMenuScene(Scene menuScene, Scene declareMatrixScene, Scene determinantScene, Scene multiplicationScene, Stage stage) {
		
		StackPane menuRoot = (StackPane)menuScene.getRoot();
		
		Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GHOSTWHITE);
		
		Text title = new Text("Linear Algebra Solver");
		title.setFont(new Font(45));
		menuRoot.setAlignment(title, Pos.TOP_CENTER);
		
		
		//Buttons to move to a certain scene are organized in a HBox
		Button declareMatrixButton = new Button("Declare Matrix");
		Button determinantButton = new Button("Determinant");
		Button multiplicationButton = new Button("Multiplication");
		
		HBox matrixOperationButtonBar = new HBox(BUTTON_BAR_SPACING);
		matrixOperationButtonBar.getChildren().addAll(declareMatrixButton, determinantButton, multiplicationButton);
		matrixOperationButtonBar.setAlignment(Pos.CENTER);
		
		
		menuRoot.getChildren().addAll(background, title, matrixOperationButtonBar);
		
		//Adds fucntionality to buttons allowing for the scene to change 
		addEventHandlersForSwitchingScenes(stage, declareMatrixScene, determinantScene, multiplicationScene, declareMatrixButton, determinantButton, multiplicationButton);

	}	
	
	
	//The declareMatrixScene allows the user to select a matrix from the MatrixVariableCollection, choose the dimensions,
	// and declare the matrix according to the current state of the TextFields in MatrixInputField's GridPane
	public static void setUpDeclareMatrixScene(Scene declareMatrixScene, Scene menuScene, Stage stage, MatrixVariableCollection matrixVariableCollection) {
		
		StackPane declareMatrixRoot = (StackPane)declareMatrixScene.getRoot();

		Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GHOSTWHITE);
		
		Text title = new Text("Matrix Declaration"); 
		title.setFont(new Font(45));
		
		Button menuButton = new Button("Menu");

		//matrixInputField holds gridPane and TextFields where the user will input the numbers for each coordinate of a matrix
		MatrixInputField matrixInputField = new MatrixInputField(MAX_MATRIX_SIZE, MAX_MATRIX_SIZE);
		
		//The matrixGridPane is filled with the 2D arraylist of TextFields from the matrixInputField.
		//The user can edit the TextFields to hold the strings that will later be converted to Doubles
		GridPane matrixGridPane = matrixInputField.getMatrixGridPane();
		
		//gridPaneHolder is used to properly size the matrixGridPane
		HBox gridPaneHolder = new HBox();
		gridPaneHolder.getChildren().add(matrixGridPane);
		gridPaneHolder.setMaxSize(500, 500);
		
		
		Button setMatrixVariableButton = new Button("Set Variable");
		Button setDimensionsButton = new Button("Set Dimensions");
		
		
		Text rowLabel = new Text("Num Rows");
		rowLabel.setFont(new Font(20));
		
		Text colLabel = new Text("Num Cols");
		colLabel.setFont(new Font(20));
		
		Text variableLabel = new Text("Variable");
		variableLabel.setFont(new Font(20));
		
		
		// Where user selects which matrix will be declared
		ComboBox matrixVariableComboBox = generateMatrixVariableComboBox(); 

		
		// ComboBoxes for the user to choose row and col sizes of 1 to 5
		ComboBox matrixRowSizeComboBox = new ComboBox();

        matrixRowSizeComboBox.getItems().add(((Integer)1));
		matrixRowSizeComboBox.getItems().add(((Integer)2));
		matrixRowSizeComboBox.getItems().add(((Integer)3));
		matrixRowSizeComboBox.getItems().add(((Integer)4));
		matrixRowSizeComboBox.getItems().add(((Integer)5));


		ComboBox matrixColSizeComboBox = new ComboBox();
		
        matrixColSizeComboBox.getItems().add(((Integer)1));
		matrixColSizeComboBox.getItems().add(((Integer)2));
		matrixColSizeComboBox.getItems().add(((Integer)3));
		matrixColSizeComboBox.getItems().add(((Integer)4));
		matrixColSizeComboBox.getItems().add(((Integer)5));

		
		//Text is hidden until an error is identified
		Text errorText = new Text();
		errorText.setFont(new Font(15));
		errorText.setFill(Color.RED);
		errorText.setVisible(false);
		
		//The userInputItems VBox stores the labels, the ComboBoxes, and the errorText. (on right side of screen)
		VBox userInputItems = new VBox(5);
		
		//Place all elements in the  userInputItems VBox to the right and limit the size of the userInputItems VBox
		userInputItems.getChildren().addAll(rowLabel, matrixRowSizeComboBox, colLabel, matrixColSizeComboBox, setDimensionsButton, variableLabel, matrixVariableComboBox, setMatrixVariableButton, errorText);
		userInputItems.setMaxSize(125, 400);
		userInputItems.setAlignment(Pos.CENTER_RIGHT);

		//Align all elements to proper position
		declareMatrixRoot.setAlignment(menuButton, Pos.TOP_LEFT);
		declareMatrixRoot.setAlignment(title, Pos.TOP_CENTER);
		declareMatrixRoot.setAlignment(matrixGridPane, Pos.CENTER);
		declareMatrixRoot.setAlignment(userInputItems, Pos.CENTER_RIGHT);
		declareMatrixRoot.setAlignment(gridPaneHolder, Pos.CENTER);
		
		
		
		declareMatrixRoot.getChildren().addAll(background, title, menuButton, userInputItems, gridPaneHolder);
		

		//setDimensionsClicked will change the MatrixInputField's ArrayList of TextFields if valid inputs are entered
		EventHandler<MouseEvent> setDimensionsButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {	
				//errorText does not need to be modified whenever the setDimensionsButton is clicked because the matrix will always have a value becuase it has a staring value that can only be modified between 1-5
				//setDimensionsAccordingToComboBoxes modifies the current matrixInputField GridPane so that it is the same dimensions as inputted
				setDimensionsAccordingToComboBoxes(declareMatrixRoot, matrixInputField, gridPaneHolder, matrixGridPane, matrixRowSizeComboBox, matrixColSizeComboBox);
			} 
		};
		setDimensionsButton.addEventFilter(MouseEvent.MOUSE_CLICKED, setDimensionsButtonClicked); 

		
		//Upon clicking the "Set Matrix Variable" button, the selected variable from 
		// the matrixVariableCollection will be declared if valid Doubles are entered
		EventHandler<MouseEvent> setSetMatrixVariableButtonButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {	
				//get matrixVariableComboBox to check for a null entry, if any entries are null, spit out error message.
				if(matrixVariableComboBox.getValue() != null){
					//close error message if it was there
					setTextVisibilityAndContents(errorText, false, "Check the input selections");
					//declare a Matrix from MatrixVariableCollection
					setVariableToCurrentTextFields(matrixVariableCollection, matrixInputField, matrixVariableComboBox, errorText);
				}else {
					//Error is presented until there is a different error or until the error is fixed(once user chooses valid inputs)
					setTextVisibilityAndContents(errorText, true, "Check the input selections");
				}
			} 
		};
		setMatrixVariableButton.addEventFilter(MouseEvent.MOUSE_CLICKED, setSetMatrixVariableButtonButtonClicked); 		
		
		
		addButtonToMenuSceneEventHandler(stage, menuScene, menuButton);
		
	}	
	
	public static void setUpDeterminantScene(Scene determinantScene, Scene menuScene, Stage stage, MatrixVariableCollection matrixVariableCollection) {
		
		
		StackPane determinantRoot = (StackPane)determinantScene.getRoot();

		Text title = new Text("Matrix Determinant"); 
		title.setFont(new Font(45));
		
		Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GHOSTWHITE);
		Button menuButton = new Button("Menu");
		
		Text outputText = new Text("");
		setTextVisibilityAndContents(outputText, false, null);
		
		Button calculateDeterminantButton = new Button("Calculate Determinant");
		
		ComboBox matrixVariableComboBox = generateMatrixVariableComboBox();
		
		// The ComboBox/calculate button will be placed horizontally in the center
		HBox matrixVariableSelectionArea = new HBox(5);
		matrixVariableSelectionArea.getChildren().addAll(calculateDeterminantButton, matrixVariableComboBox);
		matrixVariableSelectionArea.setMaxSize(300, 300);
		matrixVariableSelectionArea.setAlignment(Pos.CENTER);

		//The outputText will be directly below the ComboBox/Button
		VBox inputAndMessageSection = new VBox(5);
		inputAndMessageSection.getChildren().addAll(matrixVariableSelectionArea, outputText);
		inputAndMessageSection.setMaxSize(400, 300);
		inputAndMessageSection.setAlignment(Pos.CENTER);
		
		//When the button is clicked, the matrix will be verified to have a determinant,
        	// then the determinant is calculated and the outpuText is displayed.
		EventHandler<MouseEvent> setCalculateDeterminantButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {	
				calculateDeterminant(matrixVariableCollection, matrixVariableComboBox, outputText);
			} 
		};
		calculateDeterminantButton.addEventFilter(MouseEvent.MOUSE_CLICKED, setCalculateDeterminantButtonClicked); 
		
		determinantRoot.setAlignment(menuButton, Pos.TOP_LEFT);
		determinantRoot.setAlignment(title, Pos.TOP_CENTER);
		
		determinantRoot.getChildren().addAll(background, title, menuButton, inputAndMessageSection);
		
		addButtonToMenuSceneEventHandler(stage, menuScene, menuButton);
	}
		
	public static void setUpMultiplicationScene(Scene multiplicationScene, Scene menuScene, Stage stage, MatrixVariableCollection matrixVariableCollection) {
	
		StackPane multiplicationRoot = (StackPane)multiplicationScene.getRoot();
		
		MatrixInputField outputMatrixField = new MatrixInputField(5, 5);		
		
		Text title = new Text("Matrix Multiplication"); 
		title.setFont(new Font(45));
		
		Rectangle background = new Rectangle(WINDOW_WIDTH, WINDOW_HEIGHT, Color.GHOSTWHITE);
		Button menuButton = new Button("Menu");
		
		
		Text outputText = new Text("");
		outputText.setFill(Color.RED);
		
		ComboBox leftMatrixVariableComboBox = generateMatrixVariableComboBox();
		ComboBox rightMatrixVariableComboBox = generateMatrixVariableComboBox();
		
		Button computeButton = new Button("Compute");
		
		HBox variableSelectionArea = new HBox(5);
		variableSelectionArea.setMaxSize(300, 300);
		variableSelectionArea.setAlignment(Pos.BOTTOM_RIGHT);
		
		variableSelectionArea.getChildren().addAll(leftMatrixVariableComboBox, rightMatrixVariableComboBox, computeButton);
		
		VBox variableSelectionAndMessageOutput = new VBox(5);
		variableSelectionAndMessageOutput.setMaxSize(500, 500);
		variableSelectionAndMessageOutput.setAlignment(Pos.BOTTOM_RIGHT);

		variableSelectionAndMessageOutput.getChildren().addAll(variableSelectionArea, outputText);
		
		
		HBox gridPaneHolder = new HBox();
		gridPaneHolder.getChildren().add(outputMatrixField.getMatrixGridPane());
		gridPaneHolder.setMaxSize(500, 500);
		gridPaneHolder.setAlignment(Pos.CENTER);
		
		
		multiplicationRoot.setAlignment(menuButton, Pos.TOP_LEFT);
		multiplicationRoot.setAlignment(title, Pos.TOP_CENTER);
		multiplicationRoot.setAlignment(variableSelectionAndMessageOutput, Pos.BOTTOM_RIGHT);
		multiplicationRoot.getChildren().addAll(background, title, menuButton, gridPaneHolder, variableSelectionAndMessageOutput);

		EventHandler<MouseEvent> setComputeButtonClicked = new EventHandler<MouseEvent>() { 
		
			@Override 
			public void handle(MouseEvent event) {				
				String leftComboBoxContents = (String)leftMatrixVariableComboBox.getValue();
				String rightComboBoxContents = (String)rightMatrixVariableComboBox.getValue();
				//if either of the comboBoxes are empty, send out error.
				if( (leftMatrixVariableComboBox.getValue() == null) || (rightMatrixVariableComboBox.getValue() == null) ) {
					setTextVisibilityAndContents(outputText, true , "Select two matricies for computation");
				}else if(  ( matrixVariableCollection.getMatrix(leftComboBoxContents).getNumRows() == 0 ) || 
				           ( matrixVariableCollection.getMatrix(rightComboBoxContents).getNumRows() == 0 ) ) {
			
					setTextVisibilityAndContents(outputText, true, "Initialize both of the matricies before computation");
				}else{
					setTextVisibilityAndContents(outputText, false, null);
					multiplyMatriciesAndSetOutputMatrixField(outputMatrixField, matrixVariableCollection, outputText, (String)leftMatrixVariableComboBox.getValue(), (String)rightMatrixVariableComboBox.getValue(), gridPaneHolder);
				}
				
			} 

		};
		computeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, setComputeButtonClicked);
		
		addButtonToMenuSceneEventHandler(stage, menuScene, menuButton);
	 

	}
	
	//Methods used within the Scene setups///////////////////////////////////////////////////////
	
	
	//menuScene supporting Methods///////////////////////////////////////////////////////////////
	
	public static void addEventHandlersForSwitchingScenes(Stage stage, Scene declareMatrixScene, Scene determinantScene, Scene multiplicationScene, Button declareMatrixButton, Button determinantButton, Button multiplicationButton) {
		
		//Switch scene to declareMatrixScene
		EventHandler<MouseEvent> declareMatrixButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {
				stage.setScene(declareMatrixScene);
			} 
		};
		declareMatrixButton.addEventFilter(MouseEvent.MOUSE_CLICKED, declareMatrixButtonClicked); 
		
		//Switch scene to determinantScene
		EventHandler<MouseEvent> determinantButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {
				stage.setScene(determinantScene);
			} 
		};
		determinantButton.addEventFilter(MouseEvent.MOUSE_CLICKED, determinantButtonClicked); 
		
		
		//Switch scene to multiplicationScene
		EventHandler<MouseEvent> multiplicationButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {	
				stage.setScene(multiplicationScene);
			} 
		};
		multiplicationButton.addEventFilter(MouseEvent.MOUSE_CLICKED, multiplicationButtonClicked);
	}	
	
	
	
	//declareMatrixScene supporting Methods//////////////////////////////////////////////////////
	
	//A Matrix from the MatrixVariableCollection is declared to have the values from the TextFields, but valid entries are checked for.
	public static void setVariableToCurrentTextFields(MatrixVariableCollection matrixVariableCollection, MatrixInputField matrixInputField, ComboBox matrixVariableComboBox, Text errorText) {
		
		boolean onlyValidInputs = true;
		Matrix selectedMatrix = matrixVariableCollection.getMatrix((String)matrixVariableComboBox.getValue());
		String currentTextFieldString = "";
		Double valueFromString = 0.0;
		
		//Only allow for ANY parts of the matrix to be added if ALL of the inputs are valid
		// Entries are checked to see if they are valid during event handleing and prior to this method call
			
		//must set size according to the textFields Present
		selectedMatrix.setValuesMatrixOfSize(matrixInputField.getNumRows(), matrixInputField.getNumCols());
			
		for(int r = 0; r < selectedMatrix.getNumRows(); r++) {
			for(int c = 0; c < selectedMatrix.getNumCols(); c++) {
				//Excpetion handling for string to Double formatting
				try
				{
					//Note on Data Types:
					// Using either a float or Double data type will create some(marginal) error, 
					// but for the most precision, the decimal data type could have been used.
					// I decided to use Doubles becuase when testing extremely small and large numbers, I saw almost no precision errors.
					
					//Attempt to convert a string to a Double
					currentTextFieldString = matrixInputField.getTextFieldsMatrix().get(r).get(c).getCharacters().toString();
					valueFromString = Double.parseDouble(currentTextFieldString);
				}
				catch(NumberFormatException e)
				{
					//String cannot be converted to a valid Double, so error is displayed and the determinant will not be calculated
					setTextVisibilityAndContents(errorText, true, "Incorrect numerical input");
					onlyValidInputs = false;
					break;
				}
			}
		}
		
		//loop through and add values of textfield to the selectedMatrix
		if(onlyValidInputs) {
			for(int i = 0; i < selectedMatrix.getNumRows(); i++) {
				for(int j = 0; j < selectedMatrix.getNumCols(); j++) {
					currentTextFieldString = matrixInputField.getTextFieldsMatrix().get(i).get(j).getCharacters().toString();
					valueFromString = Double.parseDouble(currentTextFieldString);
					
					selectedMatrix.getValuesMatrix().get(i).set(j, valueFromString);
				}
			}

		}

		//will print out the current values of selectedMatrix
		/*
		for(int a = 0; a < selectedMatrix.getNumRows(); a++) {
			for(int b = 0; b < selectedMatrix.getNumCols(); b++) {
				System.out.print("" + selectedMatrix.getValuesMatrix().get(a).get(b) + " ");
			}
			System.out.println();
		}
		*/
		
	}
	
	
	
	//determinantScene supporting Methods////////////////////////////////////////////////////////
	
	// Dimensions are set as long as the comboBoxes are not null
	public static void setDimensionsAccordingToComboBoxes(StackPane declareMatrixRoot, MatrixInputField matrixInputField,HBox gridPaneHolder, GridPane matrixGridPane, ComboBox matrixRowSizeComboBox, ComboBox matrixColSizeComboBox) {
		
		int currentNumRows = matrixInputField.getNumRows();
		int currentNumCols = matrixInputField.getNumCols();
		
		//Gets new row/col sizes from ComboBoxes
		Integer inputNumRows = (Integer)matrixRowSizeComboBox.getValue();
		Integer inputNumCols = (Integer)matrixColSizeComboBox.getValue();
		
		// null entries mean that the num rows/cols will stay what they were
		if(inputNumRows == null) {
			inputNumRows = currentNumRows;
		}
		
		if(inputNumCols == null) {
			inputNumCols = currentNumCols;	
		}
		
		//Set size, style the matrixGridPane, and update the matrixGridPane
		matrixInputField.setTextFieldsMatrixOfSize((int)inputNumRows, (int)inputNumCols);	
		matrixInputField.styleMatrixGridPane();
		matrixGridPane = matrixInputField.getMatrixGridPane();
		
		//Remove the previous gridPane to add in the new one
		gridPaneHolder.getChildren().remove(0);
		gridPaneHolder.getChildren().add(matrixGridPane);
	}
	
	//The matrix determinant will be calculated when there are no errors. 
	//The value of the determinant is displayed in the outputText.
	public static void calculateDeterminant(MatrixVariableCollection matrixVariableCollection, ComboBox matrixVariableComboBox, Text outputText) {
		
		boolean nullMatrixInputted = false;
		Matrix selectedMatrix = new Matrix(0,0);
		
		// Attempt to assign the selectedMatrix and check for a null pointer
		try {
			selectedMatrix = matrixVariableCollection.getMatrix((String)matrixVariableComboBox.getValue());
		}catch(NullPointerException e) {
			nullMatrixInputted = true;
		}
		
		//Error conditions lead to an error outputText and
		// the correct conditions lead to the determinant being calculated and displayed.
		if( (selectedMatrix.getNumRows() == 0) || (selectedMatrix.getNumCols() == 0) || (nullMatrixInputted == true)) {
			setTextVisibilityAndContents(outputText, true, "The selected matrix needs to be declared first");
		}else if(selectedMatrix.getNumRows() != selectedMatrix.getNumCols()) {
			setTextVisibilityAndContents(outputText, true, "The selected matrix must be square in order to calculate the determinant");
		}else if(selectedMatrix.getNumRows() == selectedMatrix.getNumCols()) {
			// Determinant is calculated and returned to be a part of the outputText.
			setTextVisibilityAndContents(outputText, true, "The determinant is " + selectedMatrix.calculateDeterminant());
		}
	}	
	

	
	//multiplicationScene supporting Methods/////////////////////////////////////////////////////
	
	//The two matricies selcted form the ComboBoxes will be multiplied. 
	//RightMatrix and LeftMatrix correspond to the Right and Left comboBoxes
	//Errors in multiplication are checked for, and the resulatantMatrix is made and displayed
	public static void multiplyMatriciesAndSetOutputMatrixField(MatrixInputField outputMatrixField, MatrixVariableCollection matrixVariableCollection, Text outputText, String leftComboBoxContents, String rightComboBoxContents, HBox gridPaneHolder) {	
		
		//matricies assigned according to the ComboBoxes
		Matrix leftMatrix = matrixVariableCollection.getMatrix(leftComboBoxContents);
		Matrix rightMatrix = matrixVariableCollection.getMatrix(rightComboBoxContents);
		
		// The condition for matrix multiplication to be possible is: the numCols of the left matrix must be the same as the num Rows of the right matrix
		if(leftMatrix.getNumCols() == rightMatrix.getNumRows()) {
		
			//The resultant matrix must have the same number of rows as the leftMatrix and the same number of cols as the rightMatrix
			Matrix resultantMatrix = new Matrix(leftMatrix.getNumRows(), rightMatrix.getNumCols());
			
			//multiply the two matricies (in Matrix class)
			resultantMatrix = leftMatrix.multiply(rightMatrix);
	
			// The output matrix must be the size of the resultant matrix
			outputMatrixField.setTextFieldsMatrixOfSize(resultantMatrix.getNumRows(), resultantMatrix.getNumCols());
			
			//loop through and set the TextFieldsMatrix to contain the values of the resultantMatrix
			for(int r = 0; r < resultantMatrix.getNumRows(); r++) {		
				for(int c = 0; c < resultantMatrix.getNumCols(); c++) {
					outputMatrixField.getTextFieldsMatrix().get(r).set(c, new TextField(Double.toString(resultantMatrix.getValuesMatrix().get(r).get(c))));
				}
			}
			
			outputMatrixField.setTextFieldsMatrixOfSize(resultantMatrix.getNumRows(), resultantMatrix.getNumCols());	
			
			// The new GridPane must be restyled and uneditable
			outputMatrixField.styleMatrixGridPane();
			outputMatrixField.setMatrixGridUneditable();
			
			//Remove the old GridPane and insert the new GridPane
			gridPaneHolder.getChildren().remove(0);
			gridPaneHolder.getChildren().add(outputMatrixField.getMatrixGridPane());
		}else {
			//Executed when matrix multiplication is not possible			
			setTextVisibilityAndContents(outputText, true, "Matrix multiplication is not possible(Check the dimensions of the matricies)");
		}
		
	}	
	
	
	
	//Methods used in multiple Scenes//////////////////////////////////////////////////////////////
	
	
	// Adds an event handler that sends the user to the menu when the button (parameter) is clicked
	public static void addButtonToMenuSceneEventHandler(Stage stage, Scene menuScene, Button button) {
		EventHandler<MouseEvent> menuButtonClicked = new EventHandler<MouseEvent>() { 
			@Override 
			public void handle(MouseEvent event) {
				stage.setScene(menuScene);
			} 
		};
		button.addEventFilter(MouseEvent.MOUSE_CLICKED, menuButtonClicked);
	}	
	
	//Method to simplify modifying a Text's visibility and String
	public static void setTextVisibilityAndContents(Text textIn, boolean visibility, String strIn) {
		
		textIn.setVisible(visibility);
		//null will be passed in when only visibility needs to be changed
		if(strIn != null) {
			textIn.setText(strIn);
		}
	}
	
	//Creates a ComboBox specifically for chosing a matrix out of the matrixVariableCollection
	public static ComboBox generateMatrixVariableComboBox() {
		ComboBox tempComboBox = new ComboBox();

        	tempComboBox.getItems().add("A");
        	tempComboBox.getItems().add("B");
        	tempComboBox.getItems().add("C");
        	tempComboBox.getItems().add("D");
        	tempComboBox.getItems().add("E"); 
		
		return tempComboBox;
	}
	
}

/*Works Cited
 *
 * JavaFX 8. Oracle JavaFX Docs, Oracle, docs.oracle.com/javase/8/javafx/api/.
 * Jenkov, Jakob. JavaFX Overview. Jenkov.com, Jenkov Aps , 17 May 2016, tutorials.jenkov.com/javafx/overview.html#stage.
 * Schildt, Herbert. Java: the Complete Reference. 9th ed., McGraw Hill, 2014.
 * Random Stack OverFlow Answers
 */
