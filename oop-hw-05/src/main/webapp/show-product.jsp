<%@ page import="task2.StoreController" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String id = request.getParameter("id");
StoreController controller = (StoreController) application.getAttribute("control");
ArrayList<String> row = controller.getRow(id);
String name = row.get(0);
double price = Double.parseDouble(row.get(1));
String file = row.get(2);
%>
<html>
<head> <title><%=name%></title> </head>
    <body>
       <h1><%=name%></h1>
       <img src="<%="store-images/" + file%>"/>;
       <form action="cartServlet" method="post">
           $<%=price%>
           <input type="hidden" name="id" value="<%=id%>"/>
           <input type="submit" value="Add to Cart">
       </form>
    </body>
</html>