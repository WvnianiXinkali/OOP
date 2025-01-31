package Quiz.src.main.java.models;

public class TagQuiz {
    public int id;
    public int tag_id;
    public int quiz_id;

    public TagQuiz(int id, int tag_id, int quiz_id) {
        this.id = id;
        this.tag_id = tag_id;
        this.quiz_id = quiz_id;
    }
}
