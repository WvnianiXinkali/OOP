package Quiz.src.main.java.models;

public class Announcement {
    private int Id;
    private String AnnouncementBody;

    public Announcement(int id, String announcementBody) {
        Id = id;
        AnnouncementBody = announcementBody;
    }

    public int getId() {
        return Id;
    }

    public String getAnnouncementBody() {
        return AnnouncementBody;
    }
}
