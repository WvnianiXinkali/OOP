package task1;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Task1Servlet", value = "/Task1Servlet")
public class Task1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException, IOException {
        AccountManager acc = (AccountManager) getServletContext().getAttribute("manacc");
        String name = httpServletRequest.getParameter("username");
        if(name != null) {
            if (acc.containsMap(name)) {
                String password = httpServletRequest.getParameter("password");
                if (acc.checkPass(name, password)) {
                    httpServletRequest.getRequestDispatcher("logged.jsp").forward(httpServletRequest, httpServletResponse);
                } else {
                    httpServletRequest.getRequestDispatcher("failedlogg.jsp").forward(httpServletRequest, httpServletResponse);
                }
            } else {
                httpServletRequest.getRequestDispatcher("failedlogg.jsp").forward(httpServletRequest, httpServletResponse);
            }
        }
    }
}
