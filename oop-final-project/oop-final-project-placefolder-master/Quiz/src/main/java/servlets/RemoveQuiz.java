package Quiz.src.main.java.servlets;

        import Quiz.src.main.java.models.DBConn;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

@WebServlet("/RemoveQuiz")
public class RemoveQuiz extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Remove Quiz");

        String Quizid = request.getParameter("quizid");

        int quizid = Integer.parseInt(Quizid);


        DBConn dbConn = new DBConn();

        dbConn.removeQuiz(quizid);

        dbConn.closeDBConn();


        response.sendRedirect("home.jsp");

    }
}

