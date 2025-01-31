package Quiz.src.main.java.models;

import java.time.LocalDateTime;

public class UserBan {
    public LocalDateTime ban_from_until;
    public int id;
    public int user_id;
    public int durationDays;

    public UserBan(int id, int user_id, LocalDateTime ban_from_until, int durationDays){
        this.id = id;
        this.user_id = user_id;
        this.ban_from_until = ban_from_until;
        this.durationDays = durationDays;
    }

    public LocalDateTime getBan_Unitl(){
        if(durationDays == -1) return ban_from_until;
        LocalDateTime banUntil = ban_from_until.plusDays(durationDays);
        return banUntil;
    }

    public boolean userStillBanned(){
        LocalDateTime now = LocalDateTime.now();
        if(this.durationDays == -1) {
            return now.isBefore(this.ban_from_until);
        }
        LocalDateTime banUntil = getBan_Unitl();

        return now.isBefore(banUntil);
    }
}
