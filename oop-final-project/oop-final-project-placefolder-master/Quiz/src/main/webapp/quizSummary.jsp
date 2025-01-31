<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.models.Question" %>
<%@ page import="Quiz.src.main.java.models.User" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.HelperMethods.CreateLittleStarRatings" %>
<%@ page import="java.util.*" %>
<html>
<title>
    Quiz summary
</title>
<style>
    .header {
        background-color: #087cfc;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px;
    }

    .lft {
        flex: 1;
    }

    .rgt {
        display:flex;
        margin-left: 10px;
    }
    .center {
        flex: 5;
        text-align: center;
    }

    .user-link {
        color: white;
        font-size: 20px;
        font-weight: bold;
        text-decoration: none;
    }


    .action-button {
        background-color: #e74c3c;
        color: white;
        padding: 10px 20px;
        text-align: center;
        display: inline-block;
        font-size: 15px;
        border-radius: 5px;
        cursor: pointer;
    }

    .action-button:hover {
        background-color: #c0392b;
    }

    header {
        color: white;
        font-size: 24px;
        font-family: Courier, monospace;
    }
    body {
        font-family: Courier, monospace;
    }
    .navbarItem {
        padding: 5px;
    }

    .best_performances{
        display: inline-block;
        border: 1px solid #087cfc;
        border-radius: 10px;
        padding: 10px;
        margin: 5px;
    }
    .highest_scores{
        display: inline-block;
        border: 1px solid #087cfc;
        border-radius: 10px;
        padding: 10px;
        margin: 5px;
    }
    .today_highest_scores{
        display: inline-block;
        border: 1px solid #087cfc;
        border-radius: 10px;
        padding: 10px;
        margin: 5px;
    }
    .last_quiz_takers{
        display: inline-block;
        border: 1px solid #087cfc;
        border-radius: 10px;
        padding: 10px;
        margin: 5px;
    }
    .test_start_button{
        background-color: #087cfc;
        border: none;
        margin-top: 10px;
        padding: 10px 15px;

        color: white;
        cursor: pointer;
        border-radius: 5px;
    }
    .flex-container {
        display: flex;
        align-items: center;
        gap: 10px;
    }
    .hidden {
        display: none;
    }
    .reviewSection {
            margin-top: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            background-color: #f9f9f9;
        }
    .review {
        margin: 10px 0;
        padding: 10px;
        border: 1px solid #ddd;
        background-color: #fff;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    .rating {
        color: #ffd700;
    }
    .review-container {
        position: relative;
        border: 1px solid #ccc;
        margin-bottom: 10px;
        padding: 10px;
    }

    .edit-icon {
        position: absolute;
        top: 10px;
        right: 10px;
        font-size: 20px;
        color: #ccc;
    }

    .edit-form {
        display: none;
        margin-top: 10px;
    }
    .edit-rating-form {
        display: block;
        margin-top: 10px;
    }
    .delete-button {
        background-color: #e74c3c;
        color: white;
        border: none;
        padding: 5px 10px;
        cursor: pointer;
        border-radius: 5px;
        position: absolute;
        bottom: 10px;
        right: 10px;
    }
</style>


<body>
<%
    User user1 = (User) session.getAttribute("user");
    //if (user1 == null) {
    //    response.sendRedirect(request.getContextPath() + "/MainPageServlet");
    //    return;
    //}

    DBConn con = new DBConn();
    if(user1!= null){
        ArrayList<User> users = con.getUsersByUsername(user1.getUsername());
        if(users.isEmpty()){
            response.sendRedirect(request.getContextPath() + "/MainPageServlet");
            return;
        }
    }


%>

</div>
<%

    int userId = 1;
    if (user1 != null) {
    userId = user1.getId();
    }
    int id = Integer.parseInt(request.getParameter("id"));

    //int id = 1;
    //int userid=1;
    Quiz quiz = con.getQuiz(id);
    if(quiz == null){
        response.sendRedirect(request.getContextPath() + "/MainPageServlet");
        return;
    }
    User user = user1;
    if (user1 != null) {
    ArrayList<User> u = con.getUsers(userId);

    user = u.get(0);
    }
    User creator = con.getUsers(quiz.creator_id).get(0);
    int quizid = quiz.id;
    HttpSession ses = request.getSession();
    if(user1 !=null ){
        ses.setAttribute("iterator"+quizid+"_"+user1.getId(),null);
        ses.setAttribute("practiceSession"+quizid,null);
    }

    String ReportText = request.getParameter("reporttext");
    ReportText = ReportText == null ? "Inappropriate Quiz" : ReportText;
    if (user1 != null) {
        ArrayList<Integer> iyo = new ArrayList<Integer>();
        iyo.add(0);
        iyo.add(user.getId());
        iyo.add(quizid);

        session.setAttribute("xulignobs" + quizid + "_" + user.getId(), iyo);


        ArrayList<Question> questions = con.getQuestions(quizid);
        for (int i = 0; i < questions.size(); i++) {
            session.setAttribute("question" + i + "_" + quizid + "_" + userId, null);
            session.setAttribute("daechira" + i + "_" + quizid + "_" + user1.getId(), false);
        }
    }
