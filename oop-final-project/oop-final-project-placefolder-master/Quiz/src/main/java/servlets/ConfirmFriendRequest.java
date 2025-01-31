package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Friend;
import Quiz.src.main.java.models.Notification;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ConfirmFriendRequest")
public class ConfirmFriendRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Confirm Friend Request Servlet");
        response.setContentType("text/html");

        String UserId = request.getParameter("userId");
        String TargetId = request.getParameter("targetId");

        int targetId = Integer.parseInt(TargetId);
        int userId = Integer.parseInt(UserId);

        DBConn dbConn = new DBConn();

        Friend friend1 = new Friend(-1, targetId, userId);
        Friend friend2 = new Friend(-1, userId, targetId);
        dbConn.insertFriend(friend1);
        dbConn.insertFriend(friend2);

        ArrayList<Notification> notifs = dbConn.getNotifications(targetId, userId, "friend request");
        Notification notif = notifs.get(0);
        Notification newNotif = new Notification(notif.getId(), notif.getReceiverId(), notif.getSenderId(), "confirmed", "Friend Request Confirmed");
        dbConn.updateNotification(newNotif);

        dbConn.closeDBConn();

        String redirectUrl = String.format("./userProfile.jsp?id=%d", targetId);
        response.sendRedirect(redirectUrl);
    }
}
