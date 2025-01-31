package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.User;
import Quiz.src.main.java.models.rateAndReview;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SaveReview")
public class SaveReview extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String QuizId = request.getParameter("quizId");
        String UserId = request.getParameter("userId");
        String Rating = request.getParameter("rating");
        String Review = request.getParameter("review");


        int quizId = Integer.parseInt(QuizId);
        int userId = Integer.parseInt(UserId);
        int rating = Integer.parseInt(Rating);

        DBConn dbConn = new DBConn();


        dbConn.insertRateAndReview(new rateAndReview(1, quizId, userId, rating, Review));

        String redirectUrl = String.format("./quizResults.jsp?id=%d", quizId);

        response.sendRedirect(redirectUrl);
    }
}

