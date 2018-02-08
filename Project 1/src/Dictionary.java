import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

class Dictionary {

    /////////////////////////////////////
    ///////////// Private Variables//////
    /////////////////////////////////////

    // private variables related to the dictionary
    private String name; // name of the dictionary
    private int nbwords = 0;  // number of words in the array
    private String[] words; // array of words
    private int maxsize; // max fixed size of the array

    private boolean sorted = false; // flag for sorted/unsorted

    // private variables related to the search for the dictionary
    private int searchIndex = 0;
    private int searchStepCounter = 0;

    /////////////////////////////////////
    ///////////// Constructors///////////
    /////////////////////////////////////

    public Dictionary(int maxsize) {
        this.maxsize = maxsize;
        words = new String[maxsize];
    }

    ////////////////////////////////////////////////////////////
    ///////////// Methods //////////////////////////////////////
    ////////////////////////////////////////////////////////////

    /* =============== GETTERS AND SETTERS =============== */

    // getter for name - DictApp1
    public String getName() {
        return name;
    }

    // setter for name
    public void setName(String name) {
        this.name = name;
    }

    // setter for sorted - DictApp2
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    // getter for sorted - DictApp2
    public boolean isSorted() {
        return sorted;
    }

    // getter for searchStepCounter - DictApp2
    public int getStep() {
        return searchStepCounter;
    }

    // getter for size - DictApp3
    public int getSize() {
        return nbwords;
    }

    /* =============== INPUT/OUTPUT =============== */

