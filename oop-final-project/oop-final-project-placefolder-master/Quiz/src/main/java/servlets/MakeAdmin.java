package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.Announcement;
import Quiz.src.main.java.models.DBConn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/MakeAdmin")
public class MakeAdmin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Make admin");

        String UserId = request.getParameter("userId");
        String TargetId = request.getParameter("targetId");

        int targetId = Integer.parseInt(TargetId);
        int userId = Integer.parseInt(UserId);

        DBConn dbConn = new DBConn();

        dbConn.makeUserAdmin(targetId);

        dbConn.closeDBConn();

        response.sendRedirect("userProfile.jsp");
    }
}
