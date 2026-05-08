package quicklab.model;

public abstract class User {   //inheritance
    protected int userID;
    protected String name, password, status;

    public User(int userID, String name, String password) {
        this.userID    = userID;
        this.name      = name;
        this.password  = password;
        this.status    = "Active";
    }

    public boolean login(String pw) {
        return !("Blocked".equals(status)) && password.equals(pw);
    }

    public abstract String getRole();  //polymorphism

    public int    getUserID()         { return userID; }
    public String getName()           { return name; }
    public String getStatus()         { return status; }
    public void   setStatus(String s) { status = s; }
    @Override public String toString() { return name; }   // encapsolutiob
}
