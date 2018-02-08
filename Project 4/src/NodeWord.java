

///////////////////////////////////////////////////////

class NodeWord { // Define node for BST

    // left and right references only needed for Binary Tree linked-list
    public NodeWord left; // left child
    public NodeWord right;// right child
    public int index;  // CBT index
    public int bstLevel; // tree level
    public Word data; // the object

    // Constructor: construct the NodeWord class with the Word object 
    public NodeWord(Word data) {
        left = null;
        right = null;
        this.data = data;
    }

    // makes insertion easier
    public int compareTo(NodeWord node) {
        return this.data.getWord().compareToIgnoreCase(node.data.getWord());
    }

    // for debugging
    @Override
    public String toString() {
        return data.toString();
    }
}
