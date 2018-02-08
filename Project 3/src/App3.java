class App3
{
    public static void main(String args[])
    {
	int n=3;    //size

	// define a Matrix
	Matrix A=new DenseMatrix(n);
        //Set Elements
	A.set(0,0,2.0);
	A.set(0,1,1.0);
	A.set(1,1,1.0);
	A.set(1,2,-3.0);
	A.set(2,0,1.0);
	A.set(2,2,1.0);
	
	A.info();     // display info
	A.display();  // display matrix in dense format

	// Define a vector
        Vector b=new VectorArray(A.getSize());
	b.set(0,1.0);
	b.set(1,1.0);
	b.set(2,1.0);
	
        System.out.println("Vector b");
        b.display();
	// multiplication
        Vector f=A.multiply(b); // f=A*b
	System.out.println("Matrix-Vector Multiplication x=A*b");
        f.display();
    }
}
