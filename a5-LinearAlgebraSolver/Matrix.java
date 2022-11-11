import java.util.ArrayList;


/*
	The Matrix class holds a two dimensional ArrayList of Doubles
	Operations such as changing the size of a matrix, multiplying matricies, 
	and finding the determinant of a matrix can be done.
*/
public class Matrix 
{
	private static final int MAX_MATRIX_SIZE = 5;
	
	//A 2D arraylist is utilized becuase the numbuer of rows and columns of the matrix may vary as the program continues
	private ArrayList<ArrayList<Double>> valuesMatrix;
	
	private int numRows;
	private int numCols;

	
	public Matrix(int numRowsIn, int numColsIn) {
		//numRows and numCols are both initialized to zero at the start so that when setValuesMatrixOfSize works correctly when called
		numRows = 0;
		numCols = 0;
		
		// Initiliazes the 2d arraylist to the correct dimensions based on the number of rows and columns inputted
		valuesMatrix = new ArrayList<ArrayList<Double>>();
		setValuesMatrixOfSize(numRowsIn, numColsIn);
		
		//Once the valuesMatrix has been inilized to its correct size, then the number of rows and columns can be set.
		numRows = numRowsIn;
		numCols = numColsIn;
	}
	
	//Set valuesMatrixOfSize follows one of two paths:
	// 1. Establish a matrix of a certain size
	// 2. Modify the current size of a Matrix's rows and cols
	public void setValuesMatrixOfSize(int numRowsIn, int numColsIn) {
		
		//The starting column and row sizes determine whether TextField rows or columns will be deleted or added.
		int startRowSize = getNumRows();
		int startColSize = getNumCols();
		
		//Decides path: establish a valuesMatrix(if it is of size 0) or modify the size of an already set valuesMatrix
		if(valuesMatrix.size() == 0) {
			establishValuesMatrix(numRowsIn, numColsIn);			
		}else{
			//Rows of TextFields need to be added
			if(startRowSize < numRowsIn) {
				// Loops from the first row index that will be added to the last index where a row will be added.
				for(int i = startRowSize; i < numRowsIn; i++) {
					//Adds a new row to the textFieldsMatrix
					valuesMatrix.add(new ArrayList<Double>());
		
					// Loops through and adds columns to the new row, so now the amount of columns in each row will equal the number of original columns.
					for(int j = 0; j < startColSize; j++) {				
						//Each TextField recieves a default value of 0 and is 5 width.
						valuesMatrix.get(i).add(new Double(0.0));
					}
				}
			}	
			
			//Columns of TextFields need to be added
			if(startColSize < numColsIn) {
				// Loops from the first row to the last row. (At this point, all rows have been added)
				for(int l = 0; l < numRowsIn; l++) {
					// Loops from the current column to the last column that will be added.
					for(int m = startColSize; m < numColsIn; m++) {
						//Each new TextField recieves a default value of 0
						valuesMatrix.get(l).add(new Double(0.0));
					}
				}
			}
			
			//Rows of TextFields need to be removed
			if(startRowSize > numRowsIn) {
				//Remove the rows starting with the last row and looping until the rows that need to stay.
				for(int p = startRowSize; p > numRowsIn; p--) {
					//p-1 is removed due to the size being one greater than the index
					valuesMatrix.remove(p - 1);
				}
			}
			
			//Columns of TextFields need to be removed
			if(startColSize > numColsIn) {
				//Loop through all rows to remove the columns in each individual row. The number of rows will already be numRowsIn
				for(int q = 0; q < numRowsIn; q++) {
					//Remove the correct amount of rows in each column starting with the last column
					for(int r = startColSize; r > numColsIn; r--) {
						// Loops through all columns and gets the current row and removes (r-1) becuase the size is one greater than the index
						valuesMatrix.get(q).remove(r - 1);
					}
				}
			}
			
		}
		
		//The number or rows and columns can now be set becuase the matrix is of the new size
		setNumRows(numRowsIn);
		setNumCols(numColsIn);
	}
	
	
	public void establishValuesMatrix(int numRowsIn, int numColsIn) {
		
		int startRowSize = 0;
		int startColSize = 0;
			
		//adds rows
		for(int i = startRowSize; i < numRowsIn; i++) {
			//add new row
			valuesMatrix.add(new ArrayList<Double>());
				
			//configure elements for new rows
			for(int j = startColSize; j < numColsIn; j++) {
				//add columns to row
				valuesMatrix.get(i).add(new Double(0.0));
			}		
		}
	}
	
