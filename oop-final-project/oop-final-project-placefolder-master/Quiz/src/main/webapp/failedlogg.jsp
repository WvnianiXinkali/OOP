<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Quiz.src.main.java.models.Achievement" %>
<%@ page import="Quiz.src.main.java.models.User" %>
<%@ page import="Quiz.src.main.java.models.Notification" %>
<%@ page import="Quiz.src.main.java.models.QuizHistory" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Information Incorrect</title>
    <style>
        body {
            background-color: #007bff;
            color: white;
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 0;
            padding: 0;
        }

        h1 {
            margin-top: 100px;
        }

        p {
            margin: 10px 0;
        }

        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        label {
            font-size: 18px;
            margin: 10px 0;
        }

        input[type="text"], input[type="password"] {
            width: 300px;
            padding: 10px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            margin: 5px 0;
        }

        input[type="submit"] {
            background-color: #1c98ff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            color: white;
            text-decoration: none;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }
    %>
    <h1>Please Try Again</h1>
    <p>Either your user name or password is incorrect. Please try again.</p>
    <form action="/Task1Servlet" method="post">
        <label for="username">User Name:</label>
        <input type="text" name="username" placeholder="Enter your username" required>
        <label for="password">Password:</label>
        <input type="password" name="password" placeholder="Enter your password" required>
        <input type="submit" value="Login" id="Login">
    </form>
    <p>
        <a href="CreateAccount.jsp">Create New Account</a>
    </p>
</body>
</html>
