package Quiz.src.main.java.models;

import java.time.LocalDateTime;

public class QuizHistory {
    private int Id;
    private double Score;
    private int Quiz_id;
    private int User_id;
    private int Time_taken;
    private Quiz _quiz;
    private LocalDateTime take_date;

    public QuizHistory(int id, double score, int quiz_id, int user_id, int time_taken){
        Id = id;
        Score = score;
        Quiz_id = quiz_id;
        User_id = user_id;
        Time_taken = time_taken;
    }

    public int getId() {
        return Id;
    }

    public int getUser_id() {
        return User_id;
    }

    public int getQuiz_id() {
        return Quiz_id;
    }

    public double getScore() {
        return Score;
    }

    public int getTime_taken() {
        return Time_taken;
    }

    public void setQuiz(Quiz quiz){
        _quiz = quiz;
    }

    public Quiz getQuiz(){
        return _quiz;
    }
    public void setTakeDate(LocalDateTime take_date){
        this.take_date = take_date;
    }
    public LocalDateTime getTakeDate(){
        return take_date;
    }
}
