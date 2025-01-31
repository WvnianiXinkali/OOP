<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head> <title>Welcome</title> </head>
    <body>
        <h1>Welcome To HomeWork 5</h1>
        <p>Please log in</p>
        <form action="/Task1Servlet" method="post" >
            <label for="username">User Name:</label>
            <input type="text" name="username" placeholder="Enter your username">
            <br> </br>
            <label for="password">Password:</label>
            <input type="text" name="password" placeholder="Enter your password">
            <input type="submit" value="Login" id="Login">
        </form>
    </body>
    <p>
      <a href="CreateAccount.jsp">Create New Account</a>
    </p>
</html>