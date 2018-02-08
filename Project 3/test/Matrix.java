import java.text.DecimalFormat;
import java.util.*;


interface Matrix {
    // Assign the value x to element i,j
    void set(int i, int j, double x);

    // get the value of the element i,j
    double get(int i, int j);

    // Extract the diagonal of the matrix
    double[] getDiagonal();

    // get the size of the matrix-- number of rows
    int getSize();

    // get the number of non-zero elements
    int getNnz();

    // Multiply a matrix by a vector
    Vector multiply(Vector B);

    // Print matrix using a specific format
    void display();

    // return info about the matrix
    void info();
}


//////////////////////////////////// ARRAY DENSE MATRIX IMPLEMENTATION

class DenseMatrix implements Matrix {
    private int size = 0; // size of the matrix- number of rows/columns
    private int nnz = 0;  // number of non-zero elements
    private double[][] data;

    public DenseMatrix(int n) {
        data = new double[n][n];
        size = n;
    }

    // accepts a sparse matrix and makes a dense matrix from it
    DenseMatrix(Matrix matrix) {
        // since the typical constructor requires a specified value, this constructor ONLY works when the matrix
        // passed is a sparse constructor
        if (matrix instanceof SparseMatrixLinkedList) {
            // copy size
            size = matrix.getSize();
            data = new double[size][size];
            // copy all values over
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    set(i, j, matrix.get(i, j));
                }
            }
            // copy nnz
            nnz = matrix.getNnz();
        }
    }

    // accepts size and number of non-zero elements, then generates a random matrix
    DenseMatrix(int size, int nnz) {
        // cannot generate more random elements than there is room in the array
        if (Math.sqrt(nnz) >= size) return;
        // initializing data
        data = new double[size][size];
        this.size = size;

        // I'm just going to add random values in sequential order until I hit nnz
        for (int i = 0; i < nnz; i++) {
            int row = i / size;
            int col = i % size;
            data[row][col] = Math.random();
        }

        this.nnz = nnz;
    }

    // return info about the matrix
    public void info() {
        System.out.println("Dense Matrix n=" + size + ", nnz=" + nnz + ", Storage=" + (8 * size * size) + "b or " + (8 * size * size) / (1024 * 1024) + "Mb");
    }

    @Override
    public void set(int i, int j, double x) {
        // check for invalid indices
        if (i < 0 || j < 0 || i > size || j > size) return;
        // if the previous value was zero, increment nnz
        if (data[i][j] == 0) nnz++;
        data[i][j] = x;
    }

    @Override
    public double get(int i, int j) {
        // check for invalid indices
        if (i < 0 || j < 0 || i > size || j > size) return 0;
        return data[i][j];
    }

    @Override
    public double[] getDiagonal() {
        double[] diagonal = new double[size];
        for (int i = 0; i < size; i++) {
            diagonal[i] = data[i][i];
        }
        return diagonal;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getNnz() {
        return nnz;
    }

    @Override
    public Vector multiply(Vector B) {
        Vector vector = new VectorArray(size); // vector to store results in
        double sum = 0; // the sum of the values for a given j
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < B.getSize(); j++) {
                // getting the sum
                sum += data[i][j] * B.get(j);
            }
            // setting the value in the results vector
            vector.set(i, sum);
            // resetting sum for next loop
            sum = 0;
        }
        // return the resulting vector
        return vector;
    }

    @Override
    public void display() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat format = new DecimalFormat("###0.0000");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(format.format(data[i][j])).append("    ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}


//////////////////////////////////// Linked-List SPARSE MATRIX IMPLEMENTATION

class RowNode {
    public int rowindex;
    public ColNode col;
    public RowNode next;

    RowNode(int i) {
        rowindex = i;
        col = null;
        next = null;
    }
}

class ColNode {
    public double entry;
    public int colindex;
    public ColNode next;

    ColNode(int j, double x) {
        colindex = j;
        entry = x;
        next = null;
    }
}


class SparseMatrixLinkedList implements Matrix {
    private RowNode top;
    private int size = 0; // size of the matrix (I'm assuming size == number of rows == number of columns)
    private int nnz = 0; // number of non-zero elements


    // constructors
    // Basic constructor- no element in the list yet
    SparseMatrixLinkedList() {
        top = null;
    }

    // accepts a dense matrix as an argument and forms a SparseMatrixLinkedList from the non-zero elements
    SparseMatrixLinkedList(Matrix matrix) {
        // if this isn't actually a dense matrix, just set the top to null and do nothing
        if (!(matrix instanceof DenseMatrix)) {
            // this shouldn't ever happen
            System.err.println("Error - not a DenseMatrix");
            top = null;
        } else {
            // copy size
            size = matrix.getSize();
            // copy all non-zero values over
            double value;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    value = matrix.get(i, j);
                    if (value != 0) set(i, j, value);
                }
            }
            // copy nnz
            nnz = matrix.getNnz();
        }
    }

    // accepts size and number of non-zero elements
    SparseMatrixLinkedList(int size, int nnz) {
        // cannot generate more random elements than there is room in the array
        if (Math.sqrt(nnz) >= size) {
            System.out.println("[DEBUG-LL] returned");
            return;
        }

        // initializing data
        top = null;
        this.size = size;

        // I'm just going to add random values in sequential order until I hit nnz
        for (int i = 0; i < nnz; i++) {
            int row = i / size;
            int col = i % size;
            set(row, col, Math.random());
        }

        this.nnz = nnz;
    }

    // methods
    @Override
    public void set(int i, int j, double x) {
        // size can be assumed to be equal to the largest index in either direction (column or row), so if either
        // of these values is greater than the current size, the size will be updated to match it
        if (i + 1 > size) size = i + 1; // size will be one greater than the index
        if (j + 1> size) size = j + 1;

        // local variables: the RowNode and ColNode where the value will go
        RowNode row;
        ColNode col;

        // ============ SEARCHING FOR THE ROW ============

        if (top == null) {
            // if the top is null, this is the first value being set, create a row for it
            top = new RowNode(i); // create a new row with the necessary index
            row = top; // set the row that the column will be in
        } else {
            // top is not null; search through rows until we find the appropriate location OR reach the end of the LL
            row = top;
            while (row.next != null && row.next.rowindex <= i) {
                row = row.next;
            }

            // once here there are three possibilities:
            // 1.) the end was reached (i.e. row.next == null); insert a new row after the one currently stored,
            //      IF the row isn't at the desired index
            // 2.) the end was not reached AND the next row is past the position for this row; if this row index
            //      does not equal i, then we need to create a new row in its position and set the row to that
            // 3.) the correct index was found; just need to set row to this row

            if (row.next == null && row.rowindex != i) {
                // possibility 1
                row.next = new RowNode(i); // create a new row with the necessary index
                row = row.next; // set the row that the column will be in
            } else if (row.rowindex != i) {
                // possibility 2
                RowNode previous = row;
                RowNode next = row.next;
                RowNode newRow = new RowNode(i); // create a new row with the necessary index
                row = newRow; // set the row that the column will be in
                // set the order of the rows: previous -> newRow -> next
                previous.next = newRow;
                newRow.next = next;
            } else {
                // possibility 3
                // nothing needs to be done; the row variable is set at the correct row
            }
        }

        // ============ SEARCHING FOR THE COLUMN ============

        if (row.col == null) {
            // if the first column is null, this is the first value being set, create a column for it
            row.col = new ColNode(j, x); // create a new column with the necessary index and value
            // increment nnz
            nnz++;
        } else {
            // the first column is not null; search through columns until we find the appropriate location
            //      OR reach the end of the LL
            col = row.col;
            while (col.next != null && col.next.colindex <= j) col = col.next;

            // once here there are three possibilities:
            // 1.) the end was reached (i.e. col.next == null); insert a new column after the one currently stored
            // 2.) the end was not reached AND the next column is past the position for this column; if this column
            //      index does not equal j, then we need to create a new column in its position
            // 3.) the correct index was found; just need to set the value

            if (col.next == null) {
                // possibility 1
                col.next = new ColNode(j, x); // create a new column with the necessary index and data
                // increment nnz
                nnz++;
            } else if (col.colindex != j) {
                // possibility 2
                ColNode previous = col;
                ColNode next = col.next;
                ColNode newRow = new ColNode(j, x); // create a new column with the necessary index and data
                // increment nnz
                nnz++;
                // set the order of the columns: previous -> newRow -> next
                previous.next = newRow;
                newRow.next = next;
            } else {
                // possibility 3
                // only increment nnz if the current data is zero
                if (col.entry == 0) nnz++;
                col.entry = x; // set the data
            }
        }
    }

    // the value either exists or we return zero
    @Override
    public double get(int i, int j) {
        // check for invalid indices
        if (i < 0 || j < 0 || i > size || j > size) return 0;

        // local variables for finding appropriate row and column
        RowNode row = top;
        ColNode col;

        // iterate through rows until appropriate index is found or end is reached
        while (row != null && row.rowindex != i) row = row.next;

        // the row is either at the correct index, or is null; return zero if null
        if (row == null) return 0;

        col = row.col; // setting first column in this row

        // iterate through columns until appropriate index is found or end is reached
        while (col != null && col.colindex != j) col = col.next;

        // the column is either at the correct index, or is null; return zero if null
        if (col == null) return 0;

        return col.entry;
    }

    @Override
    public double[] getDiagonal() {
        double[] diagonal = new double[size];
        for (int i = 0; i < size; i++) {
            diagonal[i] = get(i, i);
        }
        return diagonal;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getNnz() {
        return nnz;
    }

    @Override
    public Vector multiply(Vector B) {
        Vector vector = new VectorArray(size); // vector to store results in
        RowNode row = top; // start from the beginning

        // loop through each row
        while(row != null) {
            ColNode col = row.col;
            double sum = 0;
            // loop through value in each column, multiplying it by the corresponding value in vector B and adding
            // it to the sum
            while (col != null) {
                sum += col.entry * B.get(col.colindex);
                col = col.next;
            }
            // add the sum to the appropriate location on the result vector
            vector.set(row.rowindex, sum);
            row = row.next;
        }
        return vector;
    }

    public void display() {
        RowNode current = top; //start probe at the beginning
        System.out.println("i");
        while (current != null) { // until the end of the list
            System.out.print(current.rowindex + " ");
            ColNode jcurrent = current.col;
            while (jcurrent != null) { // until the end of the list
                System.out.format("==>  (j=%d, a=%.4f)", jcurrent.colindex, jcurrent.entry);
                jcurrent = jcurrent.next;
            }
            System.out.println();
            current = current.next; // move to next Link
        }
        System.out.println();
    }

    // return info about the matrix
    public void info() {
        System.out.println("Sparse Matrix n=" + size + ", nnz=" + nnz + ", Storage=" + (8 * nnz) + "b or " + (8 * nnz) / (1024 * 1024) + "Mb");
    }
}

