package quicklab.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private int bookID;
    private String title, author, genre, description;
    private boolean isAvailable;
    private List<Review> reviews;
    private Color coverColor;
    private String coverEmoji;

    public Book(int bookID, String title, String author, String genre,
                String description, Color coverColor, String coverEmoji) {
        this.bookID      = bookID;
        this.title       = title;
        this.author      = author;
        this.genre       = genre;
        this.description = description;
        this.isAvailable = true;
        this.reviews     = new ArrayList<>();
        this.coverColor  = coverColor;
        this.coverEmoji  = coverEmoji;
    }

    public void addReview(Review r) { reviews.add(r); }
    public void setAvailable(boolean v) { isAvailable = v; }

    public int          getBookID()     { return bookID; }
    public String       getTitle()      { return title; }
    public String       getAuthor()     { return author; }
    public String       getGenre()      { return genre; }
    public String       getDescription(){ return description; }
    public boolean      isAvailable()   { return isAvailable; }
    public List<Review> getReviews()    { return reviews; }
    public Color        getCoverColor() { return coverColor; }
    public String       getCoverEmoji() { return coverEmoji; }

    public double getAvgRating() {
        return reviews.isEmpty() ? 0 :
               reviews.stream().mapToInt(Review::getRating).average().orElse(0);
    }

    @Override public String toString() { return title + " — " + author; }
}
