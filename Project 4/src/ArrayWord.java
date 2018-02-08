import java.awt.*;
import java.io.*;
import java.util.*; 


///////////////////////////////////////////////////////
class ArrayWord{
    /* ==================== VARIABLES ==================== */
    private NodeWord[] array; // the array containing words in-order
    private int size = 0; // number of elements in the array
    private Random rand = new Random();
    private int bstLevels = 0; // number of bst levels

    /* ==================== CONSTRUCTOR ==================== */
    public ArrayWord(int size) {
        array = new NodeWord[size];
    }

    /* ==================== PUBLIC METHODS ==================== */
    // inserts a word into the array, in-order
    public void insert(NodeWord node) {
        // if there's not enough room, enlarge
        if (size + 1 >= array.length) {
            enlarge();
        }
        // if this is the first element, just put it at the root
        if (array[0] == null) {
            array[0] = node;
            size++;
            return;
        }
        int i = size - 1; // start from the end
        // shift items to the right until the appropriate location to insert is found
        while (i >= 0 && array[i].compareTo(node) > 0) {
            array[i + 1] = array[i];
            i--;
        }
        array[i + 1] = node;
        size++;
    }

    // displays the array
    public void display() {
        System.out.println("\nArray extraction using in-order traversal");
        for (int i = 0; i < size; i++) {
            System.out.println(array[i]);
        }
    }

    // gets a random word from the array
    public Word getRandomWord() {
        return array[rand.nextInt(size)].data;
    }

    // randomizes the data
    public void shuffle() {
        // go through each index and swap with a random other element
        for (int i = 0; i < size; i++) {
            NodeWord temp = array[i]; // store value at this location
            int newIndex = rand.nextInt(size); // get index for new location
            // swap
            array[i] = array[newIndex];
            array[newIndex] = temp;
        }
    }

    // deletes and returns the last word
    public NodeWord deleteLast() {
        NodeWord word = array[size - 1]; // node to be returned
        array[size - 1] = null; // "deleting" the node
        size--; // decrement size
        return word;
    }

    // tells whether this is empty
    public boolean isEmpty() {
        return size < 1;
    }

    // sets the given index with a word
    public void set(int index, NodeWord word) {
        // if setting a value at an index not included in the array, enlarge until the array can fit it
        while (index > array.length) {
            enlarge();
        }
        // set the value
        array[index] = word;
        // update the number of bst levels if necessary
        if (word.bstLevel > bstLevels) {
            bstLevels = word.bstLevel;
        }
    }

    // displays all non-null elements
    public void displayAll() {
        for (NodeWord node : array) {
            if (node != null) {
                System.out.println(node);
            }
        }
    }

    // plots the binary search tree
    public void plotBST(int width, int height) {
        StdDraw.setCanvasSize(width, height); // set canvas size
        StdDraw.setXscale(0, 1200);
        StdDraw.setYscale(-400, 400);
        // constants
        double r = 5;
        // variables used for placing items
        double x; // start in middle of canvas
        double y; // start at top
        double dist; // distance between elements in the same level
        // previous coordinates for drawing lines
        double prevX = 0;
        double prevY = 0;
        // go through each element in the array
        for (int i = 0; i < array.length; i++) {
            // setup variables for this element
            int bstLevel = (int) (Math.log(i + 1) / Math.log(2)); // the bst level of the current node
            int numElements = (int) Math.pow(2, bstLevel); // number of elements in the current level
            int position = (i + 1) % numElements; // position within the bstLevel

            // calculating position
            dist = 1100 / (numElements + 1);
            x = 50 + (position + 1) * dist;
            y = height - ((double) height * (bstLevel + 1) / (double) bstLevels);

            if (array[i] != null) {
                // non-null; blue
                StdDraw.setPenColor(Color.BLUE);
                // draw the circle
                StdDraw.filledCircle(x, y, r);
            } else {
                // null; grey
                StdDraw.setPenColor(Color.GRAY);
                // draw the circle
                StdDraw.filledCircle(x, y, r);
            }

            // calculate position of parent index
            int parentIndex = (i - 1) / 2; // getting index
            bstLevel = (int) (Math.log(parentIndex + 1) / Math.log(2)); // the bst level of parent
            numElements = (int) Math.pow(2, bstLevel); // number of elements in parent level
            position = (parentIndex + 1) % numElements; // position within parent level

            // calculating parent position
            dist = 1100 / (numElements + 1);
            prevX = 50 + (position + 1) * dist;
            prevY = height - (double) height * ((bstLevel + 1) / (double) bstLevels);

            // drawing line between parent and child
            StdDraw.line(x, y, prevX, prevY);
        }
    }

    /* ==================== PRIVATE METHODS ==================== */
    // doubles the size of the array
    private void enlarge() {
        NodeWord[] temp = new NodeWord[array.length * 2];
        System.arraycopy(array, 0, temp, 0, array.length);
        array = temp;
    }
}

