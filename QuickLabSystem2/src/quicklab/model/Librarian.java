package quicklab.model;

public class Librarian extends User {
    private int employeeID;

    public Librarian(int userID, String name, String password, int employeeID) {
        super(userID, name, password);
        this.employeeID = employeeID;
    }

    public boolean approveRental(Rental r) {
        if (!"Pending".equals(r.getStatus())) return false;
        r.approve();
        if (r.getMember() instanceof Member m) m.incrementRentals();
        return true;
    }

    public void returnRental(Rental r) {
        if (!"Approved".equals(r.getStatus())) return;
        r.returnBook();
        if (r.getMember() instanceof Member m) m.decrementRentals();
    }

    public void blockUser(User u)   { u.setStatus("Blocked"); }
    public void unblockUser(User u) { u.setStatus("Active"); }
    public void markSpoiler(Review r)  { r.setSpoilerTag(true); }
    public void unmarkSpoiler(Review r){ r.setSpoilerTag(false); }

    public Book addBook(int id, String title, String author, String genre,
                        String desc, java.awt.Color c, String emoji) {
        return new Book(id, title, author, genre, desc, c, emoji);
    }

    @Override public String getRole() { return "Librarian"; }
    public int getEmployeeID()        { return employeeID; }
}
