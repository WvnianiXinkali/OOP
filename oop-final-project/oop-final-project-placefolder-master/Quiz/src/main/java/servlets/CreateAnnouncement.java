package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.Announcement;
import Quiz.src.main.java.models.DBConn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateAnnouncement")
public class CreateAnnouncement extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Create Announcement");

        String text = request.getParameter("createann");

        DBConn dbConn = new DBConn();

        dbConn.insertAnnouncement(new Announcement(1, text));

        dbConn.closeDBConn();

        response.sendRedirect("userProfile.jsp");
    }
}
