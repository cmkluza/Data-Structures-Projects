import java.io.*;
import java.util.*;

class CollectionBST {
    /* ==================== VARIABLES ==================== */
    private DictionaryBST[] dictionaries;
    private int maxSizeWord; // number of letters in the max size word
    private int steps; // number of steps for a search

    /* ==================== CONSTRUCTOR ==================== */
    public CollectionBST(DictionaryBST dictionary) {
        maxSizeWord = dictionary.getMaxSizeWord().getNumLetters();
        // make enough dictionaries to hold up to the max size word
        dictionaries = new DictionaryBST[dictionary.getMaxSizeWord().getNumLetters()];
        // initialize each dictionary
        for (int i = 0; i < maxSizeWord; i++) {
            dictionaries[i] = new DictionaryBST();
            ArrayWord array = dictionary.extractSubArrayInOrder(i + 1);
            dictionaries[i].initDictionary(array);
        }
    }

    /* ==================== PUBLIC METHODS ==================== */
    // prints info about the collection
    public void info() {
        System.out.println("--------------------------------------------------");
        System.out.println("The collection contains 23 BST dictionaries" +
                "\ndict --> size --> nblevels");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dictionaries.length; i++) {
            sb.append(i + 1).append(" --> ")
                    .append(dictionaries[i].getSize()).append(" --> ")
                    .append(dictionaries[i].getBstLevels()).append("\n");
        }
        System.out.println(sb.toString());
        System.out.println("--------------------------------------------------");
    }

    // searches the appropriate dictionary for the given word
    public boolean search(String word) {
        // reset the step counter
        steps = 0;
        // call the search method in the appropriate dictionary
        DictionaryBST dictionary = dictionaries[word.length() - 1];
        boolean result = dictionary.search(word); // perform the search, store result
        steps = dictionary.getStep(); // update number of steps
        return result; // return the result
    }

    // getter for steps
    public int getStep() {
        return steps;
    }

    /* ==================== PRIVATE METHODS ==================== */

}