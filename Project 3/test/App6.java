class App6
{
	public static void main(String args[])
	{
		int size,nnz;
		long startTime, endTime;
		EasyIn easy = new EasyIn();


		// dense matrix A1
		System.out.println("Size of the dense matrix to generate?");
		size = easy.readInt();
		System.out.println("Nb of non-zero elements?");
		nnz = easy.readInt();
		Matrix A1=new DenseMatrix(size,nnz);
		A1.info();
		// sparse matrix A2 (from dense to sparse)
		Matrix A2=new SparseMatrixLinkedList(A1);
		A2.info();


		// Vector b and fill randomly

		Vector b=new VectorArray(A1.getSize()); // array based-implementation
		//Vector b=new VectorLL(A1.getSize()); 	// linked list based implementation
		b.fill(); // fill randomly


		// multiplication A1*b
		startTime = System.currentTimeMillis(); // capture time
		Vector x=A1.multiply(b); // x=A1*b
		endTime = System.currentTimeMillis(); // capture time
		System.out.println("Matrix-Vector Multiplication using dense matrix done in "+(endTime-startTime)+"ms");


		// multiplication A2*b
		startTime = System.currentTimeMillis(); // capture time
		x=A2.multiply(b); // x=A2*b
		endTime = System.currentTimeMillis(); // capture time
		System.out.println("Matrix-Vector Multiplication using sparse matrix done in "+(endTime-startTime)+"ms");
	}
}
