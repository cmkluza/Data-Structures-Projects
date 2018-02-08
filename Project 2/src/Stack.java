import java.util.Arrays;

class Stack {

    //variables
    private int maxSize; // Stack capacity initialized by user
    private int top;     // top of stack
    private Object[] array; // array that holds the items

    // constructors
    public Stack(int maxSize) {
        this.maxSize = maxSize;    // set array size
        array = new Object[maxSize];// create array
        top = -1;                // no item yet
    }

    ////// Traditional methods
    public boolean isEmpty() {
        return (top == -1);//true if stack is empty
    }


    public boolean isFull() {
        return (top == maxSize - 1);//true if stack is full
    }


    public int size() {
        return (top + 1);// return current number of items
    }


    public Object peek() {
        if (!isEmpty())
            return array[top];     //object at the top
        else
            throw new IllegalStateException("Stack is empty");
    }

    public Object pop() {
        if (!isEmpty()) {
            top--;                 //decrement top
            return (array[top + 1]);
        } else
            throw new IllegalStateException("Stack is empty");
    }

    public void push(Object item) {
        if (!isFull()) {
            top++;             //increment top
            array[top] = item;  //insert item
        }
    }

    ////////////////////////
    ////// Additional methods
    /////////////////////////

    /// swap the item on top and top-1
    public void swap() {
        Object temp = array[top];
        array[top] = array[top - 1];
        array[top - 1] = temp;
    }

    //// duplicate the top item   
    public void copy() {
        // can only do this if stack isn't full
        if (!isFull()) {
            array[top + 1] = array[top];
            top++;
        } else {
            System.out.println("Stack is full!");
        }
    }

    //// display the stack
    public void display() {
        // only display if not empty
        if (!isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= top; i++) {
                sb.append(i).append("\t").append(array[i]).append("\n");
            }
            System.out.println(sb);
        }
    }


    //// flush (empty) the stack
    public void flush() {
        Arrays.fill(array, null);
        top = -1;
    }


    //// delete top item of the stack
    public void del() {
        array[top] = null;
        top--;
    }

}
////////////////////////////////////
