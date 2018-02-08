class Word{
    // private variables 
    private String word; // the word itself
    private int numLetters; // number of letters in the word
    private String dictionary; // dictionary the word belongs to

    //constructor
    public Word(String word, int numLetters, String dictionary) {
        this.word = word;
        this.numLetters = numLetters;
        this.dictionary = dictionary;
    }

    // methods
    public String getWord() {
        return word;
    }

    public int getNumLetters() {
        return numLetters;
    }

    public String getDictionary() {
        return dictionary;
    }

    @Override
    public String toString() {
        return word + "; " + numLetters + "; " + dictionary;
    }
}
