package Quiz.src.main.java.models;

public class rateAndReview {
    public int id;
    public String review;
    public int rating;
    public int userId;
    public int quizId;

    public rateAndReview(int id, int quizId, int userId, int rating, String review){
        this.id = id;
        this.rating = rating;
        this.review = review;
        this.userId = userId;
        this.quizId = quizId;
    }
}
