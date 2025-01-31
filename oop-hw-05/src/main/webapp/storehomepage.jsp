<%@ page import="java.sql.*" %>
<%@ page import="task2.StoreController" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
StoreController controller = (StoreController) application.getAttribute("control");
ResultSet res = controller.getDataBase();
%>
<html>
<head>
    <title>Student Store</title>
</head>
<body>
    <h1>Student Store</h1>
    <h>Items available:</h>
    <ul>
        <% while (res.next()) { %>
        <li><a href="show-product.jsp?id=<%= res.getString(1) %>"><%= res.getString(2) %></a></li>
        <% } %>
    </ul>
</body>
</html>
