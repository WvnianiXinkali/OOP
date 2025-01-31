package Quiz.src.main.java.models;

public class UserAchievement {
    private int Id;
    private int UserId;
    private int AchievementId;

    public UserAchievement(int id, int userId, int achievementId){
        Id = id;
        UserId = userId;
        AchievementId = achievementId;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return UserId;
    }

    public int getAchievementId() {
        return AchievementId;
    }
}
