package Quiz.src.main.java.servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import Quiz.src.main.java.models.*;
import Quiz.src.main.java.models.enums.*;

import static Quiz.src.main.java.HelperMethods.PassHasher.hashPassword;


@WebServlet(name = "quizResult", value = "/quizResult")
public class testeeer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.size();
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException, IOException {
        System.out.println("mze");
    }
}
