interface Vector {
    // randomly fill all entries with number 0<=x<1
    void fill();

    // Assign the value x to element i
    void set(int i, double x);

    // Get the value of the element i
    double get(int i);

    // Get the size of the vector (number of rows)
    int getSize();

    // Print vector using a specific format
    void display();

    // Calculate the Loo norm of a vector // norm=max(|x_i|) (x_i component i of x)
    double normLoo();

    // Calculate the L2 norm of a vector (Euclidean norm=sqrt(sum(x_i^2)))
    double normL2();

    // Divide the vector by a scalar value; helper method for App8
    Vector divide(double x);
}


//////////////////////// USING ARRAY IMPLEMENTATION

class VectorArray implements Vector {
    private double[] array; // the array containing all the rows
    private int maxSize; // the fixed maximum number of rows/size of the array
    private int nRows; // the number of rows in the vector

    public VectorArray(int n) {
        array = new double[n];
        maxSize = n;
        nRows = 0;
    }

    @Override
    public void fill() {
        for (int i = 0; i < maxSize; i++) {
            array[i] = Math.random();
        }
        // array is filled, so nRows is the same as maxSize
        nRows = maxSize;
    }

    @Override
    public void set(int i, double x) {
        // only increment number of rows if the current value isn't 0 (i.e. only increment if we're adding a new value,
        // not over-writing an existing value)
        if (array[i] == 0) nRows++;
        array[i] = x;
    }

    @Override
    public double get(int i) {
        return array[i];
    }

    @Override
    public int getSize() {
        return nRows;
    }

    @Override
    public void display() {
        for (int i = 0; i < maxSize; i++) {
            System.out.println(array[i]);
        }
    }

    @Override
    public double normLoo() {
        double max = Math.abs(array[0]); // set first element to max
        // loop through each element
        for (int i = 1; i < nRows; i++) {
            // if a greater value is found, set that as the max
            if (Math.abs(array[i]) > max)
                max = Math.abs(array[i]);
        }
        // return the max absolute value of the vector
        return max;
    }

    @Override
    public double normL2() {
        double sum = 0; // the sum of squared elements
        // loop through each element, squaring it and adding it to the sum
        for (int i = 0; i < nRows; i++) {
            sum += array[i] * array[i];
        }
        // return the square root of the sum
        return Math.sqrt(sum);
    }

    @Override
    public Vector divide(double x) {
        Vector vector = new VectorArray(maxSize);
        for (int i = 0; i < maxSize; i++) {
            vector.set(i, this.array[i] / x);
        }
        return vector;
    }
}


//////////////////////// USING Linked-List IMPLEMENTATION


class VecNode {
    public int index;
    public double entry;
    public VecNode next;

    VecNode(int i, double x) {
        index = i;
        entry = x;
        next = null;
    }
}


class VectorLL implements Vector {
    private VecNode first; // the first node in this vector
    private int maxSize; // fixed maximum number of rows in this vector
    private int nRows; // number of rows in the vector

    public VectorLL(int n) {
        nRows = 0;
        maxSize = n;
        // instantiate first with default value of zero
        first = new VecNode(0, 0);
        VecNode current = first;
        // instantiate any remaining rows with default value of zero
        for (int i = 1; i < n; i++) {
            current.next = new VecNode(i, 0); // add the row
            current = current.next; // move to that row
        }
    }

    // ignores any current values
    @Override
    public void fill() {
        // iterate through each row setting a random value
        VecNode current = first;
        while (current != null) {
            current.entry = Math.random(); // give it a random value
            current = current.next; // move to the next value
        }
        // once this is done, nRows will equal maxSize
        nRows = maxSize;
    }

    @Override
    public void set(int i, double x) {
        // make sure index is valid
        if (i < 0 || i >= maxSize) {
            return;
        }
        // find the appropriate row
        VecNode row = first; // start at first
        while (row.next != null && row.index != i) {
            row = row.next;
        }
        // if somehow the index doesn't match something's gone horribly wrong
        if (row.index != i) {
            System.err.println("this should never happen");
        } else {
            // we've reached the appropriate index
            // only increment number of rows if we're entering a new row (i.e. current value is zero)
            if (row.entry == 0.0d) nRows++;
            row.entry = x;
        }
    }

    @Override
    public double get(int i) {
        // iterate through nodes until proper index is found
        VecNode current = first;
        while(current != null && current.next != null && current.index != i) {
            current = current.next;
        }
        // if the first row was null, return 0
        if (current == null) return 0;
        // check to see if we reached the end without finding the given index
        if (current.next == null && current.index != i) return 0;
        // we're at the appropriate node, return the value
        return current.entry;
    }

    @Override
    public int getSize() {
        return nRows;
    }

    @Override
    public void display() {
        for (int i = 0; i < nRows; i++) {
            System.out.println(get(i));
        }
    }

    @Override
    public double normLoo() {
        double max = Math.abs(get(0)); // set first element to max
        double num; // the value at the current node
        // loop through each element
        for (int i = 1; i < nRows; i++) {
            num = get(i);
            // if a greater value is found, set that as the max
            if (Math.abs(num) > max)
                max = Math.abs(num);
        }
        // return the max absolute value of the vector
        return max;
    }

    @Override
    public double normL2() {
        double sum = 0; // the sum of squared elements
        double num; // the value at the current node
        // loop through each element, squaring it and adding it to the sum
        for (int i = 0; i < nRows; i++) {
            num = get(i);
            sum += num * num;
        }
        // return the square root of the sum
        return Math.sqrt(sum);
    }

    @Override
    public Vector divide(double x) {
        Vector vector = new VectorLL(maxSize);
        for (int i = 0; i < maxSize; i++) {
            vector.set(i, this.get(i) / x);
        }
        return vector;
    }
}




