// The MatrixVariableCollection class holds all of the matrix variables that can be declared.
// The class makes it easier to modify and declare matricies in the program.

public class MatrixVariableCollection
{

	private Matrix matrixA;
	private Matrix matrixB;
	private Matrix matrixC;
	private Matrix matrixD;
	private Matrix matrixE;

	public MatrixVariableCollection() {
		
		//All matricies are initiliazed to size zero until they are declared within the declareMatrixScene
		matrixA = new Matrix(0, 0);
		matrixB = new Matrix(0, 0);
		matrixC = new Matrix(0, 0);
		matrixD = new Matrix(0, 0);
		matrixE = new Matrix(0, 0);
		
	}
	
	/*
		A string is used to get each matrix because the matricies are selected from ComboBoxes, 
		and it is eay to return the current state of a ComboBox in terms of a string
		For example, a ComboBox could have choices: {A,B,C,D,E} and when (String)ComboBox.getValue() is
		evaluated, it returns the string associated with the matrix name.
	*/
	public Matrix getMatrix(String matrixVariableName) {
		
		Matrix temporaryMatrix;
		
		if(matrixVariableName.equals("A")) {
			
			temporaryMatrix = matrixA;
			
		}else if(matrixVariableName.equals("B")) {
			
			temporaryMatrix = matrixB;
			
		}else if(matrixVariableName.equals("C")) {
			
			temporaryMatrix = matrixC;
			
		}else if(matrixVariableName.equals("D")) {
			
			temporaryMatrix = matrixD;
			
		}else if(matrixVariableName.equals("E")) {
			
			temporaryMatrix = matrixE;
			
		}else {
			
			System.out.println("sdfasdfsd");
			temporaryMatrix = null;
			
		}
		
		return temporaryMatrix;
		
	}	

}