%>



<div class  = header>
    <div class= "lft" >
    <header style = "color: white">
        <a class = "user-link" href="./home.jsp">Home</a>
    </header>
    </div>
    <% if (user1 != null) { %>
    <div class="center">
        <a href="/userProfile.jsp" class="user-link">Logged in as <%= user1.getUsername() %></a>
    </div>
    <% } %>
    <div class= "rgt">
        <% if (user1 != null) { %>
        <% if ((user1.isAdmin())) { %>
            <form class="navbarItem" action="./RemoveQuizHistory" method="post">
              <button id = "id" class="action-button">Remove QuizHistory</button>
              <input type="hidden" name="quizid" value="<%= quizid %>">
            </form>
            <form class="navbarItem" action="./RemoveQuiz" method="post">
              <button id = "id1" class="action-button">Remove Quiz</button>
              <input type="hidden" name="quizid" value="<%= quizid %>">
            </form>
        <% } else {%>
            <form class="navbarItem" action="./markAsBad" method="post">
              <button id = "id2" class="action-button"><%= ReportText %></button>
              <input type="hidden" name="quizid" value="<%= quizid %>">
              <input type="hidden" name="userId" value="<%= userId %>">
            </form>
        <% } %>
     <% } %>
    </div>

</div>
<% if (quiz.description != null){ %>
<div>
    <h2>
        Quiz Description
    </h2>
    <p>
        <%=quiz.description%>
    </p>
<%}%>
</div>
<% if (user1 != null) { %>
<div>
    <p>Quiz Creator: <a href = <%="../userProfile.jsp?id="+quiz.creator_id%>><%=creator.getUsername()%></a></p>
</div>
<% }


    if (user1 != null) { %>
<div class = best_performances>
    <h3> Your Best Performances</h3>
    <%
        ArrayList<Integer> best = con.getYourBestPerformance(id,userId);
    %>
    <ul>
        <% for(int i : best){

            %>
        <li> <%=(double)i%></li>
        <% } %>
    </ul>
</div>

<% } %>
<div class="highest_scores">
    <h3>Highest Scores</h3>

    <%
        List<Map.Entry<Integer, Double>> bestUsers = con.getBestPerformance(id,false);
    %>


    <ul>
        <% for(Map.Entry<Integer,Double> entry : bestUsers){
            User us = con.getUsers(entry.getKey()).get(0);
            boolean canBeSeen = !us.isPrivate()||con.areFriends(us.getId(),userId);
            if (canBeSeen){
            %>
            <li><a href="<%="/userProfile.jsp?id="+us.getId()%>"><%=us.getUsername()%></a> <%=entry.getValue()%></li>
            <% }else{ %>
            <li><%="anonymous"%> <%=entry.getValue()%></li>
            <%}}%>
    </ul>
</div>
<div class = today_highest_scores>
    <h3>Today's Highest Scores</h3>
    <%
         bestUsers = con.getBestPerformance(id,true);
    %>

    <ul>
        <% for(Map.Entry<Integer,Double> entry : bestUsers){
            User us = con.getUsers(entry.getKey()).get(0);
            boolean canBeSeen = !us.isPrivate()||con.areFriends(us.getId(),userId);
            if (canBeSeen){
            %>
            <li><a href="<%="/userProfile.jsp?id="+us.getId()%>"><%=us.getUsername()%></a> <%=entry.getValue()%></li>
            <% }else{ %>
            <li><%="anonymous"%> <%=entry.getValue()%></li>
            <%}}%>
    </ul>
</div>
<div class = last_quiz_takers>
    <h3>3 Last Quiz Taker</h3>
    <%
        List<Map.Entry<Integer, Double>> lastTaker = con.getLastQuizPerformers(id);
    %>

    <ul>
        <% for(Map.Entry<Integer,Double> entry : lastTaker){
            User us = con.getUsers(entry.getKey()).get(0);
            boolean canBeSeen = !us.isPrivate()||con.areFriends(us.getId(),userId);
            if (canBeSeen){
        %>
        <li><a href="<%="/userProfile.jsp?id="+us.getId()%>"><%=us.getUsername()%></a> <%=entry.getValue()%></li>
        <% }else{ %>
        <li><%="anonymous"%> <%=entry.getValue()%></li>
        <%}}%>
    </ul>
