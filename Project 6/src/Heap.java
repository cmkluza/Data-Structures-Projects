import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

class Heap<T extends Comparable<T>> {
    /* =============== VARIABLES =============== */
    ArrayList<T> nodes;

    /* =============== CONSTRUCTOR =============== */
    // default constructor - sets value of initial capacity to default of 20
    public Heap() {
        nodes = new ArrayList<>(20);
    }

    // takes an initial capacity
    public Heap(int initialCapacity) {
        nodes = new ArrayList<>(initialCapacity);
    }

    /* =============== PUBLIC METHODS =============== */
    // adds an element to this heap
    public void insert(T element) {
        // no duplicates
        if (nodes.contains(element)) return;
        // add the element to the end
        nodes.add(element);
        // trickle up the newly added element
        trickleUp(nodes.size() - 1);
    }

    // adds many elements to this heap
    public void insertAll(Collection<T> elements) {
        for (T element : elements) insert(element);
    }

    // adds many elements to this heap
    public void insertAll(Stream<T> elements) {
        elements.forEach(this::insert);
    }

    // removes and returns an element from the heap
    public T remove() {
        // record current root
        T root = nodes.get(0);
        // place last item in root's spot
        nodes.set(0, nodes.get(nodes.size() - 1));
        // delete the last item
        nodes.remove(nodes.size() - 1);
        // trickle down from root
        trickleDown(0);
        // return the previous root
        return root;
    }

    // allows traversal of this heap
    public void forEach(Consumer<T> action) {
        nodes.forEach(action);
    }

    // clears the heap
    public void clear() {
        nodes.clear();
    }

    // whether the heap is empty
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    // whether the heap contains an element already
    public boolean contains(T element) {
        // loop through each node in the heap
        for (T node : nodes) {
            // if any node equals the given element, it's contained
            if (node.compareTo(element) == 0) return true;
        }
        // otherwise not contained
        return false;
    }

    // getter for size
    public int size() {
//        return numElements;
        return nodes.size();
    }

    // toString
    @Override
    public String toString() {
        return "Heap{" +
                "nodes=" + nodes +
                '}';
    }

    /* =============== PRIVATE METHODS =============== */
    // gets the index of the parent element
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    // gets the index of the left child
    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    // gets the index of the right child
    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    // trickles an element up
    private void trickleUp(int index) {
        // record the parent node
        int parent = getParentIndex(index);
        // record current element
        T current = nodes.get(index);
        // move upwards until it's in the appropriate location or at the top
        while (index > 0 && (nodes.get(parent).compareTo(current) > 0)) {
            // move current element up
            nodes.set(index, nodes.get(parent));
            // get next parent index
            index = parent;
            parent = getParentIndex(index);
        }
        // place current element at appropriate location
        nodes.set(index, current);
    }

    // trickles an element down
    private void trickleDown(int index) {
        // do nothing if it's empty
        if (nodes.isEmpty()) return;
        T root = nodes.get(index); // store the root
        // loop until there's no more children
        while (index < nodes.size() / 2) {
            // indices of child elements
            int leftChild = getLeftChildIndex(index);
            int rightChild = getRightChildIndex(index);
            int smallerIndex; // index of smaller child

            // determine which child is larger
            if (getRightChildIndex(index) < nodes.size() && nodes.get(rightChild).compareTo(nodes.get(leftChild)) < 0) {
                // right child is smaller
                smallerIndex = getRightChildIndex(index);
            } else {
                // left child is smaller
                smallerIndex = getLeftChildIndex(index);
            }

            // if the root is smaller, we've found the appropriate location
            if (root.compareTo(nodes.get(smallerIndex)) < 0) break;

            // move smaller child up
            nodes.set(index, nodes.get(smallerIndex));
            // adjust index
            index = smallerIndex;
        }
        // move root to appropriate location
        nodes.set(index, root);
    }
}
