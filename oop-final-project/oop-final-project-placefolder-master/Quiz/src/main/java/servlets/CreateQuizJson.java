package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;


@WebServlet("/CreateQuizJson")
@MultipartConfig
public class CreateQuizJson extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Create Quiz Json Servlet");
        response.setContentType("text/html");

        try{
            Part filePart = request.getPart("jsonFile");
            String userId_req = request.getParameter("userId");

            String jsonString = readStringFromInputStream(filePart.getInputStream());
            int creator_id = Integer.parseInt(userId_req);

            ObjectMapper objectMapper = new ObjectMapper();
            JSONQuiz jsonQuiz = objectMapper.readValue(jsonString, JSONQuiz.class);

            DBConn dbConn = new DBConn();
            int quizId = dbConn.getNextQuizId();

            Quiz quiz = new Quiz(quizId, creator_id, jsonQuiz.quiz_name, jsonQuiz.description, jsonQuiz.is_single_page, jsonQuiz.can_be_practiced, jsonQuiz.rand_question_order);
            dbConn.insertQuiz(quiz);

            ArrayList<Question> questionsToInsert = new ArrayList<>();
            ArrayList<Answer> answersToInsert = new ArrayList<>();

            System.out.println(jsonQuiz.quiz_name);


            int questionId = dbConn.getLastQuestionId();
            int questionNum = 0;
            for(JSONQuestion jsonQuestion : jsonQuiz.questions){
                questionNum++;
                questionId++;
                Question question = new Question(-1, quiz.id, jsonQuestion.question, jsonQuestion.question_type, questionNum);
                questionsToInsert.add(question);

                for(JSONAnswer jsonAnswer : jsonQuestion.answers){
                    Answer answer = new Answer(-1, questionId, jsonAnswer.answer, jsonAnswer.is_correct);
                    answersToInsert.add(answer);
                }
            }

            dbConn.updateQuizCategory(quizId, jsonQuiz.category);
            for(Question question : questionsToInsert){
                dbConn.insertQuestion(question);
            }
            for(Answer answer : answersToInsert){
                dbConn.insertAnswer(answer);
            }
            for(String tag_str : jsonQuiz.tags){
                int tag_id = dbConn.getTagId(tag_str);
                if(tag_id == -1){
                    Tag tag = new Tag(-1, tag_str);
                    dbConn.insertTag(tag);
                    tag_id = dbConn.getLastTagId();
                    if(tag_id == -1){
                        continue;
                    }
                }
                TagQuiz tagQuiz = new TagQuiz(-1, tag_id, quiz.id);
                dbConn.insertTagQuiz(tagQuiz);
                System.out.println(tag_str);
            }

            int quizNum = dbConn.getQuizNumCreatedByUser(creator_id);
            if(quizNum == 1){
                UserAchievement userAchievement = new UserAchievement(-1, creator_id, 1);
                dbConn.insertUserAchievement(userAchievement);
            } else if(quizNum == 5){
                UserAchievement userAchievement = new UserAchievement(-1, creator_id, 2);
                dbConn.insertUserAchievement(userAchievement);
            } else if(quizNum == 10){
                UserAchievement userAchievement = new UserAchievement(-1, creator_id, 3);
                dbConn.insertUserAchievement(userAchievement);
            }

            dbConn.closeDBConn();

            response.sendRedirect("./quizSummary.jsp?id=" + quiz.id);
            return;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.sendRedirect("./createQuiz/index.jsp");
            return;
        }

    }
    private String readStringFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}

class JSONAnswer {
    public String answer;
    public boolean is_correct;
}
class JSONQuestion {
    public int question_type;
    public String question;
    public ArrayList<JSONAnswer> answers;
}
class JSONQuiz {
    public int creator_id;
    public String quiz_name;
    public int category;
    public String description;
    public ArrayList<String> tags;
    public boolean is_single_page;
    public boolean can_be_practiced;
    public boolean rand_question_order;
    public ArrayList<JSONQuestion> questions;
}