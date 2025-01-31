package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

@WebServlet("/changeRatingServlet")
public class changeRatingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("changeRatingServlet");

        String ReviewId = request.getParameter("reviewId");

        String EditedRating = request.getParameter("editedRating");

        String quizId = request.getParameter("quizId");

        int reviewId = Integer.parseInt(ReviewId);

        int editedRating = Integer.parseInt(EditedRating);

        DBConn dbConn = new DBConn();

        dbConn.updateRateAndReview(new rateAndReview(reviewId, 1, 1,editedRating, dbConn.getRateAndReviewByID(reviewId).get(0).review));

        dbConn.closeDBConn();
        response.sendRedirect("quizSummary.jsp?id=" + quizId);
    }
}