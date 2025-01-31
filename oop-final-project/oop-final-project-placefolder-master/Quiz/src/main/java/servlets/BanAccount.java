package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.User;
import Quiz.src.main.java.models.UserBan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet("/BanUser")
public class BanAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Ban User");

        //String UserId = request.getParameter("userId");
        String TargetId = request.getParameter("targetId");
        String daystoban = request.getParameter("banDays");

        int targetId = Integer.parseInt(TargetId);
        //int userId = Integer.parseInt(UserId);


        DBConn dbConn = new DBConn();

        UserBan userban = dbConn.getUserBan(targetId);

        if(userban == null) {
            int Daystoban = Integer.parseInt(daystoban);
            dbConn.insertUserBan(new UserBan(1, targetId, LocalDateTime.now(), Daystoban));
        } else {
            dbConn.removeUserBan(userban.user_id);
        }
        dbConn.closeDBConn();

        response.sendRedirect("/userProfile.jsp?id=" + targetId);
    }
}