	/*
		Calculates the determinant using the cofactor method (recursive)
		In the cofactor method, a matrix is broken up into two dimsional matricies.
		These smaller matricies have a corresponding coefficient.
		
		The determinant is calculated by adding together the multiplication
		of the corresponding coefficient to the determinant for each 2x2 matricies 
		
		This source explains cofactor expansion but it uses the term minor instead of subMatrix.
		https://textbooks.math.gatech.edu/ila/determinants-cofactors.html
		
	*/
	public Double calculateDeterminant() {
		Double determinant = 0.0;
		
		if(getNumRows() == 1) {
			//A 1x1 matrix has a determinant equal to its only value
			determinant = valuesMatrix.get(0).get(0);
		}else if(getNumRows() == 2) {
			//If the matrix is 2x2, the determinant can be easily calculated
			determinant = calculateTwoByTwoDeterminant(this);
		}else {
			//A matrix larger than 2x2 must be recursively broken down into subMatricies in order to calculare the determinant
			for(int c = 0; c < getNumCols(); c++) {
				//coefficent assigned negative or postivie in an alternating manner
				Double coefficient = valuesMatrix.get(0).get(c);
			
				if( (c % 2) != 0 ){
					coefficient *= -1;
				}
				
				//The determinant is the sum of the determinants of the subMatricices
				determinant += coefficient * generateSubMatrixAndReturnDeterminant(c, getNumRows());
			}
		}
		return determinant;
	}

	
	//Returns the determinant of the subMatrix if it is 2x2 or sends the matrix back through calculateDeterminant to be broken up again.
	public Double generateSubMatrixAndReturnDeterminant(int crossOutColumn, int sizeIn) {
		
		Double subDeterminant = 0.0;
		
		//The subMatrix is always one size smaller than the current matrix
		int newSize = sizeIn - 1;
		
		//The crossOutRow  and the crossOutCol is the row and col that will be ignored when forming the submatrix
		//The crossOutCol is sent in as a parameter
		int crossOutRow = 0;
		
		//subMatrix intiliazed to 0 becuase it must be built differntly according to the crossOutRow and the crossOutColumn
		Matrix subMatrix = new Matrix(newSize, newSize);
	
		
		//Note: when getNumRows(), getNumCols(), or valuesMatrix is used, they are making calls to the current matrix and not the subMatrix
		// A subMatrix only needs to be made if its size is greater than 1
		if(newSize != 1) {	
			//Loops through the rows of the current larger matrix to make a sub matrix
			for(int i = 0; i < getNumRows(); i++) {
				//The counter variable is used to make sure the column indexes are inserted in ascending order
				//The row indexes will always be i-1 becuase the crossOutRow is chosen to be the first row
				int counter = 0;
				//Loops through the columns and only adds in an number into the subMatrix if it is not a part of the crossOutCol and the crossOutRow
				for(int c = 0; c < getNumCols(); c++){
					if((i != crossOutRow) && (c != crossOutColumn)) {
						//same comment as above, just a reminder
						//The counter variable is used to make sure the column indexes are inserted in ascending order
						//The row indexes will always be i-1 becuase the crossOutRow is chosen to be the first row					
						subMatrix.getValuesMatrix().get(i - 1).set(counter, valuesMatrix.get(i).get(c));
						
						counter++;
					}
				}
			}
		}
		
		//Submatricies and calculating subDeterminants will occur until every subMatrix is a 2x2
		// and in that case	the 2x2 determinant is calculated using the calculateTwoByTwoDeterminant(subMatrix) method
		if(newSize == 2) {	
			subDeterminant = calculateTwoByTwoDeterminant(subMatrix);
		}else if(newSize > 2) {
			//calculateDeterminant will call generateSubMatrixAndReturnDeterminant if the size of the matrix is not yet 2 by 2.
			subDeterminant = subMatrix.calculateDeterminant();
		}
		return subDeterminant;
	}
	
	
	public Double calculateTwoByTwoDeterminant(Matrix matrix) {
		// |a,b|
		// |c,d|
		// Determinant is calculated by performing ad-bc;
		Double det = 0.0;
		Double ad = 0.0;
		Double bc = 0.0;
		
		//Double check for a two by two matrix
		if(  (matrix.getNumCols() == 2) && (matrix.getNumCols() == 2) ) {
			//follows formula described above
			ad = matrix.getValuesMatrix().get(0).get(0) * matrix.getValuesMatrix().get(1).get(1);
			bc = matrix.getValuesMatrix().get(0).get(1) * matrix.getValuesMatrix().get(1).get(0);
			
			det = ad - bc;
		}
		return det;		
	}
	
