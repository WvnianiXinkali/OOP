<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">

<html>

<head>
    <title>All Quizzes</title>
</head>

<style>
    .navbar {
        background-color: #007bff;
        color: white;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 20px;
        overflow: hidden;
    }

    button.navbarItem {
        padding: 20px;
        background-color: transparent;
        border: none;
        color: #ffffff;
        cursor: pointer;
        font-size: 20px;
        font-weight: bold;
    }

    .center-text{
        text-align: center;
    }

    .container {
        display: flex;
        flex-wrap: wrap;
        width: 100%;
    }

    .left {
        flex: 2;
        padding: 10px;
    }

    .right {
        flex: 1;
        padding: 10px;
    }

    .list {
        list-style-type: none;
        gap: 20px;
    }

    .button {
        display: block;
        width: 300px;
        background-color: #007bff;
        color: white;
        padding: 8px 16px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
        margin-bottom: 10px;
    }

    .button:hover {
        background-color: #0056b3;
    }

    .button.selected {
        background-color: #3cb371;
    }

    .quizButton {
        display: block;
        width: 300px;
        background-color: #3cb371;
        color: white;
        padding: 8px 16px;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        margin-bottom: 10px;
    }

    .quizButton:hover {
        background-color: #27854a;
    }

    .no-underline {
        text-decoration: none;
        color: inherit;
    }

    .quizList {
        list-style-type: none;
        padding: 0;
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        justify-content: center;
        align-items: center;
    }

    .search {
        list-style-type: none;
        padding: 0;
        display: flex;
        flex-wrap: wrap;
        gap: 40px;
        justify-content: center;
        align-items: center;
    }
</style>

<body>

<%
    DBConn dbConn = new DBConn();
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }
    int userId= user.getId();
    String TargetId = request.getParameter("id");
    TargetId = TargetId == null ? ""+userId : TargetId;
    int targetId=Integer.parseInt(TargetId);
	// targetId = 1;

    String type = request.getParameter("type");
    ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

    String filed1 = request.getParameter("search_text1");
    String filed = request.getParameter("search_text");
    String name = request.getParameter("search_name");

    if ("All Quizzes".equals(type)) {
        quizzes = dbConn.getQuizzes();
    } else if ("Popular Quizzes".equals(type)) {
        quizzes = dbConn.getPopularQuizzes();
    } else if ("Recently Created Quizzes".equals(type)) {
        quizzes = dbConn.getRecentlyCreatedQuizzes(-1);
    } else if ("Recently Taken Quizzes".equals(type)) {
        ArrayList<QuizHistory> quizHist = dbConn.getUserRecentQuizHistory(targetId);
        ArrayList<Integer> alreadyAdded = new ArrayList<Integer>();
        for(QuizHistory qh : quizHist) {
            if(!alreadyAdded.contains(qh.getQuiz_id())) {
                quizzes.add(dbConn.getQuizById(qh.getQuiz_id()));
                alreadyAdded.add(qh.getQuiz_id());
            }
        }
    } else if ("My Recently Created Quizzes".equals(type)) {
        quizzes = dbConn.getRecentlyCreatedQuizzes(targetId);
    } else if("Search By Category".equals(type)){
        quizzes = dbConn.getQuizzesByCategory(filed);
    } else if("Search By Tag".equals(type)){
        quizzes = dbConn.getQuizzesByTag(filed1);
    } else if("Search By Name".equals(type)){
        quizzes = dbConn.getQuizzesByName(name);
    }
%>

    <div class="navbar">
        <a href="./home.jsp"><button class="navbarItem">Home</button></a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Log Out</a>
    </div>

    <div class="container">
         <div class="left">
            <div class="search">
                <form class="searchItem" action="./allQuizzes.jsp" method="post">
                  <input class="searchSubItem" name="search_text1" rows="1" cols="30" placeholder="Tag"/>
                  <button class="action-button" name="type" value = "Search By Tag">Search By Tag</button>
                </form>
                <form class="searchItem" action="./allQuizzes.jsp" method="post">
                  <input class="searchSubItem" name="search_text" rows="1" cols="30" placeholder="Category"/>
                  <button class="action-button" name="type" value = "Search By Category">Search By Category</button>
                </form>
                <form class="searchItem" action="./allQuizzes.jsp" method="post">
                  <input class="searchSubItem" name="search_name" rows="1" cols="30" placeholder="Quiz Name"/>
                  <button class="action-button" name="type" value = "Search By Name">Search By Name</button>
                </form>
            </div>

            <h1 class="center-text">Quizzes</h1>
            <% if(quizzes.size()==0){ %>
                <p class="center-text" style="color:grey;">No quizzes.</p>
            <% } else { %>
            <ul class="quizList">
            <% for(Quiz q : quizzes) { %>
            <li> <a class="no-underline" href="<%= " quizSummary.jsp?id=" + q.id %>">
                 <button class="quizButton">
                     <%= q.quiz_name %>
                 </button>
                 </a>
            </li>
            <% }} %>
            </ul>
        </div>

        <div class="right">
            <form action="allQuizzes.jsp" method="post">
                <ul class="list">
                    <input class="button <%= "All Quizzes".equals(type) ? "selected" : "" %>" type="submit" name="type" value="All Quizzes">
                    <input class="button <%= "Popular Quizzes".equals(type) ? "selected" : "" %>" type="submit" name="type" value="Popular Quizzes">
                    <input class="button <%= "Recently Created Quizzes".equals(type) ? "selected" : "" %>" type="submit" name="type" value="Recently Created Quizzes">
                    <input class="button <%= "Recently Taken Quizzes".equals(type) ? "selected" : "" %>" type="submit" name="type" value="Recently Taken Quizzes">
                    <input class="button <%= "My Recently Created Quizzes".equals(type) ? "selected" : "" %>" type="submit" name="type" value="My Recently Created Quizzes">
                </ul>
            </form>
        </div>
    </div>
</body>

</html>