class App5
{
    public static void main(String args[])
    {
	int n=3;    //size
	int nnz=6;  //nnz (non-zero elements)

	Matrix A1=new DenseMatrix(n);
        //Set Elements
	A1.set(0,0,2.0);
	A1.set(0,1,1.0);
	A1.set(1,1,1.0);
	A1.set(1,2,-3.0);
	A1.set(2,0,1.0);
	A1.set(2,2,1.0);
	
	System.out.println("Original Matrix in Dense Format");
	A1.info();
	A1.display();  // display matrix in dense format

        Matrix A2=new SparseMatrixLinkedList(A1); // from dense to sparse

	System.out.println("Original Matrix in Linked-List Format");
	A2.info();
	A2.display();  // display matrix in link-list format
	
        A2.set(0,0,1.0); // change one element
	A2.set(5,2,1.0); // increase dynamically

	System.out.println("Modified Matrix in Linked-List Format");
	A2.info();
	A2.display();  // display matrix in link-list format

	Matrix A3=new DenseMatrix(A2);
	A3.info();
	A3.display();  // display matrix in dense format

	
	System.out.println("Diagonal elements");
	double[] diagS=A2.getDiagonal();
	double[] diagD=A3.getDiagonal();
	for (int i=0;i<A2.getSize();i++){System.out.println(diagD[i]+" "+diagS[i]);}
	System.out.println();
       
}
}
