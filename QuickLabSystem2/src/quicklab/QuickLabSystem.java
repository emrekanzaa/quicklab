package quicklab;

import quicklab.model.*;
import java.util.*;

public class QuickLabSystem {
    private List<Book>   books   = new ArrayList<>();
    private List<User>   users   = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private int nextBookID = 1, nextReviewID = 1;

    public Optional<User> findUser(String name, String pw) {
        return users.stream().filter(u -> u.getName().equalsIgnoreCase(name) && u.login(pw)).findFirst();
    }
    public Optional<Book> findBook(int id) {
        return books.stream().filter(b -> b.getBookID() == id).findFirst();
    }

    public void addBook(Book b)     { books.add(b); }
    public void addUser(User u)     { users.add(u); }
    public void addRental(Rental r) { if (r!=null) rentals.add(r); }
    public void addReview(Review r) { if (r!=null) reviews.add(r); }

    public int nextBookID()   { return nextBookID++; }
    public int nextReviewID() { return nextReviewID++; }

    public List<Book>   getBooks()   { return books; }
    public List<User>   getUsers()   { return users; }
    public List<Rental> getRentals() { return rentals; }
    public List<Review> getReviews() { return reviews; }

    public Rental getRentalByMemberAndBook(int memberID, int bookID) {
        return rentals.stream()
            .filter(r -> r.getMember().getUserID()==memberID
                      && r.getBook().getBookID()==bookID
                      && ("Approved".equals(r.getStatus()) || "Pending".equals(r.getStatus())))
            .findFirst().orElse(null);
    }
}
