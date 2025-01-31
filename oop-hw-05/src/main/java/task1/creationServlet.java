package task1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "creationServlet", value = "/creationServlet")
public class creationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        AccountManager acc = (AccountManager) getServletContext().getAttribute("manacc");
        String name = httpServletRequest.getParameter("username");
        String pass = httpServletRequest.getParameter("password");
        if(acc.containsMap(name)){
            httpServletRequest.getRequestDispatcher("alreadycreated.jsp").forward(httpServletRequest, httpServletResponse);
        } else{
            acc.addToMap(name, pass);
            httpServletRequest.getRequestDispatcher("logged.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }
}
