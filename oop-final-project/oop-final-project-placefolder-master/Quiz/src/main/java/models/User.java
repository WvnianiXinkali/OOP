package Quiz.src.main.java.models;
import java.sql.Timestamp;

public class User {
    private int Id;
    private String Username;
    private String PasswordHash;
    private String PfpLink;
    private String Role;
    private boolean isPrivate;
    private String EventTime;

    public User(int id, String username, String passwordHash, String role, boolean isPrivate){
        Id = id;
        Username = username;
        PasswordHash = passwordHash;
        Role = role;
        this.isPrivate = isPrivate;
        //EventTime = eventTime;
    }

    public int getId() { return Id; }
    public boolean isAdmin(){ return Role.equals("admin");
    }
    public String getRole(){ return Role;
    }
    public String getUsername() { return Username; }
    public String getPasswordHash() { return PasswordHash; }
    public String getPfpLink(){ return PfpLink; }
    public void setPfpLink(String pfpLink) { PfpLink = pfpLink; }
    public boolean isPrivate() { return isPrivate; }
    public String getEventTime() { return EventTime; }

    @Override
    public String toString() {
        return "" + getId() + " " + isAdmin() + getRole() + getUsername() + getPasswordHash() + getPfpLink() + isPrivate()
                +getEventTime();
    }
}
