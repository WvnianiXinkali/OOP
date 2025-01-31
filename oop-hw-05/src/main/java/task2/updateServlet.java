package task2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "updateServlet", value = "/updateServlet")
public class updateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        StoreController cont = (StoreController) getServletContext().getAttribute("control");
        cart cart1 = (cart) httpServletRequest.getSession().getAttribute("cartt");
        Enumeration<String> itemss = httpServletRequest.getParameterNames();
        while(itemss.hasMoreElements()){
            String idi = itemss.nextElement();
            if(Integer.parseInt(httpServletRequest.getParameter(idi)) == 0){
                cart1.removeItem(idi);
            } else {
                cart1.makeItem(idi, Integer.parseInt(httpServletRequest.getParameter(idi)));
            }
        }
        httpServletRequest.getRequestDispatcher("cart.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
