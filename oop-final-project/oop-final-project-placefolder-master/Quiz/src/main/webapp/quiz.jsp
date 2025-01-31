<%@ page import="Quiz.src.main.java.models.Question" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.models.enums.QuestionType" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="Quiz.src.main.java.models.Answer" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.lang.Object" %>
<%@ page import="java.time.LocalTime" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    body {
        font-family: Courier, monospace;
    }

    .submit_button {
        background-color: #087cfc;
        border: none;
        margin-top: 10px;
        padding: 10px 15px;
        color: white;
        cursor: pointer;
        border-radius: 5px;
        font-weight: bold;
    }
</style>
<html>
<style>
    .header {
        background-color: #087cfc;
    }

    header {
        color: white;
        font-size: 24px;
    }
</style>
<body>
<div class="header">
    <header style="color: white">
        Quiz
    </header>
</div>

<%

%>

<form action="quizResults.jsp?id=<%= request.getParameter("id") %>" method="POST">
    <%
    int quizID = Integer.parseInt(request.getParameter("id"));
    DBConn con = new DBConn();
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }

    ArrayList<Question> questions = con.getQuestions(quizID);
    Quiz quiz = con.getQuiz(quizID);
    if(quiz == null) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }
    if (quiz.rand_question_order) {
        Collections.shuffle(questions);
    }
    HttpSession ses = request.getSession();

    LocalTime quizStartTime = LocalTime.now();

    ses.setAttribute("quizStartTime"+quizID+"_"+user.getId(), quizStartTime);
    ses.setAttribute("shuffledQuestions"+quizID+"_"+user.getId(), questions);

    for (int i = 0; i < questions.size(); i++) {
        Question question = questions.get(i);
        QuestionType questionType = question.type;
    %>

    <% if (questionType == QuestionType.QUESTION_RESPONSE) { %>
        <p>Question <%= i + 1 %>: <%= question.question %></p>
        <input type="text" name=<%="question"+i %>>

    <% } else if (questionType == QuestionType.FILL_IN_THE_BLANK) { %>
        <p>Question <%= i + 1 %>: <%= question.question %></p>
        <%
        ArrayList < Answer> answers = con.getAnswers(questions.get(i).id, false);
        for (int j = 0; j < answers.size(); j++) {
        %>
        <input type="text" name=<%="question"+i+"_"+j%>>

    <% } } else if (questionType == QuestionType.MULTIPLE_CHOICE) { %>
        <p>Question <%= i + 1 %>: <%= question.question %></p>
        <%
        ArrayList < Answer> answers = con.getAnswers(questions.get(i).id, false);
        for (int j = 0; j < answers.size(); j++) {
        %>
        <input type="radio" name=<%="question"+i%> value="<%=answers.get(j).answer%>"> <%=answers.get(j).answer%><br>

    <% } } else if (questionType == QuestionType.PICTURE_RESPONSE) { %>
        <p>Question <%= i + 1 %>: </p>
        <img src=<%=question.question%>  width="300" height="200" style="border: 2px solid black;">
        <br><br>
        <input type="text" name=<%="question"+i%>>

    <% } else if (questionType == QuestionType.MULTI_ANSWER) { %>
        <p>Question <%= i + 1 %>: <%= question.question %></p>
        <%
        ArrayList < Answer> answers = con.getAnswers(questions.get(i).id, false);
        for (int j = 0; j < answers.size(); j++) {
        %>
        <input type="text" name=<%="question"+i+"_"+j%>>

    <% } } else if (questionType == QuestionType.MULTI_AN_CHOICE) { %>
        <p>Question <%= i + 1 %>: <%= question.question %></p>
        <%
        ArrayList < Answer> answers = con.getAnswers(questions.get(i).id, false);
        for (int j = 0; j < answers.size(); j++) {
        %>
        <input type="checkbox" name=<%="question"+i+"_"+j%> value="<%= answers.get(j).answer %>"> <%= answers.get(j).answer %><br>

    <% } } } %>

    <div>
        <input class="submit_button" type="submit" value="Submit">
    </div>

</form>
</body>
</html>


<% ArrayList<Integer> iyo = (ArrayList<Integer>) session.getAttribute("xulignobs"+quizID+"_"+user.getId());
      if (iyo != null) {
          if (user.getId() == iyo.get(1) && quizID == iyo.get(2) && iyo.get(0) == 1) {
              response.sendRedirect(request.getContextPath() + "/quizResults.jsp?id=" + quizID);
              return;
          }
      }
%>
