import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class HashTable {

    /* ==================== VARIABLES ==================== */
    private Movie[] movies; // array to store movies
    private int maxSize; // fixed max size of the hash table
    private int numMovies; // number of movies in the hash table
    private int collisions; // number of collisions
    private int maxProbeLength; // furthest probe has to travel to handle collisions
    private int steps; // number of steps when searching

    private static final int CONSTANT = 5; // used for double hashing

    /* ==================== CONSTRUCTOR ==================== */
    public HashTable(MovieArray array) {
        // set up variables
        movies = new Movie[getPrime(2 * array.getSize())];
        maxSize = movies.length;
        numMovies = array.getSize();
        collisions = 0;
        maxProbeLength = 0;
        // create a copy of the array so as not to remove elements from the original
        MovieArray copy = new MovieArray(array);
        // insert each movie from the array
        while (!copy.isEmpty()) {
            insert(copy.delete());
        }
    }

    /* ==================== PUBLIC METHODS ==================== */
    // inserts a movie into the hash table
    public void insert(Movie movie) {
        // can't insert into a full hash table (shouldn't ever happen)
        if (numMovies == maxSize) return;
        int index = hash1(movie); // insertion index
        int step = hash2(movie); // step size
        int steps = 0; // number of steps
        // find the appropriate location for insertion
        while (movies[index] != null) {
            // increment relevant variables
            collisions++;
            steps++;
            index = (index + step) % maxSize;
        }
        // insert the movie
        movies[index] = movie;
        // if necessary, update maxProbeLength
        if (steps > maxProbeLength) {
            maxProbeLength = steps;
        }
    }

    // saves the hash table into a file
    public void saveHashTable(String fileName) {
        try {
            // if the file doesn't exist, create it
            if (!Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName));
            }
            // write the data to the file, over-writing any existing data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                // write maxSize of the table at the top
                writer.write(String.valueOf(maxSize));
                writer.write("\n");
                // write each movie
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i] != null) {
                        // non-null, write the movie
                        writer.write(i + " " + movies[i].toString());
                        writer.write("\n");
                    } else {
                        // null, write five asterisks
                        writer.write(i + " *****");
                        writer.write("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // finds a movie in the table
    public boolean find(Movie movie) {
        // if empty, return false
        if (numMovies == 0) return false;
        int index = hash1(movie); // insertion index
        int step = hash2(movie); // step size
        int start = index; // where we started searching
        steps = 0; // number of steps taken to find
        // check first item
        if (movie.equals(movies[index])) {
            return true;
        }
        // increment index
        index = (index + step) % maxSize;
        steps++;
        // step along until we find the item OR reach the start (i.e. went through each item)
        while (!movie.equals(movies[index]) && index != start) {
            index = (index + step) % maxSize;
            steps++;
        }
        // true if we found the movie
        return movie.equals(movies[index]);
    }

    // getter for maxSize
    public int getMaxSize() {
        return maxSize;
    }

    // getter for collisions
    public int getCol() {
        return collisions;
    }

    // getter for maxProbeLength
    public int getMaxProbe() {
        return maxProbeLength;
    }

    // getter for steps
    public int getStep() {
        return steps;
    }

    /* ==================== PRIVATE METHODS ==================== */
    // hashes the movie title using the Horner method
    private int hash1(Movie movie) {
        // set title to lower case since I'm ignoring case
        char[] letters = movie.getTitle().toLowerCase().toCharArray();
        int hash = 0;
        // loop through each character
        for (char letter : letters) {
            hash = (hash * 256 + letter) % maxSize;
        }
        return hash;
    }

    // hashes again to compute step
    private int hash2(Movie movie) {
        int hash = hash1(movie); // get the key
        return CONSTANT - (hash % CONSTANT); // return step
    }

    ///////////////////////////////////////////////////////////////
    /////////////// get the first prime number after number 'min' 
    ///////////////// (from TextBook Chapter 11) /////////////////
    //////////////////////////////////////////////////////////////


    private int getPrime(int min)
    // returns 1st prime > min
    {
        for (int j = min + 1; true; j++)
            // for all j > min
            if (isPrime(j))
                // is j prime?
                return j;
        // yes, return it
    }

    // -------------------------------------------------------------
    private boolean isPrime(int n)
    // is n prime?
    {
        for (int j = 2; (j * j <= n); j++)
            // for all j
            if (n % j == 0)
                // divides evenly by j?
                return false;
        // yes, so not prime
        return true;
        // no, so prime
    }

}
