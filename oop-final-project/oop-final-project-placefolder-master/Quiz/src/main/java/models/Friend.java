package Quiz.src.main.java.models;

public class Friend {
    private int Id;
    private int User_id;
    private int Friend_id;

    public Friend(int id, int user_id, int friend_id){
        Id = id;
        User_id = user_id;
        Friend_id = friend_id;
    }

    public int getId() {
        return Id;
    }

    public int getUser_id() {
        return User_id;
    }

    public int getFriend_id() {
        return Friend_id;
    }
}
