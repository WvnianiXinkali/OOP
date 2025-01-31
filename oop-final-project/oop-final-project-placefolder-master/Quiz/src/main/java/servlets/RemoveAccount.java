package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RemoveAccount")
public class RemoveAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Remove Account");

        String UserId = request.getParameter("userId");
        String TargetId = request.getParameter("targetId");

        int targetId = Integer.parseInt(TargetId);
        int userId = Integer.parseInt(UserId);

        DBConn dbConn = new DBConn();

        dbConn.getUserFriends(targetId).forEach((a) -> dbConn.removeFriend(a.getUser_id(), a.getFriend_id()));
        dbConn.removeUserAchievements(targetId);
        dbConn.getQuizzesByCreator(targetId).forEach((q) -> dbConn.removeQuizHistory(q.id));
        dbConn.getQuizzesByCreator(targetId).forEach((q) ->dbConn.getQuestions(q.id).forEach((question) -> dbConn.removeQuizAnswers(question.id)));
        dbConn.getQuizzesByCreator(targetId).forEach((q) -> dbConn.removeQuizQuestions(q.id));
        dbConn.removeUserQuizes(targetId);
        dbConn.removeUserNotifications(targetId);
        dbConn.removeUser(targetId);
        dbConn.closeDBConn();
        if(targetId == userId) {
            response.sendRedirect("/LogoutServlet");
        } else {
            response.sendRedirect("userProfile.jsp");
        }
    }
}
