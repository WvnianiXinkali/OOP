<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Quiz.src.main.java.models.Achievement" %>
<%@ page import="Quiz.src.main.java.models.User" %>
<%@ page import="Quiz.src.main.java.models.Notification" %>
<%@ page import="Quiz.src.main.java.models.QuizHistory" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
  <title>User</title>
  <!-- <link rel="stylesheet" href="styles.css"> -->
</head>
<body>
  <style>.navbar {
    background-color: #007bff;
    align-items: center;
    display: flex;
}

.navbar {
    background-color: #007bff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
}

.left-side1 {
    display: flex;
    align-items: center;
}

.right-side1 {
    display: flex;
    align-items: center;
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

.block-container {
    display: flex;
    /* align-items: center; */
    justify-content: center;
    margin-top: 10px;
    padding: 20px;
    border: 2px solid #007bff;
    border-radius: 12px;
}
.ban-user-container {
    display: flex;
    flex-direction: column;
    margin-right: 20px;
}

.ban-user-container form {
    margin-bottom: 10px;
}


.block-contents {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.block-title {
    font-size: 40px;
}

.block-items {
    font-size: 25px;
}

.left-side {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-right: 20px;
}

.profile-image {
    width: 200px;
    height: 200px;
    object-fit: cover;
    border-radius: 5%;
    border: 4px solid #007bff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.username {
    font-size: 24px;
    margin-top: 10px;
}

.right-side {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}


.action-button {
    background-color: #2b91fe;
    color: white;
    border: 1px solid #000000;
    border-radius: 12px;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 5px;
    margin: 3px 3px 0 0;
}

.action-button:hover {
    background-color: #9dff0a;
}

.remove-friend-button {
    background-color: #2b91fe;
    color: white;
    /* border: none; */
    border: 1px solid #000000;
    border-radius: 12px;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 5px;
}

.remove-friend-button:hover {
    background-color: #ff2c2c;
}

.admin-button {
    margin-left: auto;
    color: white;
    text-decoration: none;
    margin-right: 20px;
    font-size: 18px;
    font-weight: bold;
}

#id {
    margin-left: 0px;
    color: white;
    text-decoration: none;
    margin-right: 20px;
    font-size: 18px;
    font-weight: bold;
}

.note_text {
    margin-top: 10px;
    margin-bottom: 0px;
    resize: none;
    font-size: 18px;
}

.achievement-icon {
    width: 30px;
    height: 30px;
}

#main-block {
    display: flex;
    justify-content: space-between;
}

#left {
    display:flex;
}



#id {
    background-color:red;
    padding: 5px 10px;
    font-size: 13px;
}

#id1 {
    background-color:#FFD700;
    padding: 5px 10px;
    font-size: 13px;
}

#id2 {
    background-color:black;
    padding: 5px 10px;
    font-size: 13px;
}





</style>
  <%
    DBConn dbConn=new DBConn();
    User user = (User) session.getAttribute("user");
    if (user == null || dbConn.getUsers(user.getId()).isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }
    UserBan userban1 = dbConn.getUserBan(user.getId());

    if (userban1 != null) {
        if(userban1.userStillBanned()){
            response.sendRedirect(request.getContextPath() + "/MainPageServlet");
            return;
        } else {
            dbConn.removeUserBan(user.getId());
        }
    }
    int userId= user.getId();
    String TargetId = request.getParameter("id");
    TargetId = TargetId == null ? ""+userId : TargetId;
    int targetId=Integer.parseInt(TargetId);

    UserBan targetBan = dbConn.getUserBan(targetId);
    if(!user.isAdmin()){
        if (targetBan != null) {
            if(targetBan.userStillBanned()){
                response.sendRedirect(request.getContextPath() + "/userProfile.jsp?id=" + user.getId());
                return;
            } else {
                dbConn.removeUserBan(targetBan.user_id);
            }
        }
    }

    User targetUser = dbConn.getUsers(targetId).get(0);

