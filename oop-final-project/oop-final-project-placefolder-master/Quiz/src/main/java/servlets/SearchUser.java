package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SearchUser")
public class SearchUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Look Up User Servlet");
        response.setContentType("text/html");

        String search_text = request.getParameter("search_text");

        search_text = search_text.toLowerCase(Locale.ROOT);

        System.out.println(search_text);

        DBConn dbConn = new DBConn();

        int userId = 1;

        ArrayList<User> users = dbConn.getUsers(-1);

        for(User u : users){
            if (u.getUsername().toLowerCase(Locale.ROOT).equals(search_text)){
                userId = u.getId();
            }
        }


        String redirectUrl = String.format("./userProfile.jsp?id=%d", userId);

        response.sendRedirect(redirectUrl);
    }
}
