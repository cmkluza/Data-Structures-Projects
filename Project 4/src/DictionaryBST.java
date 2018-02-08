import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class DictionaryBST {
    /* ==================== VARIABLES ==================== */
    private NodeWord root; // the root of the tree
    private int bstLevels; // levels in the binary search tree
    private int size; // size (number of elements) of the BST
    private int steps; // the number of steps for the most recent search
    private Word maxSizeWord = null; // the max size word in the dictionary; only the first word of max size will be stored

    /* ==================== CONSTRUCTOR ==================== */
    public DictionaryBST() {
        bstLevels = 0;
        size = 0;
    }

    /* ==================== PUBLIC METHODS ==================== */
    // inserts a NodeWord into the BST
    public void insert(NodeWord node) {
        if (root == null) {
            // this is the first item, set as root
            root = node;
            // update node variables
            root.index = 0; // root is always zero
            root.bstLevel = 0; // root is considered level zero
            // update tree variables
            bstLevels = 0; // root is considered level zero
            size = 1; // only one item
            // update max sized word
            maxSizeWord = node.data;
        } else {
            // not the first node, search for the appropriate location
            NodeWord current = root; // start from the root
            NodeWord parent = root; // keep track of parent node
            int level = 0; // keep track of what level the current node is at
            // continue until we reach a null node
            while (current != null) {
                parent = current; // update parent node
                if (node.compareTo(current) < 0) {
                    // this node is less than the current, move to the left
                    current = current.left;
                } else {
                    //  this node is greater than or equal to current, move to the right
                    current = current.right;
                }
                // increment level every time we go another level into the tree
                level++;
            }
            // current node is where to insert
            current = node;
            // if the level is greater than the current number of levels, update current number of levels
            if (level > bstLevels) {
                bstLevels = level;
            }
            // update parent's reference and set node variables
            if (node.compareTo(parent) < 0) {
                parent.left = current;
                current.index = leftIndex(parent);
            } else {
                parent.right = current;
                current.index = rightIndex(parent);
            }
            current.bstLevel = level;
            // update max sized word if necessary
            if (current.data.getNumLetters() > maxSizeWord.getNumLetters()) {
                maxSizeWord = current.data;
            }
            // increment size every time you add an item
            size++;
        }
    }

    // prints basic info about the BST
    public void info() {
        System.out.println("The size of the dictionary is " + size + " using " + bstLevels + " BST levels");
    }

    // loads the contents of a dictionary into this BST
    public void loadDictionary(String name) {
        // do nothing if the file name is invalid
        if (!Files.exists(Paths.get(name + ".txt"))) {
            System.err.println("No dictionary named " + name + ".txt was found!");
            return;
        }
        // file name is valid, load the words
        try {
            Files.lines(Paths.get(name + ".txt")).forEach(word -> insert(new NodeWord(new Word(word, word.length(), name))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // displays each value in this BST in order
    public void displayInOrder() {
        System.out.println("\nDisplay in-order " + size + " words");
        // recursively display starting from the root
        displayInOrder(root);
    }

    // "shows" the tree
    public void show(String type) {
        switch (type.toLowerCase()) {
            case "word":
                // display the tree using the word
                System.out.println("\nThe tree using (word) looks like:");
                show(root, "word");
                break;
            case "id":
                // display the tree using the id
                System.out.println("\nThe tree using (id) looks like:");
                show(root, "id");
                break;
            case "index":
                // display the tree using the index
                System.out.println("\nThe tree using (index) looks like:");
                show(root, "index");
                break;
        }
    }

    // getter for size
    public int getSize() {
        return size;
    }

    // extracts an array of words in-order
    public ArrayWord extractArrayInOrder() {
        ArrayWord array = new ArrayWord(size); // the array to store everything in
        // recursive calls to add each word in-order
        extractArrayInOrder(array, root);
        // returning the array
        return array;
    }

    // searches for a word
    public boolean search(String word) {
        // reset number of steps
        steps = 0;
        // start from the root
        NodeWord current = root;
        // continue searching until we find the word or reach a null node
        while (current != null) {
            if (word.compareToIgnoreCase(current.data.getWord()) > 0) {
                // word is greater than current node; to the right
                steps++;
                current = current.right;
            } else if (word.compareToIgnoreCase(current.data.getWord()) < 0) {
                // word is less than current node; to the left
                steps++;
                current = current.left;
            } else {
                // word is equal to current node
                steps++;
                return true;
            }
        }
        // if here, didn't find it, return false
        return false;
    }

    // getter for steps
    public int getStep() {
        return steps;
    }

    // initialize the dictionary from an ArrayWord object - clears current contents in the BST
    public void initDictionary(ArrayWord array) {
        root = null; // "clear" the current contents fo the BST
        array.shuffle(); // randomize the data
        // insert nodes until the ArrayWord is empty
        while (!array.isEmpty()) {
            insert(new NodeWord(array.deleteLast().data));
        }
    }

    // getter for max sized word
    public Word getMaxSizeWord() {
        return maxSizeWord;
    }

    // returns a sub-array in-order containing only words of the given size
    public ArrayWord extractSubArrayInOrder(int length) {
        ArrayWord array = new ArrayWord(size); // over-estimate size to reduce needed enlarging of the array
        // recursive calls to add each word in-order
        extractSubArrayInOrder(length, array, root);
        // return the array
        return array;
    }

    // getter for bst levels
    public int getBstLevels() {
        return bstLevels;
    }

    // creates a dictionary BST with anagrams for the given word
    public DictionaryBST createBSTAnagram(String word) {
        // creating and initializing the dictionary with only words of the same length
        DictionaryBST dict = new DictionaryBST();
        dict.initDictionary(this.extractSubArrayInOrder(word.length()));

        // sorting the characters of the input word
        String sortedWord;
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        sortedWord = String.valueOf(chars);

        // ArrayWord to store all anagrams
        ArrayWord anagrams = new ArrayWord(dict.size);

        // get an array version of the dictionary that's easier to work with
        ArrayWord array = dict.extractArrayInOrder();

        // go through each value in array, sorting characters and comparing to sortedWord
        while(!array.isEmpty()) {
            NodeWord node = array.deleteLast();
            chars = node.data.getWord().toCharArray();
            Arrays.sort(chars);
            String sorted = String.valueOf(chars);
            if (sortedWord.equalsIgnoreCase(sorted)) {
                anagrams.insert(new NodeWord(node.data));
            }
        }

        // create a tree from the ArrayWord
        DictionaryBST anagramBST = new DictionaryBST();
        anagramBST.initDictionary(anagrams);
        return anagramBST;
    }

    // spell checks a given text file
    public void spellCheckFile(String fileName) {
        // give an error if the file isn't found
        if (!Files.exists(Paths.get(fileName))) {
            System.err.println("No file named " + fileName + " was found!");
            return;
        }

        System.out.println(); // newline

        // load the file and perform spell checking
        try {
            Files.lines(Paths.get(fileName)).forEach(line -> {
                // only process non-empty lines
                if (!line.isEmpty()) {
                    // split line by spaces
                    String[] args = line.split(" ");
                    String[] dictIds = new String[args.length]; // these will correspond directly to the dictionary that contains a given word
                    // process each word
                    for (int i = 0; i < args.length; i++) {
                        String dictId = getDictId(args[i].replaceAll("\\p{Punct}|\\d", ""));
                        if (dictId.equalsIgnoreCase("no ID")) {
                            // word wasn't found, surround with parentheses
                            args[i] = "(" + args[i] + ")";
                        }
                        // set the dictionary id for this word
                        dictIds[i] = dictId;
                    }
                    // two StringBuilders; one to re-build the original line (with spelling errors now marked)
                    // and one for the dictionary IDs
                    StringBuilder lineBuilder = new StringBuilder();
                    StringBuilder dictIdBuilder = new StringBuilder("[");
                    for (int i = 0; i < args.length; i++) {
                        lineBuilder.append(args[i]).append(" ");
                        dictIdBuilder.append(dictIds[i]).append("-");
                    }
                    dictIdBuilder.append("]");
                    System.out.println(lineBuilder);
                    System.out.println(dictIdBuilder);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // converts this to an ArrayWord object
    public ArrayWord convertToArrayInOrder() {
        ArrayWord array = new ArrayWord(size); // start out with number of elements; array will expand itself if necessary
        // recursively add each element in this to the array at the appropriate position
        convertToArrayInOrder(array, root);
        // return the array
        return array;
    }

    /* ==================== PRIVATE METHODS ==================== */
    // gets the index for the left child
    private int leftIndex(NodeWord node) {
        return 2 * node.index + 1;
    }

    // gets the index for the right child
    private int rightIndex(NodeWord node) {
        return 2 * node.index + 2;
    }

    // gets the index of the parent
    private int parentIndex(NodeWord node) {
        return (node.index - 1) / 2;
    }

    // recursive method utilized by #displayInOrder
    private void displayInOrder(NodeWord node) {
        // base case is a null node
        if (node == null) return;
        // go through the left subtree
        if (node.left != null)
        displayInOrder(node.left);
        // display the value
        System.out.println(node.data);
        // go through the right subtree
        if (node.right != null)
        displayInOrder(node.right);
    }

    // recursive utility method to "show" the tree
    private void show(NodeWord node, String type) {
        // base case - return if node is null
        if (node == null) return;
        // call on left subtree
        show(node.right, type);

        // print the data
        StringBuilder sb = new StringBuilder();
        // get the correct spacing
        for (int i = 0; i < node.bstLevel; i++) {
            sb.append("\t");
        }
        // append the correct data and print
        if (type.equalsIgnoreCase("word")) sb.append(node.data.getWord());
        else if (type.equalsIgnoreCase("id")) sb.append(node.data.getDictionary());
        else if (type.equalsIgnoreCase("index")) sb.append(node.index);
        // print the data
        System.out.println(sb.toString());

        // call on right subtree
        show(node.left, type);
    }

    // recursive utility to extract an ArrayWord
    private void extractArrayInOrder(ArrayWord array, NodeWord node) {
        // base case on a null node
        if (node == null) {
            return;
        }
        // call on the left child
        extractArrayInOrder(array, node.left);
        // add the node to the array
        array.insert(node);
        // call on the right child
        extractArrayInOrder(array, node.right);
    }

    // recursive utility to extract a sub-array
    private void extractSubArrayInOrder(int length, ArrayWord array, NodeWord node) {
        // base case on a null node
        if (node == null) {
            return;
        }
        // call on left child
        extractSubArrayInOrder(length, array, node.left);
        // add the node to the array if it's of the correct length
        if (node.data.getNumLetters() == length) {
            array.insert(node);
        }
        // call on right child
        extractSubArrayInOrder(length, array, node.right);
    }

    // adaptation of search method that returns the dictionary ID of a word
    private String getDictId(String word) {
        // start from the root
        NodeWord current = root;
        // continue searching until we find the word or reach a null node
        while (current != null) {
            if (word.compareToIgnoreCase(current.data.getWord()) > 0) {
                // word is greater than current node; to the right
                current = current.right;
            } else if (word.compareToIgnoreCase(current.data.getWord()) < 0) {
                // word is less than current node; to the left
                current = current.left;
            } else {
                // word is equal to current node
                return current.data.getDictionary();
            }
        }
        // if here, didn't find it, return false
        return "no ID";
    }

    // recursive utility to convert to an array
    private void convertToArrayInOrder(ArrayWord array, NodeWord node) {
        // base case when node is null
        if (node == null) {
            return;
        }
        // call on left child
        convertToArrayInOrder(array, node.left);
        // set in array
        array.set(node.index, node);
        // call on right child
        convertToArrayInOrder(array, node.right);
    }

}














