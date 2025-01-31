package Quiz.src.main.java.models;

public class Answer{
    public int id;
    public int question_id;
    public String answer;
    public boolean isCorrect;

    public Answer(int id, int question_id, String answer, boolean isCorrect) {
        this.id = id;
        this.question_id = question_id;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getId(){
        return id;
    }

    public String getAnswer(){
        return answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
