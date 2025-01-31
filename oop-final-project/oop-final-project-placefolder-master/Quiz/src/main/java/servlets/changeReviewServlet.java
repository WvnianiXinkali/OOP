package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;

@WebServlet("/changeReviewServlet")
public class changeReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("changeReviewServlet");

        String ReviewId = request.getParameter("reviewId");

        String editedReview = request.getParameter("editedReview");

        String quizId = request.getParameter("quizId");

        int reviewId = Integer.parseInt(ReviewId);


        DBConn dbConn = new DBConn();

        dbConn.updateRateAndReview(new rateAndReview(reviewId, 1, 1, dbConn.getRateAndReviewByID(reviewId).get(0).rating,editedReview));

        dbConn.closeDBConn();
        response.sendRedirect("quizSummary.jsp?id=" + quizId);
    }
}
