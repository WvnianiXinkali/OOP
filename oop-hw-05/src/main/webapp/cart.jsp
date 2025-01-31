<%@ page import="task2.StoreController" %>
<%@ page import="task2.cart" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
    <body>
        <h1>Shopping Cart</h1>
        <%
        double cost = 0;
        StoreController controller = (StoreController) application.getAttribute("control");
        cart cart1 = (cart) session.getAttribute("cartt");
        ArrayList<String> items = cart1.getList();
        %>
        <form method="post" action="updateServlet">
            <% for (String item : items) {
                ArrayList<String> row = controller.getRow(item);
                String name = row.get(0);
                double price = Double.parseDouble(row.get(1));
                String file = row.get(2);
            %>
            <ul>
                <li>
                    <input name="<%=item%>" type="number" value="<%=cart1.getCount(item)%>"> <%=name + ", " + price%>
                </li>
            </ul>
            <% cost += cart1.getCount(item) * price; } %>
            <p>Total: $<%=cost%> <input type="submit" value="Update Cart"></p>
        </form>

        <a href="/storehomepage.jsp">Continue Shopping</a>
    </body>
</html>
