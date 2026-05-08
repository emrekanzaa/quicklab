package quicklab.model;

import quicklab.factory.RentalFactory;
import quicklab.service.RentalService;

public class Member extends User {
    private int activeRentals;

    public Member(int userID, String name, String password) {
        super(userID, name, password);
    }

    public Rental requestRental(Book book, String type) {
        if (!"Active".equals(status) || !book.isAvailable()) return null;
        RentalService svc = RentalFactory.create(type);
        return new Rental(this, book, svc, type);
    }

    public Review addReview(Book book, String content, int rating,
                            boolean isSpoiler, int nextID) {
        Review r = new Review(nextID, book.getBookID(), userID, name,
                              content, rating, isSpoiler);
        book.addReview(r);
        return r;
    }

    public void incrementRentals() { activeRentals++; }
    public void decrementRentals() { if (activeRentals > 0) activeRentals--; }

    @Override public String getRole() { return "Member"; }
    public int getActiveRentals()     { return activeRentals; }
}
