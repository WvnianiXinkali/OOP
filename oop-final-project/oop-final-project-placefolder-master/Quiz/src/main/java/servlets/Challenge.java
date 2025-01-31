package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Notification;
import Quiz.src.main.java.models.QuizHistory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Challenge")
public class Challenge extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Send Challenge Servlet");
        response.setContentType("text/html");

        String QuizId = request.getParameter("quizId");
        String UserId = request.getParameter("userId");
        String TargetId = request.getParameter("targetId");

        int targetId = Integer.parseInt(TargetId);
        int userId = Integer.parseInt(UserId);

        if(QuizId == "" || QuizId == null){
            String redirectUrl = String.format("./userProfile.jsp?id=%d&challengetext=%s", targetId, "Incorrect Quiz Id");
            response.sendRedirect(redirectUrl);
            return;
        }

        int quizId = Integer.parseInt(QuizId);

        // check if challenger completed quiz

        // get challenger score

        DBConn dbConn = new DBConn();

        if(dbConn.getQuizById(quizId) == null) {
            String redirectUrl = String.format("./userProfile.jsp?id=%d&challengetext=%s", targetId, "Incorrect Quiz Id");
            response.sendRedirect(redirectUrl);
            return;
        }

        ArrayList<QuizHistory> quizHistories = dbConn.getUserQuizHistory(userId);

        List<QuizHistory> ls = new ArrayList<>(quizHistories.stream().filter(a -> a.getQuiz_id() == quizId).toList());

        String quizLink = String.format("<a href=\"./quizSummary.jsp?id=%d\">%s</a>", quizId, dbConn.getQuizById(quizId).quiz_name);

        String challengeText = !ls.isEmpty() ? String.format("%s has challenged you to %s\n%s got %s on this quiz.", dbConn.getUsers(userId).get(0).getUsername(), quizLink,dbConn.getUsers(userId).get(0).getUsername(),String.format("%.1f", ls.get(0).getScore())):
                String.format("%s has challenged you to %s", dbConn.getUsers(userId).get(0).getUsername(), quizLink);

        System.out.println(challengeText);

        Notification notification = new Notification(-1, targetId, userId, "challenge", challengeText);

        dbConn.insertNotification(notification);

        dbConn.closeDBConn();

        String redirectUrl = String.format("./userProfile.jsp?id=%d&challengetext=%s", targetId, "Challenge Sent");
        response.sendRedirect(redirectUrl);
    }
}
