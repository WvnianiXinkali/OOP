package Quiz.src.main.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        System.out.println("log out servlet");
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.setAttribute("user", null);
            session.setAttribute("username", null);
        }

        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        session.setAttribute("daloginda", true);
        httpServletRequest.getRequestDispatcher("loginPage.jsp").forward(httpServletRequest, httpServletResponse);
    }

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }
}

