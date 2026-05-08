package quicklab.model;

public class Review {
    private int reviewID, bookID, memberID, rating;
    private String content, memberName;
    private boolean isSpoiler;

    public Review(int reviewID, int bookID, int memberID, String memberName,
                  String content, int rating, boolean isSpoiler) {
        this.reviewID   = reviewID;
        this.bookID     = bookID;
        this.memberID   = memberID;
        this.memberName = memberName;
        this.content    = content;
        this.rating     = rating;
        this.isSpoiler  = isSpoiler;
    }

    public void updateContent(String c) { content = c; }
    public void setSpoilerTag(boolean v){ isSpoiler = v; }

    public int     getReviewID()  { return reviewID; }
    public int     getBookID()    { return bookID; }
    public int     getMemberID()  { return memberID; }
    public String  getMemberName(){ return memberName; }
    public String  getContent()   { return content; }
    public int     getRating()    { return rating; }
    public boolean isSpoiler()    { return isSpoiler; }
}
