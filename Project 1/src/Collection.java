import java.util.Random;

class Collection {

    /////////////////////////////////////
    ///////////// Private Variables//////
    /////////////////////////////////////
    private Dictionary[] dictionaries;
    private String name;
    private int steps = 0;

    /////////////////////////////////////
    ///////////// Constructor///////////
    /////////////////////////////////////
    public Collection(Dictionary dict) {
        name = dict.getName();
        dictionaries = new Dictionary[dict.maxSizeWord()];
        init(dict);
    }

    /////////////////////////////////////
    ///////////// Methods     ///////////
    /////////////////////////////////////

    // prints information requested - DictApp4
    public void info() {
        System.out.println("--------------------------------------------------");
        // print the dictionary name and how large the dictionary is
        System.out.println("The dictionary named \"" + name + "\" has " + dictionaries.length + " dictionaries.");
        System.out.println("dict --> size");
        for (int i = 0; i < dictionaries.length; i++) {
            System.out.println(i + 1 + " --> " + dictionaries[i].getSize());
        }
        System.out.println("--------------------------------------------------\n");
    }

    // saves the collection to separate files with appropriate extension - DictApp4
    public void saveCollection() {
        for (int i = 0; i < dictionaries.length; i++) {
            dictionaries[i].saveDictionary(String.valueOf(i + 1));
        }
    }

    // searches for a word in the appropriate dictionary - DictApp4
    public boolean search(String query) {
        // appropriate dictionary for a word is its length minus one
        Dictionary dict = dictionaries[query.length() - 1];
        boolean result =  dict.linearSearch(query); // preforms the query, stores the result
        steps = dict.getStep(); // set the number of steps it took
        return result;
    }

    // returns number of steps necessary to search the sub-dictionary
    public int getStep() {
        return steps;
    }

    // crack lock method - DictApp5
    public void crackLock(char[][] lock, int nbletter, int nboption) {
        Dictionary dict = dictionaries[nbletter - 1]; // the dictionary used in this method
        int c = (int) Math.pow(nboption, nbletter); // number of combinations
        int wordsFound = 0; // number of words found
        // generating the random combinations of characters, storing them in a
        String[] words = getWords(c, lock);
        // loop through each randomly generated word
        for (String word : words) {
            // if the word is found in the dictionary, delete it, increment number of words found, and print it out
            if (dict.linearSearch(word)) {
                dict.delete(word);
                wordsFound++;
                System.out.println(word);
            }
        }
        System.out.println("Number of words found: " + wordsFound);
    }

    /* =============== PRIVATE METHODS =============== */

    // initializes the dictionaries with appropriate values from main dictionary - DictApp4
    private void init(Dictionary dict) {
        // initialize each dictionary object, sets sorted
        for (int i = 0; i < dictionaries.length; i++) {
            dictionaries[i] = new Dictionary(300000);
            dictionaries[i].setName(dict.getName());
        }

        // go through each word in dict and put it in the appropriate dictionary
        for (int i = 0; i < dict.getSize(); i++) {
            // length of the word minus one will be the index of the dictionary it is stored in
            dictionaries[dict.get(i).length() - 1].insert(dict.get(i));
        }
    }

    // returns an array of strings containing random combinations of letters provided for crackLock - DictApp5
    private String[] getWords(int c, char[][] lock) {
        String[] words = new String[5 * c]; // array to store random combinations of letters
        // loop through each space in the array
        for (int i = 0; i < words.length; i++) {
            // build a string from the characters provided
            StringBuilder sb = new StringBuilder();
            for (char[] character : lock) {
                sb.append(character[new Random().nextInt(character.length)]);
            }
            // store the string
            words[i] = sb.toString();
        }
        return words;
    }
   
}

