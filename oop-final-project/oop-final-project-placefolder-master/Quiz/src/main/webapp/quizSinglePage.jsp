<%@ page import="Quiz.src.main.java.models.Question" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.models.enums.QuestionType" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="Quiz.src.main.java.models.Answer" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.HelperMethods.AnswerChecker" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz</title>

    <style>
        .header {
            background-color: #087cfc;
        }

        header {
            color: white;
            font-size: 24px;
            font-weight: bold;
        }
    </style>
</head>
<style>
    .submit_button{
        background-color: #087cfc;
        border: none;
        margin-top: 10px;
        padding: 10px 15px;

        color: white;
        cursor: pointer;
        border-radius: 5px;
    }
    .normal{
        background-color : white;
    }
    .redOne{
        background-color : red;
    }
    .greenOne{
        background-color : green;
    }
</style>

<%

%>

<body>
<div class="header">
    <header style="color: white">
        Quiz
    </header>
</div>

<%
    int quizID = Integer.parseInt(request.getParameter("id"));
    User user1 = (User) session.getAttribute("user");
    if (user1 == null) {
       response.sendRedirect(request.getContextPath() + "/MainPageServlet");
       return;
    }
    HttpSession ses = request.getSession();
    Integer iterator = (Integer) ses.getAttribute("iterator"+quizID+"_"+user1.getId());



    //boolean correction  = Boolean.valueOf(request.getParameter("correction"));
    boolean correction  = Boolean.parseBoolean(request.getParameter("correction"));

    DBConn con = new DBConn();
    Quiz quiz = con.getQuiz(quizID);
    ArrayList<Question> questions = (ArrayList) ses.getAttribute("shuffledQuestions"+quizID+"_"+user1.getId());


    if(!quiz.rand_question_order){
        questions = con.getQuestions(quizID);
    }

    if(questions == null){
        questions = con.getQuestions(quizID);
        if(quiz.rand_question_order){
            Collections.shuffle(questions);
        }
        ses.setAttribute("shuffledQuestions"+quizID+"_"+user1.getId(),questions);
    } else {
        boolean b = false;
        for(int i = 0; i < questions.size(); i++){
            if(con.getQuestion(questions.get(i).id) == null){
                b = true;
                break;
            }
        }
        if(b){
            questions = con.getQuestions(quizID);
            if(quiz.rand_question_order){
                Collections.shuffle(questions);
            }
            ses.setAttribute("shuffledQuestions"+quizID+"_"+user1.getId(),questions);
        }
        ses.setAttribute("shuffledQuestions"+quizID+"_"+user1.getId(),questions);
    }

    if(iterator == null) {
        LocalTime quizStartTime = LocalTime.now();
        ses.setAttribute("quizStartTime"+quizID+"_"+user1.getId(), quizStartTime);
        iterator = 0;
    }else{
        Question quest = questions.get(iterator);
        if(quest.isMultiAnswerType()){

            ArrayList<Answer> arr = con.getAnswers(quest.id,false);
            ArrayList<String> tmp =new ArrayList<String>();

            for(int i=0;i<arr.size();i++){
                String key = String.format("question%d_%d",iterator,i);
                tmp.add(request.getParameter(key));

            }
            if(!((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue())
            ses.setAttribute("question"+iterator+"_"+quizID+"_"+user1.getId(),tmp);
        }else{

            ArrayList<String> tmp =new ArrayList<String>();
            tmp.add(request.getParameter("question"+iterator));
            if(!((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue())
            ses.setAttribute("question"+iterator+"_"+quizID+"_"+user1.getId(),tmp);
        }
    }

    String action = request.getParameter("action");
    if ("Next".equals(action)) {
        iterator++;
    } else if ("Prev".equals(action)) {
        iterator--;
    }else if ("submit".equals(action)) {
        ArrayList<String> responseAnswer =(ArrayList<String>) ses.getAttribute("question"+iterator+"_"+quizID+"_"+user1.getId());
        ArrayList<String> userCorrectAnswers = AnswerChecker.checkAnswer(questions.get(iterator).id,responseAnswer);
        session.setAttribute("daechira" + iterator + "_" + quizID + "_" + user1.getId(), true);
        //ArrayList<Answer> answers = con.getAnswers(question.id,true);
        //here do the work

    }else if("End Quiz".equals(action)){

        ArrayList<Integer> iyo = (ArrayList<Integer>) session.getAttribute("xulignobs"+quizID+"_"+user1.getId());
          if (iyo != null) {
              if (user1.getId() == iyo.get(1) && quizID == iyo.get(2) && iyo.get(0) == 1) {
                  response.sendRedirect(request.getContextPath() + "/quizResults.jsp?id=" + quizID);
                  return;
              }
          }

        ses.setAttribute("iterator"+quizID+"_"+user1.getId(),null);
        response.sendRedirect("quizResults.jsp?id=" + request.getParameter("id"));
        }else if( action != null){
        try {
            int selected = Integer.parseInt(action);
            iterator = selected - 1;
        } catch (NumberFormatException e) {
            %> <h1> erroooooooor while parsing request</h1> <%
        }
    }
    ses.setAttribute("iterator"+quizID+"_"+user1.getId(), iterator);

    if (iterator >= 0 && iterator < questions.size()) {
        Question question = questions.get(iterator);
        QuestionType questionType = question.type;
%>

<form action=<%="quizSinglePage.jsp?id="+quizID+(correction?"&&correction=true":"")%> method="post">
    <p>Question <%= iterator + 1 %>: <%= questionType == QuestionType.PICTURE_RESPONSE?"":question.question %></p>

    <% if (questionType == QuestionType.QUESTION_RESPONSE) {
        ArrayList<String> answ = (ArrayList) ses.getAttribute("question"+iterator+"_"+quizID+"_"+user1.getId());
        String v = "";
        if(answ != null) {
             v = answ.get(0) == null ? "" : answ.get(0);
        }
        String className = "normal";
        boolean questionCompleted = ((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue();
        if(questionCompleted && correction){
            className = AnswerChecker.checkAnswerByString(question.id, v, false, 1) ?
                        "greenOne" : "redOne";
        }
    %>
    <input type="text" name=<%="question"+iterator%>  value="<%=v%>" class="<%=className%>">

    <% } else if (questionType == QuestionType.FILL_IN_THE_BLANK) {
        ArrayList<Answer> answers = con.getAnswers(question.id,true);
        ArrayList<String> definedAnswers = (ArrayList) ses.getAttribute("question"+iterator+"_"+quizID+"_"+user1.getId());
        ArrayList<Boolean> answerArray = AnswerChecker.checkAnswerByStringForMulties(question.id, definedAnswers);

        for (int j = 0; j < answers.size(); j++) {
            String v = "";
            if(definedAnswers != null) {

                v = definedAnswers.get(j) == null ? "" : definedAnswers.get(j);

            }
            String className = "normal";
            boolean questionCompleted = ((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue();
            if(questionCompleted && correction){
                className = (answerArray.get(j)) ?
                            "greenOne" : "redOne";
            } %>
    <input type="text" name=<%=String.format("question%d_%d",iterator,j)%> value="<%=v%>"class="<%=className%>">
    <%
        }
    %>

    <% } else if (questionType == QuestionType.MULTIPLE_CHOICE) {
        ArrayList<Answer> answers = con.getAnswers(question.id,false);
        ArrayList<String> selected = (ArrayList<String>) ses.getAttribute("question" +iterator+"_"+quizID+"_"+user1.getId());
        String selectedAnswer = selected==null ?"":selected.get(0);
        for (int j = 0; j < answers.size(); j++) {

            boolean isSelected = answers.get(j).answer.equals(selectedAnswer);
            String className = "normal";

            boolean questionCompleted = ((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue();
            if(questionCompleted && correction && isSelected){
                className = AnswerChecker.checkAnswerByString(question.id, selectedAnswer, false, 1) ?
                            "greenOne" : "redOne";
            }
    %>
    <input type="radio" name=<%="question"+iterator%> value="<%= answers.get(j).answer %>" <%= isSelected ? "checked" : "" %> class="<%=className%>">
    <%= answers.get(j).answer %><br>
    <%
        }
    %>

    <% } else if (questionType == QuestionType.PICTURE_RESPONSE) {
        ArrayList<String> answ = (ArrayList) ses.getAttribute("question"+iterator+"_"+quizID+"_"+user1.getId());
        String v = "";
        if(answ != null) {
            v = answ.get(0) == null ? "" : answ.get(0);
        }
        String className = "normal";
        boolean questionCompleted = ((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue();
        if(questionCompleted && correction){
            className = AnswerChecker.checkAnswerByString(question.id, v, false, 1) ?
                        "greenOne" : "redOne";
        }
        %>

    <img src=<%=question.question%>  width="300" height="200" style="border: 2px solid black;">
    <br>
    <input type="text" name=<%="question"+iterator%> value="<%=v%>"class="<%=className%>">

    <% } else if (questionType == QuestionType.MULTI_ANSWER) {


        ArrayList<Answer> answers = con.getAnswers(question.id,true);
        ArrayList<String> definedAnswers = (ArrayList) ses.getAttribute("question"+iterator+"_"+quizID+"_"+user1.getId());
        for (int j = 0; j < answers.size(); j++) {
            if(definedAnswers!=null && definedAnswers.get(j) == null) {
                definedAnswers.set(j, "");
            }
        }
        ArrayList<Boolean> sworebi = AnswerChecker.checkAnswerByStringForMulties(question.id, definedAnswers);

        for (int j = 0; j < answers.size(); j++) {
            String v = "";
            if(definedAnswers != null) {

                v = definedAnswers.get(j) == null ? "" : definedAnswers.get(j);

            }

    String className = "normal";
    boolean questionCompleted = ((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue();
    if(questionCompleted && correction){
        className = sworebi.get(j) ?
                    "greenOne" : "redOne";
    }%>
    <input type="text" name=<%=String.format("question%d_%d",iterator,j)%> value="<%=v%>" class="<%= className%>">
    <%
        }
    %>
        <% } else if (questionType == QuestionType.MULTI_AN_CHOICE) { %>
        <%
            ArrayList<String> selected = (ArrayList<String>) ses.getAttribute("question" + iterator + "_" + quizID + "_" + user1.getId());
            ArrayList<Answer> answers = con.getAnswers(question.id, false);
            ArrayList<Boolean> isAnswerCorrectList = AnswerChecker.checkAnswerByStringForMulties(question.id, selected);

            for (int j = 0; j < answers.size(); j++) {
            boolean isSelected = selected != null && selected.contains(answers.get(j).answer);

            String className = "normal";
            boolean questionCompleted = ((Boolean) session.getAttribute("daechira" + iterator + "_" + quizID + "_" + user1.getId())).booleanValue();
            if (questionCompleted && correction) {
                className = isAnswerCorrectList.get(j) ?
                            "greenOne" : "redOne";
            }
        %>
        <label class="<%=className%>">
        <input type="checkbox" name=<%="question"+iterator+"_"+j%> value="<%= answers.get(j).answer %>" <%= isSelected ? "checked" : "" %> class="<%=className%>">
        </label>
        <%= answers.get(j).answer %><br>
        <%
            }
        %>
        <% } %>


    <div>
        <%
            if (iterator != 0){
        %>
        <input class="submit_button" type="submit" name="action" value="Prev">
        <% } %>
        <%
            if (iterator != questions.size()-1){
        %>
        <input class="submit_button" type="submit" name="action" value="Next">
        <% } %>
        <% if (correction && !((Boolean) session.getAttribute("daechira" + iterator +"_"+ quizID + "_" + user1.getId())).booleanValue()) { %>
        <input class="submit_button" type="submit" name="action" value="submit">
        <%}%>
        <input class="submit_button" type="submit" name="action" value="End Quiz">
    </div>
    <div>
        <%
            for(int i=0;i<questions.size();i++){ %>
        <input class="submit_button" type="submit" name="action" value=<%=Integer.toString(i+1)%>>
        <%
            }
        %>
    </div>

</form>
<%
} else {
        ses.setAttribute("iterator"+quizID+"_"+user1.getId(),null);
%>

<p>Quiz completed.</p>
<%
    }
%>
</body>
</html>