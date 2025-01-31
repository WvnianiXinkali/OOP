package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.User;
import Quiz.src.main.java.models.UserBan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MainPageServlet", value = "/MainPageServlet")
public class StartMainServlet extends HttpServlet {
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        System.out.println("Startmain servlet");
        HttpSession session = httpServletRequest.getSession(false);

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();
                    if (username != null) {
                        DBConn acc = (DBConn) httpServletRequest.getServletContext().getAttribute("manacc");
                        ArrayList<User> users = acc.getUsersByUsername(username);
                        System.out.println(users.size() + "saiziaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        if(users.isEmpty()) {
                            session.setAttribute("user", null);
                            httpServletRequest.getRequestDispatcher("/loginPage.jsp").forward(httpServletRequest, httpServletResponse);
                            return;
                        }
                        session.setAttribute("user", users.get(0));
                    }
                    break;
                }
            }
        }

        if (session == null || session.getAttribute("user") == null) {
            httpServletRequest.getRequestDispatcher("/nreg.jsp").forward(httpServletRequest, httpServletResponse);
            return;
        }

        DBConn acc = (DBConn) httpServletRequest.getServletContext().getAttribute("manacc");
        User user = (User) session.getAttribute("user");

        UserBan userban1 = acc.getUserBan(user.getId());

        if (userban1 != null) {
            if(userban1.userStillBanned()){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/LogoutServlet");
                return;
            } else {
                acc.removeUserBan(user.getId());
            }
        }


        httpServletRequest.getRequestDispatcher("/home.jsp").forward(httpServletRequest, httpServletResponse);
    }

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

    }
}
