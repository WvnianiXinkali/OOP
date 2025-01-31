<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head> <title>Create Account</title> </head>
    <body>
            <h1>The Name <%= request.getParameter("username") %> Is Already In Use</h1>
            <p>Please enter another name and password</p>
            <form method="post" action="creationServlet">
                <label for="username">User Name:</label>
                <input type="text" name="username" placeholder="Enter your username">
                <br> </br>
                <label for="password">Password:</label>
                <input type="text" name="password" placeholder="Enter your password">
                <input type="submit" value="Login" id="Login">
            </form>
    </body>
</html>