import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

///////////////////////////////////////////////////////////////////////////
class MovieArray {
    /* ==================== VARIABLES ==================== */
    private Movie[] movies; // array containing the movies
    private int maxSize; // fixed max size of the array
    private int numMovies; // number of movies
    private int steps; // number of steps used in a search

    private static Random rand = new Random();

    /* ==================== CONSTRUCTOR ==================== */
    public MovieArray(String fileName, int maxSize) {
        // if the file doesn't exist return an error
        if (!Files.exists(Paths.get(fileName))) {
            System.err.println("File (\"" + fileName + "\") not found!");
            return;
        }
        // set variables
        this.maxSize = maxSize;
        movies = new Movie[maxSize];
        // indexing variable for movies array
        final int[] i = {0};
        // file exists, load the given number, catch exceptions
        try {
            // get number of movies
            numMovies = (int) Files.lines(Paths.get(fileName)).limit((long) maxSize).count();
            // put movies in array
            Files.lines(Paths.get(fileName)).limit((long) maxSize).forEach(string -> {
                movies[i[0]] = new Movie(string);
                i[0]++;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // duplicates the MovieArray into a new array
    public MovieArray(MovieArray array) {
        // copy values over
        this.movies = array.movies.clone();
        this.maxSize = array.maxSize;
        this.numMovies = array.numMovies;
        this.steps = array.steps;
    }

    /* ==================== PUBLIC METHODS ==================== */
    // inserts an item into the database
    public void insert(Movie movie) {
        // if there's not enough room, enlarge
        if (numMovies == movies.length) {
            enlarge();
        }
        // sorted, insert at appropriate location in array
        int i = numMovies - 1; // start at last index
        // move items over until we get to the appropriate location
        while (movies[i].compareTo(movie) > 0 && i > 0) {
            movies[i] = movies[i - 1];
        }
        // at appropriate location, insert here
        movies[i - 1] = movie;
        // increment number of movies
        numMovies++;
    }

    // saves the database to a given file location
    public void save(String fileName) {
        try {
            // if the file doesn't exist, create if
            if (!Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName));
            }
            // write the data to the file, over-writing any existing data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                for (Movie movie : movies) {
                    if (movie != null) {
                        writer.write(movie.toString());
                        writer.write("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getter for size
    public int getSize() {
        return numMovies;
    }

    // ================ SORTING ================ //
    public void insertionSort() {
        // loop through each element in the array, inserting it in the appropriate position of the "already sorted" portion
        for (int i = 1; i < numMovies; i++) {
            // save current element
            Movie current = movies[i];
            // inner looping index
            int j = i - 1;
            // move elements to the right until we reach the appropriate location
            while (j >= 0 && movies[j].compareTo(current) < 0) {
                movies[j + 1] = movies[j];
                j--;
            }
            // insert the current value to the appropriate location
            movies[j + 1] = current;
        }
    }

    public void mergeSort() {
        // call recursive merge sort
        recMergeSort(0, numMovies - 1);
    }

    public void quickSort() {
        // call recursive quick sort
        recQuickSort(0, numMovies - 1);
    }

    public void heapSort() {
        // recursively heapify from the root
        heapify(0);
        // if the database is 50 or fewer, print the list of movies
        if (numMovies <= 50) {
            System.out.println("Result after Heapify:");
            for (int i = 0; i < movies.length; i++) {
                System.out.println(i + " " + movies[i]);
            }
        }
        // temporary array to store sorted values in
        Movie[] temp = new Movie[maxSize];
        // remove values and move into temp
        int num = numMovies;
        for (int i = 0; i < num; i++) {
            temp[i] = remove();
        }
        // point "movies" to "temp" and reset numMovies
        movies = temp;
    }

    public void shellSort() {
        Movie temp; // temp storage for swapping
        int h = 1; // increment distance
        // find the appropriate increment with Knuth's sequence
        while (h <= numMovies / 3) {
            h = 3 * h + 1;
        }
        // continue looping until increment is 1
        while (h > 0) {
            // loop through each value starting at increment
            for (int i = h; i < numMovies; i++) {
                // store current element
                temp = movies[i];
                int j = i; // inner index
                // move items until we find the appropriate location
                while (j >= h && (temp.compareTo(movies[j - h]) > 0)) {
                    movies[j] = movies[j - h];
                    j -= h;
                }
                // put temp value in appropriate location
                movies[j] = temp;
            }
            // decrement interval
            h = (h - 1) / 3;
        }
    }

    // performs binary search
    public boolean binarySearch(Movie movie) {
        // reset number of steps
        steps = 0;
        int midIndex = numMovies / 2; // index of middle element
        int start = 0, end = numMovies - 1; // start and end indices of the section being searched
        // while there's elements in the range
        while (end - start > 1) {
            // increase number of steps
            steps++;
            if (movies[midIndex].compareTo(movie) > 0) {
                // movie is in the lower half
                end = midIndex; // move interval down
            } else if (movies[midIndex].compareTo(movie) < 0) {
                // movie is in upper half
                start = midIndex; // move interval up
            } else {
                // movie was found
                return true;
            }
            // re-assign midIndex
            midIndex = (start + end) / 2;
        }
        // once here, movie was not found
        return false;
    }

    // returns a random movie from the array
    public Movie getRandomTitle() {
        return movies[rand.nextInt(numMovies)];
    }

    // getter for steps
    public int getStep() {
        return steps;
    }

    // deletes the last movie in the array
    public Movie delete() {
        Movie last = movies[numMovies - 1];
        movies[numMovies - 1] = null;
        numMovies--;
        return last;
    }

    // whether or not this array is empty
    public boolean isEmpty() {
        return numMovies == 0;
    }

    /* ==================== PRIVATE METHODS ==================== */
    // enlarges the array if necessary
    private void enlarge() {
        // enlargement shouldn't be often necessary and could easily hog resources so I'm opting for a small
        // increase in array space
        Movie[] temp = new Movie[(int) (movies.length * 1.3)];
        System.arraycopy(movies, 0, temp, 0, movies.length); // copying the array
        movies = temp; // moving array back to "movies"
    }

    // ================ SORTING ================ //
    // merge two sections of an array
    private void merge(int left, int middle, int right) {
        // temporary array to store values in
        Movie[] temp = new Movie[maxSize];
        // copy the array over
        System.arraycopy(movies, left, temp, left, right + 1 - left);
        int i = left; // left probe
        int j = middle + 1; // right probe
        int k = left; // array index
        // copy the smallest values into the original array
        while (i <= middle && j <= right) {
            if (temp[i].compareTo(temp[j]) > 0) {
                movies[k] = temp[i];
                i++;
            } else {
                movies[k] = temp[j];
                j++;
            }
            k++;
        }
        // copy any remaining values over
        while (i <= middle) {
            movies[k] = temp[i];
            i++;
            k++;
        }
    }

    // recursively merge sort
    private void recMergeSort(int left, int right) {
        // base case - only one element
        if (!(left < right)) return;

        // split into two sub arrays
        int middle = (left + right) / 2;
        // mergeSort lower half
        recMergeSort(left, middle);
        // mergeSort upper half
        recMergeSort(middle + 1, right);
        // merge sorted halves
        merge(left, middle, right);
    }

    // partition a section of an array
    private int partition(int left, int right) {
        Movie pivot = movies[right]; // use right-most value as pivot
        Movie temp; // temp storage for swapping
        int i = left - 1; // index of smaller movie
        // loop through each value
        for (int j = left; j <= right; j++) {
            // if current value is less than the pivot, increment smaller index and swap
            if (movies[j].compareTo(pivot) > 0) {
                i++;
                temp = movies[i];
                movies[i] = movies[j];
                movies[j] = temp;
            }
        }
        // if largest movie is less than the movie next to the smaller movie, swap them
        if (movies[right].compareTo(movies[i + 1]) > 0) {
            temp = movies[right];
            movies[right] = movies[i + 1];
            movies[i + 1] = temp;
        }
        // return the pivot point
        return i + 1;
    }

    // recursively quick sort
    private void recQuickSort(int left, int right) {
        // base case - only one item
        if (!(left < right)) return;
        // partition array
        int pivot = partition(left, right);
        // quick sort the left and right halves
        recQuickSort(left, pivot - 1);
        recQuickSort(pivot + 1, right);
    }

    // remove from the array; assumes array has been fully heapified
    private Movie remove() {
        // store root to return
        Movie root = movies[0];
        // move the last item to the root
        movies[0] = movies[--numMovies];
        // trickle the new root down
        trickleDown(0);
        // return the old root
        return root;
    }

    // heapifies the array
    private void heapify(int index) {
        // base case - when there's no children
        if (index > numMovies / 2 - 1) return;
        // heapify the right subtree
        heapify(index * 2 + 2);
        // heapify the left subtree
        heapify(index * 2 + 1);
        // trickle down at this node
        trickleDown(index);
    }

    // performs trickle down routine
    private void trickleDown(int index) {
        Movie root = movies[index]; // store the root
        // loop until there's no more children
        while (index < numMovies / 2) {
            // indices of child nodes
            int leftIndex = 2 * index + 1;
            int rightIndex = 2 * index + 2;
            int largerIndex;
            // determine larger child
            if (rightIndex < numMovies && movies[rightIndex].compareTo(movies[leftIndex]) > 0) {
                // right child is larger
                largerIndex = rightIndex;
            } else {
                // left child is larger
                largerIndex = leftIndex;
            }
            // if the root is larger, we've found the appropriate location
            if (root.compareTo(movies[largerIndex]) > 0) {
                break;
            }
            // move larger child up
            movies[index] = movies[largerIndex];
            // adjust index
            index = largerIndex;
        }
        // move root to appropriate location
        movies[index] = root;
    }

}
