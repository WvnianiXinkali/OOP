package Quiz.src.main.java.HelperMethods;

import Quiz.src.main.java.models.Answer;
import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerChecker {
        public static ArrayList<String> checkAnswer(int questionId,ArrayList<String> givenAnswers){
            DBConn con = new DBConn();
            Question quest = con.getQuestion(questionId);
            List<Answer> answers = con.getAnswers(questionId,true);
            ArrayList<String> correctAnswers= new ArrayList<>();
            double score = answers.size();
            if(quest.isMultiAnswerType()){
                for(int i=0;i<answers.size();i++){
                    if(givenAnswers.contains(answers.get(i).answer)){
                        correctAnswers.add(answers.get(i).answer);
                    }
                }
            }else{

                if(givenAnswers.contains(answers.get(0).answer)) {
                    correctAnswers.add(answers.get(0).answer);
                }
            }
            return correctAnswers;
        }

    public static ArrayList<Boolean> checkAnswerByStringForMulties(int questionId, ArrayList<String> myans) {
        DBConn con = new DBConn();
        Question quest = con.getQuestion(questionId);
        List<Answer> answers = con.getAnswers(questionId, true);
        List<Answer> allAnswers = con.getAnswers(questionId, false);

        ArrayList<Boolean> correctnessList = new ArrayList<>();


        if (myans == null) {
            for (int i = 0; i < allAnswers.size(); i++) {
                correctnessList.add(false);
            }
            return correctnessList;
        }

        myans.forEach(System.out::println);

        for (int i = 0; i < allAnswers.size(); i++) {
            Answer answer = allAnswers.get(i);
            if (myans != null && myans.contains(answer.getAnswer())) {
                boolean isCorrect = answers.stream()
                        .map(Answer::getAnswer)
                        .anyMatch(correctAnswer -> correctAnswer.equals(answer.getAnswer()));
                correctnessList.add(isCorrect);
            } else {
                correctnessList.add(false);
            }
            }
        for(int i = 0; i < correctnessList.size(); i++){
            if(correctnessList.get(i)){
                int j = myans.indexOf(allAnswers.get(i).answer);
                if(j != -1 && !correctnessList.get(j)){
                    correctnessList.set(j, true);
                    correctnessList.set(i, false);
                }
            }
        }
        return correctnessList;
    }

    public static boolean checkAnswerByString(int questionId,String answer, boolean isFilled, int answerId){
        DBConn con = new DBConn();
        Question quest = con.getQuestion(questionId);
        List<Answer> allAnswers = con.getAnswers(questionId,false);
        List<Answer> answers = con.getAnswers(questionId,true);

        if (quest.isMultiAnswerType()) {
            if (isFilled) {
                Answer selectedAnswer = allAnswers.get(answerId);
                return answers.contains(selectedAnswer) && answer.equals(selectedAnswer.answer);
            }
            List<String> correctAnswers = answers.stream()
                    .map(Answer::getAnswer)
                    .collect(Collectors.toList());

            List<String> userAnswers = Arrays.asList(answer.split(","));

            return correctAnswers.containsAll(userAnswers) && userAnswers.containsAll(correctAnswers);
        }

        return answers.stream()
                .map(Answer::getAnswer)
                .anyMatch(myanswer -> myanswer.equals(answer));
    }

    public static int correctAnswerCount(int questionId,ArrayList<String> givenAnswers){
        DBConn con = new DBConn();
        Question quest = con.getQuestion(questionId);
        List<Answer> answers = con.getAnswers(questionId,true);
        ArrayList<String> correctAnswers= new ArrayList<>();
        // double score = answers.size();
        int score = 0;
        if(quest.isMultiAnswerType()){
            for(int i=0;i<answers.size();i++){
                if(givenAnswers.contains(answers.get(i).answer)){
                    //correctAnswers.add(answers.get(i).answer);
                    score++;
                }
            }
        }else{

            if(givenAnswers.contains(answers.get(0).answer)) {
                //correctAnswers.add(answers.get(0).answer);
                score++;
            }
        }
        return score;
    }
    public static boolean checkAnswerBool(int questionId,ArrayList<String> givenAnswers){
        DBConn con = new DBConn();
        Question quest = con.getQuestion(questionId);
        List<Answer> answers = con.getAnswers(questionId,true);
        ArrayList<String> correctAnswers= new ArrayList<>();
        double score = answers.size();
        if(quest.isMultiAnswerType()){
            for (Answer answer : answers) {
                if (!givenAnswers.contains(answer.answer)) {
                    return false;
                }
            }
        }else{

            return givenAnswers.contains(answers.get(0).answer);
        }
        return true;
    }
}
