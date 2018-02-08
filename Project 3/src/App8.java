class App8 {
    public static void main(String args[]) {
        int size, nnz;
        EasyIn easy = new EasyIn();
        Matrix A;
        Vector x;

        // sparse matrix A
        System.out.println("Size of the sparse matrix to generate? (Enter 0 for pregenerated Matrix)");
        size = easy.readInt();
        if (size != 0) {
            System.out.println("Nb of non-zero elements?");
            nnz = easy.readInt();
            A = new SparseMatrixLinkedList(size, nnz);
            x = new VectorArray(A.getSize()); // array based-implementation
            x.fill(); // fill randomly (initial guess)
        } else {
            A = new SparseMatrixLinkedList();
            A.set(0, 0, 1.5);
            A.set(0, 1, 0.5);
            A.set(1, 0, 0.5);
            A.set(1, 1, 1.5);
            A.display();
            x = new VectorArray(A.getSize()); // array based-implementation
            // initial guess
            x.set(0, 0);
            x.set(1, 1);
        }

        A.info();

        // Compute the *largest* eigenvalue/eigenvector:     Ax=lambda x
        // Using the power method
        // Algo: Iterate: y<=A*x_old, lambda=normLoo(y), x_new=y/lambda
        //       until |lambda-lambda_prev|/|lambda_prev| < eps
        double lambda = 1, lambda_prev;
        double eps = 1e-13;
        double error = 1.0;
        Vector y;
        int iteration = 1; // number of iterations

        // continue the algorithm until |lambda-lambda_prev|/|lambda_prev| < eps
        do {
            lambda_prev = lambda; // collect the previous lambda
            y = A.multiply(x); // get the guess
            lambda = y.normLoo(); // get the new lambda
            x = y.divide(lambda); // get the next guess
            // finally, print the information and increment number of iterations
            System.out.println("Iteration: " + iteration + " lambda = " + lambda + " error = " + Math.abs(lambda - lambda_prev) / Math.abs(lambda_prev));
            iteration++;
        } while (Math.abs(lambda - lambda_prev) / Math.abs(lambda_prev) >= eps);

        // Check: Compute norm Residual norm(A*x-lambda*x)
        Vector r = A.multiply(x);
        for (int i = 0; i < A.getSize(); i++) r.set(i, r.get(i) - x.get(i) * lambda);
        System.out.println("\nResidual normLoo= " + r.normLoo() + " normL2= " + r.normL2());

    }
}
