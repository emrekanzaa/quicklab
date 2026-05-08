package quicklab.model;

import quicklab.service.RentalService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rental {
    private static int counter = 1;
    private int rentalID;
    private User member;
    private Book book;
    private String type, status;
    private double price;
    private LocalDate requestDate;
    private LocalDate dueDate;     
    private LocalDate returnDate;
    private boolean bookCollected = false;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public Rental(User member, Book book, RentalService service, String type) {
        this.rentalID    = counter++;
        this.member      = member;
        this.book        = book;
        this.type        = type;
        this.status      = "Pending";
        this.price       = service.calculatePrice();
        this.requestDate = LocalDate.now();
    }

    public void approve() {
        status  = "Approved";
        dueDate = LocalDate.now().plusWeeks(2);
        book.setAvailable(false);
    }

    public void returnBook() {
        status     = "Returned";
        returnDate = LocalDate.now();
        book.setAvailable(true);
    }

    public int         getRentalID()    { return rentalID; }
    public User        getMember()      { return member; }
    public Book        getBook()        { return book; }
    public String      getType()        { return type; }
    public String      getStatus()      { return status; }
    public void        setStatus(String s){ status = s; }
    public double      getPrice()       { return price; }
    public LocalDate   getDueDate()     { return dueDate; }
    public LocalDate   getRequestDate() { return requestDate; }

    public String getDueDateStr() {
        return dueDate != null ? dueDate.format(FMT) : "—";
    }
    public String getRequestDateStr() {
        return requestDate.format(FMT);
    }

    public boolean isBookCollected() { return bookCollected; }
    public void setBookCollected(boolean v) { bookCollected = v; }

    public boolean isOverdue() {
        return dueDate != null && "Approved".equals(status) && LocalDate.now().isAfter(dueDate);
    }
}
