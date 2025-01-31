package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Notification;
import Quiz.src.main.java.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AddFriend")
public class AddFriend extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Add Friend Servlet");
        response.setContentType("text/html");
        String UserId = request.getParameter("userId");
        String TargetId = request.getParameter("targetId");
        String addOrRemovePar = request.getParameter("removeFriend");

        int targetId = Integer.parseInt(TargetId);
        int userId = Integer.parseInt(UserId);

        System.out.println(userId);
        System.out.println(targetId);

        DBConn dbConn = new DBConn();

        if(addOrRemovePar.equals("remove")){
            dbConn.removeFriend(userId, targetId);
            dbConn.closeDBConn();

            String redirectUrl = String.format("./userProfile.jsp?id=%d&addfriendtext=%s", targetId, "Friend Removed");
            response.sendRedirect(redirectUrl);

            return;
        }

        ArrayList<Notification> notifications = dbConn.getNotifications(targetId, userId, "friend request");

        if(notifications.size() > 0){
            dbConn.closeDBConn();
            String redirectUrl = String.format("./userProfile.jsp?id=%d&addfriendtext=%s", targetId, "Request Already Sent");
            response.sendRedirect(redirectUrl);

            return;
        }

        ArrayList<User> users = dbConn.getUsers(userId);
        User user = users.get(0);
        String userName = user.getUsername();
        String confirmLink = String.format("<a href=\"./ConfirmFriendRequest?userId=%d&targetId=%d\">Confirm</a>", userId, targetId);

        Notification notification2 = new Notification(-1, targetId, userId, "friend request", String.format("%s has sent you a friend request! Click here to %s", userName, confirmLink));
        dbConn.insertNotification(notification2);

        dbConn.closeDBConn();

        String redirectUrl = String.format("./userProfile.jsp?id=%d&addfriendtext=%s", targetId, "Request Sent");
        response.sendRedirect(redirectUrl);
    }
}
