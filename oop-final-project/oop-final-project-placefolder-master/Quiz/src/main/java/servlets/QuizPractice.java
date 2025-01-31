package Quiz.src.main.java.servlets;

import Quiz.src.main.java.HelperMethods.AnswerChecker;
import Quiz.src.main.java.models.Answer;
import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet("/quizPractice")
public class QuizPractice extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession ses = req.getSession();
        int quizId = Integer.parseInt(req.getParameter("id"));
        String act = req.getParameter("action");
        if ("End Practice".equals(act)){
            ses.setAttribute("practiceSession"+quizId,null);
            RequestDispatcher dispatcher = req.getRequestDispatcher(String.format("/quizSummary.jsp?id=%d",quizId));
            dispatcher.forward(req, resp);

        }else{
            DBConn con = new DBConn();

            LinkedHashMap<Question,Integer> quests = (LinkedHashMap) ses.getAttribute("practiceSession"+quizId);
            int i =0;

            ArrayList <Question> toRemove = new ArrayList<>();
            for (Map.Entry<Question, Integer> entry : quests.entrySet()) {

                Question quest = entry.getKey();
                if (quest.isMultiAnswerType()) {
                    ArrayList<String> answ = new ArrayList<>();
                    ArrayList<Answer> corrects = con.getAnswers(quest.id,true);
                    for(int j=0;j<corrects.size();j++){
                        answ.add(req.getParameter(String.format("question%d_%d",i,j)));
                    }

                    boolean checked = AnswerChecker.checkAnswerBool(quest.id,answ);
                    if(checked){
                        if(entry.getValue()==2){
                            toRemove.add(quest);
                            System.out.println("kera");
                        }else{
                            entry.setValue(entry.getValue()+1);
                        }
                    }else{
                        entry.setValue(0);
                    }

                }else {

                    String userAnswer = req.getParameter("question"+i);
                    ArrayList<String> answ = new ArrayList<>();
                    answ.add(userAnswer);

                    boolean checked = AnswerChecker.checkAnswerBool(quest.id,answ);
                    if(checked){
                        if(entry.getValue()==2){
                            toRemove.add(quest);
                        }else{
                            entry.setValue(entry.getValue()+1);
                        }
                    }else{
                        entry.setValue(0);
                    }

                }
                i++;
            }
            for (Question question : toRemove) {
                quests.remove(question);
            }
            ses.setAttribute("practiceSession"+quizId,quests);
            long tm = System.currentTimeMillis();
            RequestDispatcher dispatcher = req.getRequestDispatcher(String.format("/practice.jsp?id=%d&timestamp=%d",quizId,tm));
            dispatcher.forward(req, resp);
        }

    }


}
