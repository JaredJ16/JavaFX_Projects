LinearAlgebraSolver README



Purpose of the Program

	The purpose of LinearAlgebraSolver is to perform basic calculations
	on matricies such as solving for a determinant or multiplying two
	matricies together. The inputted matricies can be rectangular or square,
	but must have a row size between [1,5] and a col size between [1,5].
	Extensions to the program could be added such as an eigenvalue calculator.



Scenes within LinearAlgebraSolver

---------------------------------------------------------------------------------------------------
	All Scenes:
	
		Every scene other than the menuScene has menuButton in the upper left
		corner to return the user to the menuScene.
		Every scene also has a title at the top and a GhostWhite background.
	
	
---------------------------------------------------------------------------------------------------
	Menu Scene:

		The menuScene contains the buttons to navigate to the other scenes.
	
	
---------------------------------------------------------------------------------------------------	
	Declare Matrix Scene:

		The declareMatrixScene has ComboBoxes for choosing the row number,
		the column number, and the name of the matrix to be declared. In the center 
		of the declareMatrixScene is the GridPane, which contains all the TextFields 
		where the user can input the numbers to be stored in a Matrix. The user also 
		has two buttons to click: the setDimensionsButton, which changes the dimensions of
		the TextField GridPane so that it matches the dimensions described by the ComboBoxes,
		and the setVariableButton which sets the selcted Matrix of the MatrixCollectionVariables
		to the current numbers in the GridPane.
	

---------------------------------------------------------------------------------------------------
	Determinant Scene:

		The determinantScene features a matrixVariableComboBox, a calculateMatrixButton, and
		an outputText. The matrixVariableComboBox is where the user selects the matrix. Once
		the	calculateMatrixButton is clicked, the outputText will either output the determinant
		or it will present an error message to let the user know that some of the input selections
		need to be changed. The determinat is found using a recursive cofactor method.

---------------------------------------------------------------------------------------------------	
	Multiplication Scene:

		The multiplicationScene has a leftMatrixVariableComboBox, a rightMatrixVariableComboBox, 
		a computeButton, an errorText, and a GridPane of uneditable TextFields. The user chooses 
	    the left and right matricies to multiply together with their corresponding ComboBoxes. 
		Once the user clicks the computeButton, then the GridPane is updated to be the resulatant
		matrix of the multiplication or an errorText is displayed. When the user first enters
		the multiplicationScene, the GridPane is originally an empty 5x5 matrix.
	
	
---------------------------------------------------------------------------------------------------

	
Note on the MatrixInputField Class

I noticed very late into the project that the MatrixInputField should not be a child of a Matrix,
instead it should just contain a Matrix. The class still functions as it is, but it does not follow correct 
object oriented design patterns becuase it "is not" a Matrix.