	public Matrix multiply(Matrix rightMatrix) {
		//This method is only called when matrix multiplication is possible
		
		// If an AxB matrix is multiplied by a CxD matrix, then the number of rows A
		// and the number or cols is B. (A,B,C, and D represent the dimensions)
		Matrix resultant = new Matrix(getNumRows(), rightMatrix.getNumCols());
		
		// The (r,c) index of a resultant matrix is calculated by multiplying the rth row by the cth col.
		for(int r = 0; r < resultant.getNumRows(); r++) {		
			for(int c = 0; c < resultant.getNumCols(); c++) {
				//The this parameter represents the leftMatrix 
				resultant.getValuesMatrix().get(r).set(c, multiplyRowByCol(this, rightMatrix, r, c));
			}
		}
		return resultant;
	}
	
	
	//Only works for when row and col are of the same length, but matricies are only mulitplied
    // when this is the case, so this should be fine.
	public Double multiplyRowByCol(Matrix leftMatrixIn, Matrix rightMatrixIn, int rowIndex, int colIndex) {
		Double total = 0.0;
		
		//loops through the length of the row becuase rowLength = colLength so it doesn't matter
		for(int i = 0; i < returnRow(leftMatrixIn, 0).length; i++) {
			//The number to be returned is the sum of rowIndex(n) * colIndex(n) for all n where n is row/col length
			total += returnRow(leftMatrixIn, rowIndex)[i] * returnCol(rightMatrixIn, colIndex)[i];
		}
		
		return total;
	}
	
	//Converts a row from the matrixIn to a Double matrix
	public Double[] returnRow(Matrix matrixIn, int rowIndex) {
		Double[] row = new Double[matrixIn.getNumCols()];
		
		for(int col = 0; col < matrixIn.getNumCols(); col++) {
			row[col] = matrixIn.getValuesMatrix().get(rowIndex).get(col);
		}
		
		return row;
	}
	
	//Converts a col from the matrixIn to a Double matrix 
	public Double[] returnCol(Matrix matrixIn, int colIndex) {
		Double[] col = new Double[matrixIn.getNumRows()];
		for(int row = 0; row < matrixIn.getNumRows(); row++) {
			col[row] = matrixIn.getValuesMatrix().get(row).get(colIndex);
		}
		return col;
	}
	
	
	//Setters
	public void setNumRows(int numRowsIn) {
		numRows = numRowsIn;
	}
	
	public void setNumCols(int numColsIn) {
		numCols = numColsIn;
	}
	
	//Getters
	public ArrayList<ArrayList<Double>> getValuesMatrix() {
		return valuesMatrix;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
}
