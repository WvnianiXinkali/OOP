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
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    body {
        font-family: Courier, monospace;
    }
    .header-container {

        background-color: #087cfc;
        justify-content: space-between;
        align-items: center;
        padding: 10px;
        display: flex;
        color: white;
    }
    .header{
        font-size: 20px;
        flex: 1;
        color: white;
        font-weight: bold;
    }



    .submit_button {
        background-color: #087cfc;
        border: none;
        margin-top: 10px;
        padding: 10px 15px;
        color: white;
        cursor: pointer;
        font-weight: bold;
        border-radius: 5px;
    }
    .end_button{
        background-color: #ff0000;
        border: none;
        margin-top: 10px;
        padding: 10px 15px;
        color: white;
        cursor: pointer;
        border-radius: 5px;
        font-weight: bold;
        flex: 5;
        text-align: center;
    }
    .success-button{
        background-color: #4caf50;
        border: none;
        margin-top: 10px;
        padding: 10px 15px;
        color: white;
        cursor: pointer;
        font-weight: bold;
        border-radius: 5px;
        margin-top: 10px;
        text-align: center;
    }
    .end-message {
        margin-top: 20px;
        padding: 10px;
        font-weight: bold;
        background-color: #4caf50;
        color: white;
        text-align: center;
        border-radius: 5px;
    }
    .end-button-container {
        display: flex;
        align-items: center;
    }
</style>
<html>
<style>

</style>
<body>
<div class="header-container">
    <header class="header">
        Quiz Practice
    </header>
    <div class ="end-button-container">
        <form action="/quizPractice?id=<%= request.getParameter("id") %>"method="POST">
            <input class="end_button" type="submit" name = "action" value="End Practice">
        </form>
    </div>
</div>

<%
    HttpSession ses = request.getSession();
    int quizID = Integer.parseInt(request.getParameter("id"));
    LinkedHashMap<Question,Integer> questions = (LinkedHashMap) ses.getAttribute("practiceSession"+quizID);
    if(questions.size()==0){
%>
<p class="end-message">You have successfully ended the practice session. You are now ready to take the quiz.</p>
<form action="<%="/quizSummary.jsp?id="+quizID%>" method="POST">
    <div class="redirect-button">
        <input class="success-button" type="submit" value="Go to Quiz Summary"></input>
    </div>
</form>
<%

    }else{
%>

<form action="/quizPractice?id=<%= request.getParameter("id") %>" method="POST">
    <%

        DBConn con = new DBConn();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/MainPageServlet");
            return;
        }

        int i=0;
        for (Map.Entry<Question, Integer> entry : questions.entrySet()) {
            Question question = entry.getKey();
            QuestionType questionType = question.type;
    %>

    <% if (questionType == QuestionType.QUESTION_RESPONSE) { %>
    <p>Question <%= i + 1 %>: <%= question.question %></p>
    <input type="text" name=<%="question"+i %>>

    <% } else if (questionType == QuestionType.FILL_IN_THE_BLANK) { %>
    <p>Question <%= i + 1 %>: <%= question.question %></p>
    <%
        ArrayList < Answer> answers = con.getAnswers(question.id, false);
        for (int j = 0; j < answers.size(); j++) {
    %>
    <input type="text" name=<%="question"+i+"_"+j%>>

    <% } } else if (questionType == QuestionType.MULTIPLE_CHOICE) { %>
    <p>Question <%= i + 1 %>: <%= question.question %></p>
    <%
        ArrayList < Answer> answers = con.getAnswers(question.id, false);
        for (int j = 0; j < answers.size(); j++) {
    %>
    <input type="radio" name=<%="question"+i%> value="<%=answers.get(j).answer%>"> <%=answers.get(j).answer%><br>

    <% } } else if (questionType == QuestionType.PICTURE_RESPONSE) { %>
    <p>Question <%= i + 1 %>:</p>
    <img src=<%=question.question%>  width="300" height="200" style="border: 2px solid black;">
    <input type="text" name=<%="question"+i%>>

    <% } else if (questionType == QuestionType.MULTI_ANSWER) { %>
    <p>Question <%= i + 1 %>: <%= question.question %></p>
    <%
        ArrayList < Answer> answers = con.getAnswers(question.id, false);
        for (int j = 0; j < answers.size(); j++) {
    %>
    <input type="text" name=<%="question"+i+"_"+j%>>

    <% } } else if (questionType == QuestionType.MULTI_AN_CHOICE) { %>
    <p>Question <%= i + 1 %>: <%= question.question %></p>
    <%
        ArrayList < Answer> answers = con.getAnswers(question.id, false);
        for (int j = 0; j < answers.size(); j++) {
    %>
    <input type="checkbox" name=<%="question"+i+"_"+j%> value="<%= answers.get(j).answer %>"> <%= answers.get(j).answer %><br>

    <% } } i++; } %>

    <div>
        <input class="submit_button" type="submit" value="Submit">

    </div>

</form>
<%}%>
</body>
</html>



