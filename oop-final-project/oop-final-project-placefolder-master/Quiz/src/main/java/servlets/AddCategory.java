package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.Categorya;
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

@WebServlet("/AddCategory")
public class AddCategory extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Add Category Servlet");
        String category = request.getParameter("add_text");

        DBConn dbConn = new DBConn();

        dbConn.insertCategory(new Categorya(1, category));

        dbConn.closeDBConn();
        response.sendRedirect("home.jsp");
    }
}

