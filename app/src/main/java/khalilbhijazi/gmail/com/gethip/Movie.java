package khalilbhijazi.gmail.com.gethip;

public class Movie {

    private String genre;
    private String name;

    //default constructor

    public Movie() {

    }

    //constructor for movie object. takes in two strings, the movie's genre and name, and
    //initializes private variables with those string values

    public Movie(String genre, String name) {
        this.genre = genre;
        this.name = name;
    }

    //returns the genre of the current movie object

    public String getGenre() {
        return genre;
    }

    //returns the name of the current movie object
    public String getName() {
        return name;
    }

    //sets the genre of the current movie object

    public void setGenre(String genre) {
        this.genre = genre;
    }

    //sets the name of the current movie object

    public void setName(String name) {
        this.name = name;
    }


    //returns a boolean value that indicates whether the current movie is equal to the passed in
    //argument. Two movies are only equal if they share the same fields

    public boolean equals(Object other) {
        if (!(other instanceof Movie)) {
            return false;
        } else {
            Movie otherMovie = (Movie) other;
            return this.name.equals(otherMovie.getName()) && this.genre.equals(otherMovie.getGenre());
        }
    }
}
