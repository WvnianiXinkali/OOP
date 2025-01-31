<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Quiz.src.main.java.models.Achievement" %>
<%@ page import="Quiz.src.main.java.models.User" %>
<%@ page import="Quiz.src.main.java.models.Notification" %>
<%@ page import="Quiz.src.main.java.models.QuizHistory" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.models.Categorya" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
  <title>Create Quiz</title>
  <link rel="stylesheet" href="styles.css">
</head>

<style>

.back-button {
    display: inline-block;
    background-color: #007bff;
    color: white;
    padding: 30px 60px
    text-decoration: none;
    font-size: 18px;
    font-weight: bold;
    border-radius: 5px;
    margin-top: 10px;
}

.back-button:hover {
    background-color: #0056b3;
}
</style>
<body>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/loginPage.jsp");
            return;
        }
        int userId= user.getId();
    %>
    <div class="block-container">
        <div class="block-contents">
            <div id="create_manually" class="block-title">Create manually</div>
            <form action="/CreateQuiz" method="get" id="quiz-contents">
                <input name="userId" type="hidden" value="<%= userId%>"></input>
                <input name="quiz_name" class="note_text" placeholder="Quiz name" type="text"></input>
                <select name="quiz_category" id="quiz_category" class="question-form">
                    <option value="0">No Category</option>
                    <%
                        DBConn dbconn = new DBConn();
                        ArrayList<Categorya> categories = dbconn.getCategories();
                        for(Categorya category : categories){
                            %><option value="<%= category.id%>"><%= category.category %></option><%
                        }
                        dbconn.closeDBConn();
                    %>
                </select>
                <br>
                <textarea name="description" class="note_text" placeholder="Quiz description" rows="4" cols="50"></textarea>
                <br>
                <ul id="quizTags" class="question-form">
                </ul>
                <input id="quiz_new_tag" class="note_text" placeholder="Tag" type="text"></input>
                <button class="action-button" id="addQuizTag">Add Tag</button>
                <input name="quiz_tag_max_index" id="quiz_tag_max_index" type="hidden" value="0"></input>
                <br>
                <input type="checkbox" name="isSinglePageCB" value="1">
                <label for="isSinglePageCB"> Is single page</label>
                <br>
                <input type="checkbox" name="canBePracticedCB" value="1">
                <label for="canBePracticedCB"> Can be practiced</label>
                <br>
                <input type="checkbox" name="randQuestOrderCB" value="1">
                <label for="randQuestOrderCB"> Randomize quesiton order</label>
                <br>
                <button class="action-button" id="addQuestion">Add Question</button>
                <br>
                <input class="action-button" type="submit">
                <input type="hidden" id="maxQuestionIndex" name="maxQuestionIndex" value="0">
            </form>
        </div>
        <div>
        <a class="back-button" href="<%= request.getContextPath() %>/userProfile.jsp?id=<%= userId %>">Profile</a>
        </div>
    </div>
    <div class="block-container">
        <div class="block-contents">
            <div class="block-title">Create from json file</div>
            <br>
            <form action="/CreateQuizJson" method="post" enctype="multipart/form-data">
                <input class="action-button" type="file" name="jsonFile">
                <br>
                <input class="action-button" type="submit">
                <input name="userId" type="hidden" value="<%= userId%>"></input>
            </form>
            <br>
            <div class="block-items"> sample quiz json:</div>
            <img class="sampleQuiz" src="./sampleQuiz.svg"/>
        </div>
    </div>
    <script src="script.js"></script>
</body>
</html>