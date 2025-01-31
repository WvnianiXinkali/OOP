package Quiz.src.main.java.servlets;
import Quiz.src.main.java.models.Answer;
import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet("/ProcessAnswers")
public class ProcessAnswers extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int quizId = Integer.parseInt(req.getParameter("id"));
        String formatPattern = "yyyy-MM-dd HH:mm:ss";
        HttpSession ses = req.getSession();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

        LocalTime startTime = (LocalTime) ses.getAttribute("quizStartTime");

        LocalTime endTime = LocalTime.now();

        Duration minutes = Duration.between(startTime, endTime);


        ses.setAttribute("time", minutes);

        resp.sendRedirect("/quizResults.jsp?id=" + quizId);
    }
}