if(targetId != user.getId())
    if(!user.isAdmin()){
        if(targetUser.isPrivate() && !dbConn.areFriends(user.getId(), targetId)){
            response.sendRedirect(request.getContextPath() + "/userProfile.jsp?id=" + user.getId());
            return;
        }
    }

    String buttonName = !dbConn.getUsers(userId).get(0).isPrivate() ? "Make private" : "Make Public";


    String AddFriendText = request.getParameter("addfriendtext");
    AddFriendText = AddFriendText == null ? "Add friend" : AddFriendText;

    String friendButtonClass = "action-button";
    boolean friends = dbConn.areFriends(userId, targetId);
    String removeParam = "add";

    if (friends) { 
      AddFriendText = "Remove Friend";
      friendButtonClass = "remove-friend-button";
      removeParam = "remove";
    }

    String NotePHText = request.getParameter("notephtext");
    NotePHText = NotePHText == null ? "Send a note to user" : NotePHText;

    String ChallengeText = request.getParameter("challengetext");
    ChallengeText = ChallengeText == null ? "Challenge user to Quiz" : ChallengeText;

    ArrayList<User> us = dbConn.getUsers(targetId);
    User u = us.get(0);
    String userName = u.getUsername();
    String PfpLink = u.getPfpLink();
    PfpLink = PfpLink == null || PfpLink == "" ? "https://via.placeholder.com/200x200" : PfpLink;
  %>

  <style>
  #right {
      background-color: #f8f8f8;
      border: 2px solid #007bff;
      border-radius: 12px;
      padding: 20px;
      margin-left: 20px;
      flex: 1;
    }

    #right h1 {
      font-size: 24px;
      margin-bottom: 10px;
    }

    #right form {
      margin-bottom: 20px;
    }

    #right textarea {
      width: 100%;
      padding: 5px;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 16px;
    }

    #right button {
      background-color: #2b91fe;
      color: white;
      border: none;
      border-radius: 5px;
      padding: 5px 10px;
      cursor: pointer;
      font-size: 16px;
    }

    #right ul {
      list-style-type: none;
      padding: 0;
      margin: 0;
    }

    #right li {
      font-size: 16px;
      color: #555;
      margin-bottom: 5px;
    }

  <% if (!(user.isAdmin() && userId == targetId)) { %>

       #right {
          display: none;
         }
  <% } %>

  </style>

  <div class="navbar">
    <div class="left-side1">
        <a href="./home.jsp">
          <button class="navbarItem">Home</button>
        </a>
        <form class="navbarItem" action="./SearchUser" method="post">
          <input class="navbarSubItem" name="search_text" rows="1" cols="30" placeholder="Look up user"/>
          <button class="action-button">Search User</button>
        </form>
        <a href="<%= request.getContextPath() %>/LogoutServlet">Log Out</a>
    </div>
    <div class="right-side1">
        <% if (user.isAdmin() && userId != targetId) { %>
        <div class="ban-user-container">
            <form class="navbarItem" action="./BanUser" method="post">
                <% UserBan userban = dbConn.getUserBan(targetId);
                 if (userban == null) { %>
                <label for="banDays">Days:</label>
                <input type="number" id="banDays" name="banDays" min="1" value="1" style="width: 50px;">

                <button id="id2" class="action-button">Ban User</button>
                <input type="hidden" name="userId" value="<%= userId %>">
                <input type="hidden" name="targetId" value="<%= targetId %>">
                <%
                } else {%>
                    <button id="id2" class="action-button">unBan User</button>
                    <input type="hidden" name="userId" value="<%= userId %>">
                    <input type="hidden" name="targetId" value="<%= targetId %>">
                <%
                }%>
            </form>
        </div>
        <form class="navbarItem" action="./RemoveAccount" method="post">
            <button id="id" class="action-button">Remove Account</button>
            <input type="hidden" name="userId" value="<%= userId %>">
            <input type="hidden" name="targetId" value="<%= targetId %>">
        </form>
        <% } %>


        <% if (user.isAdmin() && userId == targetId) { %>
           <a href="<%= request.getContextPath() %>/adminPage.jsp" class="admin-button">Admin</a>
        <% } %>
       <% if (user.isAdmin() && userId != targetId && !dbConn.isAdmin(targetId)) { %>
       <form class="navbarItem" action="./MakeAdmin" method="post">
          <button id = "id1" class="action-button">Make Admin</button>
          <input type="hidden" name="userId" value="<%= userId %>">
          <input type="hidden" name="targetId" value="<%= targetId %>">
       </form>
       <% } %>

    </div>
  </div>


    <div class="block-container" id="main-block">
          <div id="left">
            <div class="left-side">
              <img class="profile-image" src="<%= PfpLink %>" alt="Profile Image">
              <div class="username">
                <%= userName%>
              </div>
              <div class="user-id">User Id: <%= targetId %>
              </div>
              <% if (userId == targetId) { %>
              <form action="./Picture" method="post">
                  <textarea name="Picture" class="note_text" placeholder="Picture URL" rows="1" cols="30"></textarea>
                  <br>
                  <button class="action-button">Change Picture</button>
                  <input type="hidden" name="userId" value="<%= userId %>">
                </form>
                <% } %>
            </div>
            <div class="right-side">
              <% if(userId != targetId) { %>
              <form action="./AddFriend" method="post">
                <button class="<%= friendButtonClass %>"><%= AddFriendText%></button>
                <input type="hidden" name="userId" value="<%= userId %>">
                <input type="hidden" name="targetId" value="<%= targetId %>">
                <input type="hidden" name="removeFriend" value="<%= removeParam %>">
              </form>
              <% } %>
              <form action="./Challenge" method="post">
                <input name="quizId" class="note_text" placeholder="<%= ChallengeText%>" type="number" step="1"></input>
                <br>
                <button class="action-button">Challenge</button>
                <input type="hidden" name="userId" value="<%= userId %>">
                <input type="hidden" name="targetId" value="<%= targetId %>">
              </form>
              <form action="./Note" method="post">
                <textarea name="note_text" class="note_text" placeholder="<%= NotePHText %>" rows="4" cols="50"></textarea>
                <br>
                <button class="action-button">Send Note</button>
                <input type="hidden" name="userId" value="<%= userId %>">
                <input type="hidden" name="targetId" value="<%= targetId %>">
              </form>
              <%if(user.getId() == targetId){%>
              <form action="./makePrivate" method="post">
                  <br>
                  <button class="action-button"><%= buttonName %></button>
                  <input type="hidden" name="userId" value="<%= userId %>">
               </form>
                <%}%>
            </div>
            </div>


              <div id="right">
                  <h1> Create Announcement </h1>
                  <form action="./CreateAnnouncement" method="post">
                      <textarea name="createann" class="note_text" placeholder="Create Announcement" rows="4" cols="50"></textarea>
                      <br>
                      <button class="action-button">Create</button>
                    </form>
                  <h1>Site Statistics</h1>
                 <ul style="none; padding: 0px;">
                     <li style="list-style-type: none">  Number of users: <%= dbConn.getUsers(-1).size() %> </li>
                     <li style="list-style-type: none;"> Number of quizzes: <%= dbConn.getQuizzes().size() %> </li>
                 </ul>
              </div>
      </div>



  <div class="block-container">
    <div class="block-contents">
      <div class="block-title">History</div>
      <div class="block-items">
        <ul>
          <%
            ArrayList<QuizHistory> qhs = dbConn.GetUserQuizHistory(targetId);
            for(QuizHistory qh : qhs) {
              Quiz q = qh.getQuiz(); 
          %> 
          <li><%= String.format("%2.1f on '%s' by %s ", qh.getScore(), q.quiz_name, q.creatorName) %> </li> 
          <% 
            }
          %>
        </ul>
      </div>
    </div>
  </div>
  <div class="block-container">
    <div class="block-contents">
      <div class="block-title">Achievements</div>
      <div class="block-items">
        <ul>
          <%
            ArrayList<Achievement> uas = dbConn.getUserAchievements(targetId);
            for(Achievement ua : uas) {
          %> 
          <li><img class="achievement-icon" src="<%= ua.getAchievementIcon()%>"  title="<%= ua.getAchievementToEarn()%>"/> <%= ua.getAchievementBody()%> </li> 
          <% 
            } 
          %>
        </ul>
      </div>
    </div>
  </div>
  <% dbConn.closeDBConn(); %>

</body>
</html>