</div>
<div class="flex-container">

    <form id="quizForm" action=<%=!quiz.is_single_page?"/quiz.jsp?id=" +quizid:"/quizSinglePage.jsp?id="+quizid%> method="POST">
        <button class="test_start_button">Start</button>
    </form>
    <% if (quiz.can_be_practiced) { %>
    <form id="quizPracticeForm" action=<%="/preparePractice?id="+quiz.id%> method="POST">
        <button class="test_start_button">Test</button>
    </form>
    <% } %>
    <% if (quiz.is_single_page) { %>
    <label>
        <input type="checkbox" id="immediateCorrection" name="checkType" value="immediateCorrection"> Immediate Correction
    </label>
    <% }
    if (quiz.is_single_page){
    %>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const checkbox = document.getElementById('immediateCorrection');
            const quizForm = document.getElementById('quizForm');
            const quizPracticeForm = document.getElementById('quizPracticeForm');
            checkbox.addEventListener('change', function () {
                const isChecked = checkbox.checked;
                quizForm.action = isChecked ? '<%="/quizSinglePage.jsp?id="+quiz.id+"&correction=true"%>' :'<%="/quizSinglePage.jsp?id="+quiz.id%>';
                quizPracticeForm.action = isChecked ? '/preparePractice?id=' +quizid: '/preparePractice?id=' +quizid;
            });
        });

    </script>
    <% } %>
    <%if(user1 != null && user1.getId() == creator.getId()) {%>
        <form id="quizPracticeForm1" action="./EditQuiz?userId=<%=userId%>&&quizId=<%=quizid%>" method="POST">
            <button class="test_start_button">Edit Quiz</button>
        </form>
    <%}%>
</div>



<%
    boolean showReviewSection = false;
    String action = request.getParameter("action");
    if ("Hide All".equals(action)) {
        showReviewSection = true;
    }
%>

<script>
    function toggleEditForm(formId) {
        var editForm = document.getElementById(formId);
        editForm.style.display = editForm.style.display === 'none' ? 'block' : 'none';
    }

    document.addEventListener('DOMContentLoaded', function() {
        var editIcons = document.querySelectorAll('.edit-icon');
        editIcons.forEach(function(icon) {
            icon.addEventListener('click', function(event) {
                event.preventDefault();
                var formId = icon.getAttribute('data-form-id');
                toggleEditForm(formId);

                var ratingFormId = 'editRatingForm' + formId.substring(8);
                toggleEditForm(ratingFormId);
            });
        });
    });
</script>

<div>
    <h2>Rate and Review</h2>
    <% if (showReviewSection) { %>
    <div class="reviewSection">
        <% ArrayList<rateAndReview> rateAndReviews = con.getRateAndReview(quizid); %>
        <% if (!rateAndReviews.isEmpty()) { %>
            <ul>
                <%
                Comparator<rateAndReview> compareRat = new Comparator<rateAndReview>() {
                    @Override
                    public int compare(rateAndReview q1, rateAndReview q2) {
                        return Integer.compare(q2.id, q1.id);
                    }
                };
                Collections.sort(rateAndReviews, compareRat);
                for (rateAndReview rateAndReviewer : rateAndReviews) { %>
                <li>
                    <div class="review-container">
                        <p><strong>User:</strong> <%= con.getUsers(rateAndReviewer.userId).get(0).getUsername() %>
                            <% if(rateAndReviewer.userId == userId) { %>
                                <a href="#" class="edit-icon edit-review-icon" data-form-id="editForm<%= rateAndReviewer.id %>">&#9998;</a>
                            <% } %>
                        </p>
                        <p><strong>Rating:</strong> <%= CreateLittleStarRatings.generateRatingStars(rateAndReviewer.rating) %></p>
                        <form action="/changeRatingServlet" id="editRatingForm<%= rateAndReviewer.id %>" method="post" class="edit-form" style="display: none;">
                            <select name="editedRating">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                            </select>
                            <input type="hidden" name="reviewId" value="<%= rateAndReviewer.id %>">
                            <input type="hidden" name="quizId" value="<%= quizid %>">
                            <button type="submit" name="action" value="Save">Save</button>
                        </form>
                        <p><strong>Review:</strong> <%= rateAndReviewer.review %></p>
                        <form action="/changeReviewServlet" id="editForm<%= rateAndReviewer.id %>" method="post" class="edit-form" style="display: none;">
                            <textarea name="editedReview" rows="4" cols="50"><%= rateAndReviewer.review %></textarea>
                            <input type="hidden" name="reviewId" value="<%= rateAndReviewer.id %>">
                            <input type="hidden" name="quizId" value="<%= quizid %>">
                            <button type="submit" name="action" value="Save">Save</button>
                        </form>
                        <% if(rateAndReviewer.userId == userId) { %>
                             <form action="/deleteRateAndReview" method="post">
                                <input type="hidden" name="reviewId" value="<%= rateAndReviewer.id %>">
                                <input type="hidden" name="quizId" value="<%= quizid %>">
                                <button class="delete-button" type="submit" name="action" value="Delete">Delete</button>
                            </form>
                        <% } %>
                    </div>
                </li>
                <% } %>
            </ul>
        <% } else { %>
            <p>No ratings and reviews yet.</p>
        <% } %>
    </div>
    <% } %>
    <form method="post">
        <% String whatToShow = (showReviewSection ? "Hide All" : "See Reviews");
            String whatNotToShow = (!showReviewSection ? "Hide All" : "See Reviews");%>
        <button type="submit" name="action" value="<%= whatNotToShow %>"><%= whatToShow %></button>
    </form>
</div>


</body>
</html>
