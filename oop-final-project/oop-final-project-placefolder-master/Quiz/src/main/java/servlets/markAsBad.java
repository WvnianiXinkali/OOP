package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Notification;
import Quiz.src.main.java.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/markAsBad")
public class markAsBad extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Picture Change");

        String QuizId = request.getParameter("quizid");

        int quizId = Integer.parseInt(QuizId);

        String UserId = request.getParameter("userId");

        int userId = Integer.parseInt(UserId);

        DBConn dbConn = new DBConn();

        ArrayList<User> admins = dbConn.getAllAdmins();

        String noteText = String.format("%s reported: <a href=\"./quizSummary.jsp?id=%d\">%s</a> is an inappropriate quiz.", dbConn.getUsers(userId).get(0).getUsername(), quizId, dbConn.getQuizById(quizId).quiz_name);
        admins.forEach(a -> {Notification notification = new Notification(1, a.getId(), userId, "note",
                noteText); if(dbConn.containsNotification(notification).isEmpty()) dbConn.insertNotification(notification);});

        dbConn.closeDBConn();


       // response.sendRedirect("home.jsp");

        String redirectUrl = String.format("./quizSummary.jsp?id=%d&reporttext=%s", quizId, "Marked as Inappropriate");
        response.sendRedirect(redirectUrl);
    }
}