    // loads words from a path into a dictionary - DictApp1
    public void loadDictionary(String fileName) {
        this.name = fileName;
        File file = new File(fileName);
        // if the file doesn't exist, print an error and stop the method
        if (!file.exists()) {
            System.out.println("Error in Dictionary#loadDictionary() - File not found!");
            System.exit(1);
        }

        // getting number of words and the words array
        try (Stream<String> countStream = Files.lines(Paths.get(file.getPath())); // stream to get number of words
             Stream<String> wordStream = Files.lines(Paths.get(file.getPath())) // stream to get words into the array
        ) {
            nbwords = (int) countStream.count(); // overall number of words in the file
            words = wordStream.limit(maxsize).toArray(ignored -> new String[maxsize]); // loading words into an array
            // if the maxsize is less than the number of words, then the number of words is limited to that
            if (nbwords > maxsize) {
                nbwords = maxsize;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in Dictionary#loadDictionary - could not generate streams from files!");
            System.exit(1);
        }
    }

    

    // saves the dictionary in a file - DictApp1
    public void saveDictionary(String extension) {
        String fileName = this.name + extension;

        File file = new File(fileName); // the file
        // if the file doesn't exist, create it
        if (!file.exists()) {
            // if the file isn't created successfully, stop the command and print an error
            try {
                if (!file.createNewFile()) {
                    System.out.println("Error in Dictioanry#saveDictionary() - the file wasn't created successfully!");
                }
            } catch (IOException e) {
                System.out.println("Error in Dictionary#saveDictionary - new file could not be created!");
                e.printStackTrace();
                System.exit(1);
            }
        }

        // write each word to the file
        try (FileWriter writer = new FileWriter(file)) {
            for (String word : words) {
                if (word != null) {
                    writer.write(word);
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error in Dictionary#saveDictionary - couldn't create FileWriter!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /* =============== SORTING METHODS =============== */

    // sort with bubble sort - DictApp1
    public void sortBubble() {
        int swapNumber; // the number of swaps in a given pass
        int compare; // integer representing comparison of strings
        String first, second; // the two strings being compared at any given time

        // continue sorting algorithm until there are no swaps
        do {
            // reset swap number
            swapNumber = 0;
            // loop through each index to compare words
            for (int i = 0; i < nbwords - 1; i++) {
                first = words[i]; // getting the first word
                second = words[i + 1]; // getting the second word
                compare = first.compareTo(second); // the comparison integer
                // swap if needed
                if (compare > 0) {
                    // first was greater than second; swap
                    words[i] = second;
                    words[i + 1] = first;
                    swapNumber++;
                }
            }
        } while (swapNumber != 0);

        // set sort
        sorted = true;
    }

    // sort with selection sort - DictApp1
    public void sortSelection() {
        int unsortedIndex = 0; // the index of the "left-most" unsorted element
        int minIndex; // the index of the minimum value
        String min; // the minimum value of unsorted elements
        String temp; // temp word storage while swapping

        // while the sorted index is less than the index of the last word, keep sorting
        while (unsortedIndex < nbwords) {
            min = words[unsortedIndex]; // set min to first unsorted element
            minIndex = unsortedIndex;
            // loop through each element, store overall minimum element
            for (int i = unsortedIndex; i < nbwords; i++) {
                if (min.compareTo(words[i]) > 0) {
                    min = words[i];
                    minIndex = i;
                }
            }
            // only need to swap if min isn't already the left-most value
            if (!min.equals(words[unsortedIndex])) {
                // swap min and current left-most value
                temp = words[unsortedIndex];
                words[unsortedIndex] = min;
                words[minIndex] = temp;
            }

            // increment unsortedIndex
            unsortedIndex++;
        }

        // set sort
        sorted = true;
    }

    // sort with insertion sort - DictApp1
    public void sortInsertion() {
        int unsortedIndex = 1; // index of left-most unsorted element; starts at 1 because the first element is always "partially sorted"
        int storeIndex; // the index where temp will be stored
        String comparedWord; // temporary storage for the string being compared
        String temp; // temporary storage for words being swapped

        // while the sorted index is less than the index of the last word, keep sorting
        while (unsortedIndex < nbwords) {
            comparedWord = words[unsortedIndex]; // get string being compared
            storeIndex = unsortedIndex;
            // loop through elements before the unsorted element, store index where the word goes
            for (int i = unsortedIndex; i >= 0; i--) {
                // if temp is less than the current word, the store index becomes the index of that word
                if (comparedWord.compareTo(words[i]) < 0) {
                    storeIndex = i;
                }
            }
            // only need to refactor if storeIndex isn't equal to unsortedIndex
            if (storeIndex != unsortedIndex) {
                // go down through each element from the unsorted index, moving elements to the right
                for (int i = unsortedIndex; i > storeIndex; i--) {
                    temp = words[i - 1]; // store word
                    words[i] = temp; // move word to the right
                }
                // store comparedWord in now sorted position
                words[storeIndex] = comparedWord;
            }
            // increment unsortedIndex
            unsortedIndex++;
        }

        // set sort
        sorted = true;
    }

    // sort with enhanced insertion sort - DictApp1
    public void sortEnhancedInsertion() {
        System.out.println("Not implemented");
    }

    /* =============== SEARCHING METHODS =============== */

    // linear search - DictApp2
    public boolean linearSearch(String query) {
        // reset global variables
        searchStepCounter = 0;
        searchIndex = 0;
        // loops through every term in the words array
        for (String word : words) {
            // ignore null values
            if (word == null) continue;
            // increment number of steps necessary to find value
            searchStepCounter++;
            // returns true if it finds a match
            if (word.equals(query)) {
                // sets the index of the located word; index will be 1 less than number of search steps
                searchIndex = searchStepCounter - 1;
                return true;
            }
        }
        // returns false if there is no match found
        return false;
    }

    // binary search - DictApp2
    public boolean binarySearch(String query) {
        // cannot perform binary search on an unsorted dictionary
        if (!sorted) {
            System.out.println("Cannot perform binary search on an unsorted dictionary!");
            return false;
        }

        // reset global variables
        searchStepCounter = 0;
        searchIndex = 0;

        int max, min, mid, compare;
        max = nbwords - 1; // max index
        min = 0; // min index
        mid = (max + min) / 2; // middle index

        // search until difference between max and min is 1
        while (max > min + 1) {
            searchStepCounter++;
            compare = words[mid].compareTo(query);
            if (compare == 0) {
                // if zero, then there's a match
                searchIndex = mid;
                return true;
            } else if (compare > 0) {
                // if greater than zero, query is less than word, move to lower half
                max = mid;
                mid = (max + min) / 2;
                searchIndex = mid;
            } else {
                // if less than zero, query is less than word, move to higher half
                min = mid;
                mid = (max + min) / 2;
                searchIndex = mid;
            }
        }

        // if here, a match was not found
        return false;
    }

    /* =============== MISCELLANEOUS METHODS =============== */

    // prints information about the dictionary - DictApp1
    public void info() {
        System.out.println("--------------------------------------------------");
        // print the dictionary name and how large the dictionary is
        System.out.println("The dictionary named \"" + name + "\" has " + nbwords + " words."
                + "\nThe dictionary is " + (sorted ? "sorted" : "un-sorted") + ".");
        // if the dictionary is full, add a warning
        if (isFull()) {
            System.out.println("This dictionary is full!");
        }
        System.out.println("--------------------------------------------------\n");
    }

    // gets a random word from the dictionary - DictApp2
    public String getRandomWord() {
        return words[new Random().nextInt(nbwords)];
    }

    // whether or not the dictionary is full - DictApp3
    public boolean isFull() {
        return nbwords >= maxsize;
    }

    // deletes the last word of the dictionary and decreases number of words by 1 - DictApp3
    public String deleteLast() {
        String last = words[nbwords - 1]; // the last word
        words[nbwords - 1] = null; // "deleting" the last word
        nbwords--; // decrementing number of words
        return last;
    }

    // deletes a given word - DictApp5
    public void delete(String query) {
        // can't delete from an empty dictionary
        if (nbwords == 0) {
            System.out.println("You can't delete a word from an empty dictionary!");
            return;
        }
        // if sorted, perform binary search, otherwise perform linear search
        if (sorted) {
            // if the word isn't found, return an error
            if (!binarySearch(query)) {
                System.out.println("Word not found!");
                return;
            }
        } else {
            // if the word isn't found, return an error
            if (!linearSearch(query)) {
                System.out.println("Word not found!");
                return;
            }
        }
        String temp; // temp word storage
        // delete the word
        words[searchIndex] = null;
        // move all other words to left to accommodate for new space
        for (int i = searchIndex; i < nbwords - 1; i++) {
            temp = words[i + 1];
            words[i] = temp;
        }

        // decrement number of words
        nbwords--;
    }

    // inserts a new word - DictApp3
    public void insert(String word) {
        // if the dictionary is empty, just add the word
        if (nbwords == 0) {
            words = new String[maxsize];
            words[0] = word;
            nbwords++;
            return;
        }

        // if the dictionary is full, can't add the word
        if (isFull()) {
            System.out.println("Dictionary is full - can't insert a word!");
            return;
        }

        if (!sorted) {
            // not sorted - add word to end of list
            words[nbwords] = word;
        } else {
            // sorted - perform search; appropriate index is stored in searchIndex
            binarySearch(word);;
            String temp; // temporary word storage
            // go down through each element from the right-most word, moving elements to the right
            for (int i = nbwords - 1; i >= searchIndex + 1; i--) {
                temp = words[i]; // store temp word
                words[i + 1] = temp;
            }
            // store the new word in the appropriate location
            words[searchIndex + 1] = word;
        }

        // increment number of words
        nbwords++;
    }

    // returns the max size word in a dictionary - DictApp4
    public int maxSizeWord() {
        // if the dictionary is empty, return zero
        if (nbwords == 0) return 0;
        int max = words[0].length(); // length of the longest word
        // loop through each word
        for (int i = 1; i < nbwords; i++) {
            // if max is less than current max, replace it
            if (max < words[i].length()) {
                max = words[i].length();
            }
        }
        return max;
    }

    // returns a word from the dictionary - DictApp4
    public String get(int index) {
        return words[index];
    }
}
