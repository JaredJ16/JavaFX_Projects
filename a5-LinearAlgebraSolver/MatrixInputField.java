import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import javafx.geometry.Pos;


//The MatrixInputField class was designed to hold a GridPane. The GridPane is a collection of TextFields where the user
// could enter in values for the matrix. The MatrixInputField extends Matrix becuase it also needs to store all of the values
// inputted by the user into a Matrix object.

public class MatrixInputField extends Matrix
{
	private static final int MAX_MATRIX_SIZE = 5;

	//The textFieldsMatrix is a two dimensional ArrayList that holds all of the TextFields that will be placed into their corresponding positions in the GridPane.
	private ArrayList<ArrayList<TextField>> textFieldsMatrix;
	
	// The matrixGrid will be added to the declareMatrix scene where the user will see a grid of TextFields where they can input their numbers.
	private GridPane matrixGrid;	
	
	public MatrixInputField(int numRowsIn, int numColsIn) {
		//Use the parent constructor to set the number of columns and rows.
		super(numRowsIn, numColsIn);
				
		//Initialize the two dimensional ArrayList, generate all of the TextFields, and store the TextFields into the ArrayList.
		textFieldsMatrix = new ArrayList<ArrayList<TextField>>();
		setTextFieldsMatrixOfSize(numRowsIn, numColsIn);
		
		//Place the TextFields that were in the textFieldsMatrix into the matrixGrid so that they can be displayed in the declareMatrixScene.
		matrixGrid = new GridPane();
		establishMatrixGridOfSize(numRowsIn, numColsIn);
	}
	
	
	public void setTextFieldsMatrixOfSize(int numRowsIn, int numColsIn) {
		//The starting column and row sizes determine whether TextField rows or columns will be deleted or added.
		int startRowSize = getNumRows();
		int startColSize = getNumCols();
		
		//The method follows two main paths: if the textFieldsMatrix is empty, then generate the rows and columns neccesary
		// or if the textFieldsMatrix is not empty, then add or remove rows or columns according to the starting and ending amounts of rows and columns.
		if(textFieldsMatrix.size() == 0) {
			establishTextFieldsMatrix(numRowsIn, numColsIn);			
		}else{
			//Rows of TextFields need to be added
			if(startRowSize < numRowsIn) {
				// Loops from the first row index that will be added to the last index where a row will be added.
				for(int i = startRowSize; i < numRowsIn; i++) {
					//Adds a new row to the textFieldsMatrix
					textFieldsMatrix.add(new ArrayList<TextField>());
					
					// Loops through and adds columns to the new row, so now the amount of columns in each row will equal the number of original columns.
					for(int j = 0; j < startColSize; j++) {
						//Each TextField recieves a default value of 0 and is 5 width.
						textFieldsMatrix.get(i).add(new TextField("0"));
						textFieldsMatrix.get(i).get(j).setPrefColumnCount(5);
					}
				}
			}	
			//Columns of TextFields need to be added
			if(startColSize < numColsIn) {
				// Loops from the first row to the last row. (At this point, all rows have been added)
				for(int l = 0; l < numRowsIn; l++) {
					// Loops from the current column to the last column that will be added.
					for(int m = startColSize; m < numColsIn; m++) {
						//Each TextField recieves a default value of 0 and is 5 width.
						textFieldsMatrix.get(l).add(new TextField("0"));
						textFieldsMatrix.get(l).get(m).setPrefColumnCount(7);
					}
				}
			}
			
			//Rows of TextFields need to be removed
			if(startRowSize > numRowsIn) {
				//Remove the rows starting with the last row and looping until the rows that need to stay.
				for(int p = startRowSize; p > numRowsIn; p--) {
					//p-1 is removed due to the size being one greater than the index
					textFieldsMatrix.remove(p - 1);
				}
			}
			
			//Columns of TextFields need to be removed
			if(startColSize > numColsIn) {
				//Loop through all rows to remove the columns in each individual row. The number of rows will already be numRowsIn
				for(int q = 0; q < numRowsIn; q++) {
					//Remove the correct amount of rows in each column starting with the last column
					for(int r = startColSize; r > numColsIn; r--) {
						// Loops through all columns and gets the current row and removes (r-1) becuase the size is one greater than the index
						textFieldsMatrix.get(q).remove(r - 1);
					}
				}
			}
		}
		// Initliaze the Matrix that will hold the values of the TextField
		setValuesMatrixOfSize(numRowsIn, numColsIn);
		
		// After the textFieldsMatrix is modified to the correct size, the matrixGrid needs to also be changed to the correct size. 
		establishMatrixGridOfSize(numRowsIn, numColsIn);
	}
	
	//Establishes the textFields for a matrix if it is currently empty
	public void establishTextFieldsMatrix(int numRowsIn, int numColsIn) {
		int startRowSize = 0;
		int startColSize = 0;
			
		//adds rows
		for(int i = startRowSize; i < numRowsIn; i++) {
			//add new row
			textFieldsMatrix.add(new ArrayList<TextField>());
				
			//configure textFields for the new row
			for(int j = startColSize; j < numColsIn; j++) {
				textFieldsMatrix.get(i).add(new TextField("0"));
				textFieldsMatrix.get(i).get(j).setPrefColumnCount(7);	
			}		
		}
	}
	
	//Setups up the matrixGrid irregardless of current size
	public void establishMatrixGridOfSize(int numRowsIn, int numColsIn) {
		//current matrixGrid is dumped and a new GridPane is assigned
		matrixGrid = null;
		matrixGrid = new GridPane();

		for(int i = 0; i < textFieldsMatrix.size(); i++) {
			for(int j = 0; j < textFieldsMatrix.get(0).size(); j++) {
				setMatrixGridPosition(i, j);	
			}	
		}
		//Position the matrixGridPane in the center and leave gaps between the TextFields.
		styleMatrixGridPane();
	}
	
	//Sets the (row, col) position of the matrixGrid to the corresponding TextField in the TextFieldsMatrix
	public void setMatrixGridPosition(int row, int col) {
		
		matrixGrid.setRowIndex(getIndexOfTextField(row, col), row);
		matrixGrid.setColumnIndex(getIndexOfTextField(row, col), col);
		matrixGrid.getChildren().add(getIndexOfTextField(row, col));
		
	}
	
	// All textFields of a MatrixInputField are set to uneditable
	public void setMatrixGridUneditable() {
		for(int i = 0; i < getNumRows(); i++) {
			for(int j = 0; j < getNumCols(); j++) {
				getIndexOfTextField(i, j).setEditable(false);
			}
		}
	}
	
	public void styleMatrixGridPane() {
		matrixGrid.setAlignment(Pos.CENTER);
		matrixGrid.setPrefSize(500, 500);
		matrixGrid.setHgap(10);
		matrixGrid.setVgap(10);		
	}
	
	
	//getters
	public GridPane getMatrixGridPane() {
		return matrixGrid;
	}
	
	public TextField getIndexOfTextField(int rowIn, int colIn) {
		return textFieldsMatrix.get(rowIn).get(colIn);
	}
	
	public ArrayList<ArrayList<TextField>> getTextFieldsMatrix() {
		return textFieldsMatrix;
	}

}
