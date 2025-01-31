<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">

<html>

<head>
    <title>Notifications</title>
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

    .box {
        border: 5px solid #1c98ff;
        border-radius: 15px;
        padding: 10px;
        margin: 10px;
        flex: 1;
    }

    .list {
        list-style-type: none;
        gap: 20px;
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

%>

    <div class="navbar">
        <a href="./home.jsp"><button class="navbarItem">Home</button></a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Log Out</a>
    </div>

    <div class="container">
         <div class="box">
            <h1 class="center-text">Friend Requests</h1>
            <ul>
                 <% ArrayList<Notification> frs = dbConn.getNotifications(targetId, -1,"friend request");
                    if(frs.size()==0){ %>
                        <p style="color:grey;">No friend requests.</p>
                    <% } else {
                    for(Notification fr : frs) {
                    %>
                    <li>
                        <%= fr.getNotifBody()%>
                    </li>
                    <% }} %>
            </ul>
        </div>

        <div class="box">
            <h1 class="center-text">Challenges</h1>
            <ul>
                 <% ArrayList<Notification> cs = dbConn.getNotifications(targetId, -1,"challenge");
                    if(cs.size()==0){ %>
                        <p style="color:grey;">No challenges.</p>
                    <% } else {
                    for(Notification c : cs) {
                    %>
                    <li>
                        <%= c.getNotifBody()%>
                    </li>
                    <% }} %>
            </ul>
        </div>

        <div class="box">
            <h1 class="center-text">Notes</h1>
            <ul>
                 <% ArrayList<Notification> ns = dbConn.getNotifications(targetId, -1,"note");
                    if(ns.size()==0){ %>
                        <p style="color:grey;">No notes.</p>
                    <% } else {
                    for(Notification n : ns) {
                    %>
                    <li>
                        <%= n.getNotifBody()%>
                    </li>
                    <% }} %>
            </ul>
        </div>
    </div>
</body>

</html>