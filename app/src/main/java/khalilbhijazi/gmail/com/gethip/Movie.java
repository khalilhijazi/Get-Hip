package khalilbhijazi.gmail.com.gethip;

public class Movie {

    private String genre;
    private String name;

    public Movie() {

    }

    public Movie(String genre, String name) {
        this.genre = genre;
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }
}
