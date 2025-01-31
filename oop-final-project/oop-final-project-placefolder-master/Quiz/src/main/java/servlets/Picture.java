package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Picture")
public class Picture extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Picture Change");

        String UserId = request.getParameter("userId");

        int userId = Integer.parseInt(UserId);

        String picUrl = request.getParameter("Picture");

        DBConn dbConn = new DBConn();

        dbConn.updateUserPicture(userId, picUrl);

        dbConn.closeDBConn();


        response.sendRedirect("userProfile.jsp");

    }
}


