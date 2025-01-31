package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;

@WebServlet("/deleteRateAndReview")
public class deleteRateAndReview extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("deleteRateAndReview");

        String ReviewId = request.getParameter("reviewId");

        String quizId = request.getParameter("quizId");

        int reviewId = Integer.parseInt(ReviewId);


        DBConn dbConn = new DBConn();

        dbConn.removeRateAndReview(reviewId);

        dbConn.closeDBConn();
        response.sendRedirect("quizSummary.jsp?id=" + quizId);
    }
}
