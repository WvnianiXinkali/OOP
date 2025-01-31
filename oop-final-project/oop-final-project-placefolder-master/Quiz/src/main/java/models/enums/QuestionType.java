package Quiz.src.main.java.models.enums;

public enum QuestionType {
    QUESTION_RESPONSE(0),
    FILL_IN_THE_BLANK(1),
    MULTIPLE_CHOICE(2),
    PICTURE_RESPONSE(3),
    MULTI_ANSWER(4),
    MULTI_AN_CHOICE(5),
    MATCHING(6);
    public final int value;
    QuestionType(int k){
        this.value = k;
    }
    public static QuestionType fromInt(int value) {
        for (QuestionType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(" " + value);
    }
}

