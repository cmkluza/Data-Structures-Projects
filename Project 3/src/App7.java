class App7
{
    public static void main(String args[])
    {
	int size,nnz;
	long startTime, endTime;
	EasyIn easy = new EasyIn();
	
	// sparse matrix A
	System.out.println("Size of the sparse matrix to generate?");
	size = easy.readInt();     
	System.out.println("Nb of non-zero elements?");
	nnz = easy.readInt();
	Matrix A=new SparseMatrixLinkedList(size,nnz);
	A.info();

	// Define a vector
	Vector b=new VectorArray(A.getSize()); // array based-implementation	
	b.fill(); // fill randomly
	
	// multiplication
        startTime = System.currentTimeMillis(); // capture time
	Vector x=A.multiply(b); // x=A*b
	endTime = System.currentTimeMillis(); // capture time
	System.out.println("Matrix-Vector Multiplication using sparse matrix done in "+(endTime-startTime)+"ms");
    }
}
