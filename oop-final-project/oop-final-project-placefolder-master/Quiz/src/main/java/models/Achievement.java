package Quiz.src.main.java.models;

public class Achievement {
    private int Id;
    private String AchievementBody;
    private String ToEarn;
    private String Icon;

    public Achievement(int id, String achievementBody, String toEarn){
        Id = id;
        AchievementBody = achievementBody;
        ToEarn = toEarn;
    }

    public int getId() {
        return Id;
    }

    public String getAchievementBody() {
        return AchievementBody;
    }

    public String getAchievementToEarn() {
        return ToEarn;
    }

    public void setAchievementIcon(String icon){
        Icon = icon;
    }

    public String getAchievementIcon(){
        return Icon;
    }
}