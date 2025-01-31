package task2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "cartServlet", value = "/cartServlet")
public class cartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        StoreController cont = (StoreController) getServletContext().getAttribute("control");
        cart cart1 = (cart) httpServletRequest.getSession().getAttribute("cartt");
        cart1.incItem(httpServletRequest.getParameter("id"), 1);
        httpServletRequest.getRequestDispatcher("cart.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
