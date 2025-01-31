<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
    <meta charset="UTF-8">
    <style>
     .navbar {
         background-color: #007bff;
         color: white;
         display: flex;
         justify-content: space-between;
         align-items: center;
         padding: 10px 20px;
     }

     .navbar a {
         color: white;
         text-decoration: none;
         margin-left: 20px;
         font-size: 18px;
     }
     .navbar {
         background-color: #007bff;
         overflow: hidden;
     }

     .link {
         color: black;
         text-decoration: none;
         margin-left: 20px;
         font-size: 18px;
         font-weight: bold;
     }

     .navbar a {
         float: right;
         display: block;
         color: white;
         text-align: center;
         padding: 15px 16px;
         text-decoration: none;
         font-size: 20px;
         font-weight: bold;
     }

     .navbarItem {
         padding: 5px;
     }

     input.navbarSubItem {
         border: 2px solid #ccc;
         resize: none;
         padding: 5px;
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

     .announcements {
         border: 5px solid #1c98ff;
         border-radius: 15px;
         text-align: center;
         margin: 10px;
     }

     .announcementsList {
         list-style-type: none;
     }

     .box {
         border: 5px solid #1c98ff;
         border-radius: 15px;
         padding: 10px;
         margin: 10px;
     }

     .achievement-icon {
         width: 30px;
         height: 30px;
     }

     .frUsername {
         font-size: 20px;
         font-weight: bold;
     }

     .list {
         list-style-type: none;
         display: flex;
         gap: 20px;
     }

     .quizButton {
         background-color: #007bff;
         color: white;
         padding: 8px 16px;
         border: none;
         border-radius: 5px;
         font-size: 16px;
         cursor: pointer;
         box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
     }

     .quizButton:hover {
         background-color: #0056b3;
     }

    </style>
</head>

<%  DBConn dbConn=new DBConn();
    User user = (User) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }

    Cookie[] cookies = request.getCookies();

    boolean usernameCookieExists = false;
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                usernameCookieExists = true;
                break;
            }
        }
    }

    if (usernameCookieExists) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }

        %>
<body>
    <div class="navbar">
        <a class = "link" href="<%= request.getContextPath() %>/loginPage.jsp">Logg in</a>
        <a class = "link" href="<%= request.getContextPath() %>/CreateAccount.jsp">Create Account</a>
    </div>

    <div class="announcements">
        <h1>Announcements</h1>
        <ul class="announcementsList">
            <% ArrayList<Announcement> as = dbConn.getAnnouncements();
                for(Announcement a : as) {
                %>
                <li>
                    <%= a.getAnnouncementBody() %>
                </li>
            <% } %>
        </ul>
    </div>

    <div class="container">
        <div class="left">
            <div class="box">
                <h1>Popular Quizzes</h1>
                <ul class="list">
                    <% ArrayList<Quiz> qs = dbConn.getPopularQuizzes();
                        for(Quiz q : qs) {
                        %>
                        <li> <a href="<%= " quizSummary.jsp?id=" + q.id %>">
                                <button class="quizButton">
                                    <%= q.quiz_name %>
                                </button>
                            </a>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
