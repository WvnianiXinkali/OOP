package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import static Quiz.src.main.java.HelperMethods.PassHasher.hashPassword;

@WebServlet(name = "creationServlet", value = "/creationServlet")
public class CreationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

    }



    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        DBConn acc = (DBConn) getServletContext().getAttribute("manacc");

        String name = httpServletRequest.getParameter("username");
        String pass = httpServletRequest.getParameter("password");

        pass = hashPassword(pass);

        if(!acc.getUsersByUsername(name).isEmpty()){
            httpServletRequest.getRequestDispatcher("alreadycreated.jsp").forward(httpServletRequest, httpServletResponse);
        } else{
            acc.insertUser(new User(1, name, pass, "user", false));
            User user = acc.getUsersByUsername(name).get(0);
            HttpSession session = httpServletRequest.getSession();

            session.setAttribute("loggedIn", true);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());

            Cookie sessionCookie = new Cookie("username", name);
            sessionCookie.setMaxAge(Integer.MAX_VALUE);
            httpServletResponse.addCookie(sessionCookie);
            httpServletRequest.getRequestDispatcher("home.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }
}
