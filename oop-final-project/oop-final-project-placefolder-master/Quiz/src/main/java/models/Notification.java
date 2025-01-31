package Quiz.src.main.java.models;

public class Notification {
    private int Id;
    private int ReceiverId;
    private int SenderId;
    private String NotifType;
    private String NotifBody;

    public Notification(int id, int receiver_id, int sender_id, String notifType, String notifBody){
        Id = id;
        ReceiverId = receiver_id;
        SenderId = sender_id;
        NotifType = notifType;
        NotifBody = notifBody;
    }

    public int getId() {
        return Id;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public int getSenderId() {
        return SenderId;
    }

    public String getNotifType() {
        return NotifType;
    }

    public String getNotifBody() {
        return NotifBody;
    }
}