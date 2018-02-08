class Movie implements Comparable<Movie> {

    // Common private variables
    private String title;
    private String studio;
    private String price;
    private String rating;
    private String year;
    private String genre;

    // Constructor: construct the Movie class with the 3 given parameters
    public Movie(String title, String studio, String price, String rating, String year, String genre) {
        this.title = title;
        this.studio = studio;
        this.price = price;
        this.rating = rating;
        this.year = year;
        this.genre = genre;
    }

    // automatically splits and parses a given string
    public Movie(String data) {
        String[] args = data.split("\\|"); // split the data
        // if there aren't enough arguments, do nothing
        if (args.length != 6) return;
        // set the data
        title = args[0];
        studio = args[1];
        price = args[2];
        rating = args[3];
        year = args[4];
        genre = args[5];
    }

    /////////////////////////////////////////////////////////
    // methods 
    /////////////////////////////////////////////////////////
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return title + "|" + studio + "|" + price + "|" + rating + "|" + year + "|" + genre;
    }

    @Override
    public int compareTo(Movie o) {
        return this.title.compareToIgnoreCase(o.title);
    }

    public boolean equals(Movie movie) {
        if (movie == null) return false;
        return title.equalsIgnoreCase(movie.title);
    }
}
