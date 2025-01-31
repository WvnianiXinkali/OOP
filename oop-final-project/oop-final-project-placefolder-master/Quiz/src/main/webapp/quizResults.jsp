<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.models.*" %>
<%@ page import="Quiz.src.main.java.models.enums.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.src.main.java.models.Question" %>
<%@ page import="Quiz.src.main.java.models.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.time.Duration" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="Quiz.src.main.java.models.DBConn" %>
<%@ page import="Quiz.src.main.java.models.Quiz" %>
<%@ page import="Quiz.src.main.java.models.Question" %>
<%@ page import="Quiz.src.main.java.models.Answer" %>
<%@ page import="Quiz.src.main.java.models.enums.QuestionType" %>
<%@ page import="Quiz.src.main.java.HelperMethods.*" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<%@ page import="java.util.stream.Collectors" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Results</title>
      <style>
          body {
              font-family: Arial, sans-serif;
              margin: 0;
              padding: 0;
              background-color: #f4f4f4;
          }
        .container {
            max-width: 1600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
          header {
              background-color: #007bff;
              color: #ffffff;
              text-align: center;
              padding: 10px;
              margin-bottom: 20px;
              border-top-left-radius: 10px;
              border-top-right-radius: 10px;
          }
          .score {
              text-align: center;
              font-size: 24px;
              color: #007bff;
              margin-top: 20px;
              font-weight: bold;
          }
          h2 {
              margin-top: 20px;
              text-align: center;
          }
          .section {
              background-color: #f8f8f8;
              padding: 15px;
              border-radius: 5px;
              margin-bottom: 20px;
              box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
          }
          table {
              width: 100%;
              border-collapse: collapse;
              margin-top: 10px;
          }
          th, td {
              padding: 10px;
              border-bottom: 1px solid #dddddd;
          }
          th {
              background-color: #f5f5f5;
              text-align: left;
              color: #333333;
          }
          footer {
              background-color: #f4f4f4;
              text-align: center;
              padding: 10px;
              margin-top: 20px;
              border-bottom-left-radius: 10px;
              border-bottom-right-radius: 10px;
          }
          .time-taken {
              font-weight: bold;
              color: black;
          }
          .tables-row {
              display: flex;
              align-items: center;
          }
          .table-container {
              flex: 1;
              margin-right: 20px;
          }
          .homeButton {
              background-color: #007bff;
              color: #ffffff;
              position: absolute;
              top: 0;
              right: 0;
              margin: 10px;
              padding: 10px;
              border: none;
              border-radius: 5px;
              font-size: 16px;
              cursor: pointer;
              box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
          }
      </style>
</head>

<%
User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/MainPageServlet");
    return;
}
int quizID = Integer.parseInt(request.getParameter("id"));

HttpSession ses = request.getSession();

LocalTime startTime = (LocalTime) ses.getAttribute("quizStartTime"+quizID+"_"+user.getId());

LocalTime endTime = LocalTime.now();

Duration minutes = Duration.between(startTime, endTime);


ses.setAttribute("time"+quizID+"_"+user.getId(), minutes);
DBConn con = new DBConn();

int totalScore = 0;

Quiz quiz = con.getQuiz(quizID);
ArrayList<Question> questions = (ArrayList)ses.getAttribute("shuffledQuestions" + quizID + "_" + user.getId());
List<Answer> correctAnswers1 = new ArrayList<Answer>();
for(int i = 0; i < questions.size(); i++){
    List<Answer> correctAnswers2 = con.getAnswers(questions.get(i).id,true);
    correctAnswers1.addAll(correctAnswers2);
}

int esa = -1;

for(int i = 0; i < questions.size(); i++){
    Question question = questions.get(i);
    QuestionType questionType = question.type;
    List<Answer> correctAnswers = con.getAnswers(questions.get(i).id,true);
    List<Answer> allAnswers = con.getAnswers(questions.get(i).id, false);

    ArrayList<String> userAnswersSingle = (ArrayList<String>)ses.getAttribute("question" + i +"_"+quizID+"_"+user.getId());

    if(userAnswersSingle == null){
        userAnswersSingle = new ArrayList<String>();
        for(int i1 = 0; i1 < allAnswers.size(); i1++){
            userAnswersSingle.add(null);
        }
    }



    if (questionType == QuestionType.QUESTION_RESPONSE){
        String answer =  request.getParameter("question" + i);
        if(quiz.is_single_page) answer = userAnswersSingle.get(0);
        if(answer != null)
        if(answer.equalsIgnoreCase(correctAnswers.get(0).answer)) totalScore++;

    } else if(questionType == QuestionType.FILL_IN_THE_BLANK){
        ArrayList<String> userAnswers = new ArrayList<String>();
        if(quiz.is_single_page){
        userAnswers = userAnswersSingle;
        } else{
        for(int j = 0; j < correctAnswers.size(); j++){
            userAnswers.add(request.getParameter("question" + i +"_"+j));
        }
        }
        Collections.sort(correctAnswers, new Comparator<Answer>() {
            @Override
            public int compare(Answer a1, Answer a2) {
                return Integer.compare(a1.getId(), a2.getId());
            }
        });
        for(int l = 0; l < correctAnswers.size(); l++){
            if(userAnswers.get(l) != null)
                    if(userAnswers.get(l).equalsIgnoreCase(correctAnswers.get(l).answer)) totalScore++;
        }
    } else if(questionType == QuestionType.MULTIPLE_CHOICE){
        String answer =  request.getParameter("question" + i);
        if(quiz.is_single_page) answer = userAnswersSingle.get(0);
        if(answer != null)
        if(answer.equalsIgnoreCase(correctAnswers.get(0).answer)) totalScore++;

    } else if(questionType == QuestionType.PICTURE_RESPONSE) {
        String answer = request.getParameter("question" + i);
        if(quiz.is_single_page) answer = userAnswersSingle.get(0);
        if(answer != null)
        if(answer.equalsIgnoreCase(correctAnswers.get(0).answer)) totalScore++;

    } else if (questionType == QuestionType.MULTI_ANSWER){
        ArrayList<String> userAnswers = new ArrayList<String>();
        if(quiz.is_single_page){
        userAnswers = userAnswersSingle;
        } else{
        for(int j = 0; j < correctAnswers.size(); j++){
            userAnswers.add(request.getParameter("question" + i +"_"+j));
        }
        }
        Map<String, Integer> correctFrequencyMap = new HashMap<String, Integer>();
        for (Answer curAnswer : correctAnswers) {
            correctFrequencyMap.put(curAnswer.answer, correctFrequencyMap.getOrDefault(curAnswer.answer, 0) + 1);
        }

        for (int k = 0; k < userAnswers.size(); k++) {
            String userAnswer = userAnswers.get(k);

            if(userAnswer != null)
            if (correctFrequencyMap.containsKey(userAnswer) && correctFrequencyMap.get(userAnswer) > 0) {
                correctFrequencyMap.put(userAnswer, correctFrequencyMap.get(userAnswer) - 1);
                totalScore++;
            }
        }

    } else if (questionType == QuestionType.MULTI_AN_CHOICE){
        ArrayList<String> userAnswers = new ArrayList<String>();
        if(quiz.is_single_page){
        userAnswers = userAnswersSingle;
        } else{
        for(int j = 0; j < correctAnswers.size(); j++){
            userAnswers.add(request.getParameter("question" + i +"_"+j));
        }
        }
        Map<String, Integer> correctFrequencyMap = new HashMap<String, Integer>();
        for (Answer curAnswer : correctAnswers) {
            correctFrequencyMap.put(curAnswer.answer, correctFrequencyMap.getOrDefault(curAnswer.answer, 0) + 1);
        }

        for (int k = 0; k < userAnswers.size(); k++) {
            String userAnswer = userAnswers.get(k);

            if(userAnswer != null)
            if (correctFrequencyMap.containsKey(userAnswer) && correctFrequencyMap.get(userAnswer) > 0) {
                correctFrequencyMap.put(userAnswer, correctFrequencyMap.get(userAnswer) - 1);
                totalScore++;
            }
        }
    }
}
%>

<%
int time_taken = (int)((Duration)session.getAttribute("time"+quizID+"_"+user.getId())).toSeconds();
ArrayList<Integer> iyo = (ArrayList<Integer>) session.getAttribute("xulignobs"+quizID+"_"+user.getId());
if(iyo == null || (user.getId() == iyo.get(1) && quizID == iyo.get(2) && iyo.get(0) == 0)){
    con.insertQuizHistory(new QuizHistory(1, totalScore, quizID, user.getId(), (int)((Duration)session.getAttribute("time"+quizID+"_"+user.getId())).toSeconds()));

    ArrayList<QuizHistory> userHistory = con.getUserQuizHistory(user.getId());
    HashSet<Integer> uniqueQuizIds = new HashSet<Integer>();
    ArrayList<QuizHistory> uniqueQuizHistories = new ArrayList<QuizHistory>();

    for (QuizHistory history : userHistory) {
        int quizId = history.getQuiz_id();
        if (!uniqueQuizIds.contains(quizId)) {
            uniqueQuizIds.add(quizId);
            uniqueQuizHistories.add(history);
        }
    }
    if(uniqueQuizHistories.size() == 10){
        con.insertUserAchievement(new UserAchievement(1, user.getId(), 4));
    }

    ArrayList<QuizHistory> quizQuizHistory = con.GetquizQuizHistoryAndDate(quizID);

    Comparator<QuizHistory> customComparator = new Comparator<QuizHistory>() {
        @Override
        public int compare(QuizHistory q1, QuizHistory q2) {
            return Double.compare(q2.getScore(), q1.getScore());
        }
    };
    Collections.sort(quizQuizHistory, customComparator);

    ArrayList<Achievement> userAchievements = con.getUserAchievements(user.getId());
    boolean isttr = false;
    for(int d = 0 ; d < userAchievements.size(); d++){
        if(userAchievements.get(d).getId() == 5) isttr = true;
    }

    if((quizQuizHistory.isEmpty() || (quizQuizHistory.get(0).getScore() <= totalScore)) && !isttr){
        con.insertUserAchievement(new UserAchievement(1, user.getId(), 5));
    }

    if(uniqueQuizHistories.size() == 1){
        con.insertUserAchievement(new UserAchievement(1, user.getId(), 6));
    }


    iyo = new ArrayList<Integer>();
    iyo.clear();
    iyo.add(1);
    iyo.add(user.getId());
    iyo.add(quizID);

    session.setAttribute("xulignobs"+quizID+"_"+user.getId(), iyo);

} else{%>
<%}


%>



<body>
    <div class="container">

        <header>
            <h1>Quiz Results</h1>
        </header>

        <a href="<%= " quizSummary.jsp?id=" + quizID %>">
            <button class="homeButton">Done</button>
        </a>

        <div class="score">
            Your Score: <%=totalScore%> out of <%=correctAnswers1.size()%>
             <br>
            <span class="time-taken">Time taken: <%=TimeInStrings.timeToStrings(time_taken)%></span>
        </div>

        <div class="tables-row">
            <div class="table-container">
                <h2>Your Answers and Correct Answers:</h2>
                <table class="answers-details">
                    <tr>
                        <th>Question #</th>
                        <th>Your Answer </th>
                        <th>Correct Answer</th>
                    </tr>

                    <%
                    for (int i = 0; i < questions.size(); i++) {
                        Question question = questions.get(i);
                        QuestionType questionType = question.type;
                        List<Answer> correctAnswers = con.getAnswers(question.id, true);
                        String userAnswer = "";
                        List<Answer> allAnswers = con.getAnswers(questions.get(i).id, false);
                        ArrayList<String> userAnswersSingle1 = (ArrayList<String>)ses.getAttribute("question" + i + "_" + quizID + "_" + user.getId());


                        if(userAnswersSingle1 == null){
                            userAnswersSingle1 = new ArrayList<String>();
                            for(int i1 = 0; i1 < allAnswers.size(); i1++){
                                userAnswersSingle1.add(null);
                            }
                        }

                        if (questionType == QuestionType.QUESTION_RESPONSE || questionType == QuestionType.MULTIPLE_CHOICE
                                || questionType == QuestionType.PICTURE_RESPONSE) {
                            if(quiz.is_single_page) userAnswer = userAnswersSingle1.get(0);
                            else{
                            userAnswer = request.getParameter("question" + i);
                            }
                        } else if (questionType == QuestionType.FILL_IN_THE_BLANK || questionType == QuestionType.MULTI_ANSWER
                                || questionType == QuestionType.MULTI_AN_CHOICE) {
                            ArrayList<String> userAnswers = userAnswersSingle1;

                            ArrayList<String> userAnswers23 = new ArrayList<String>();

                            if(!quiz.is_single_page){
                                for(int j = 0; j < correctAnswers.size(); j++){
                                    userAnswers23.add(request.getParameter("question" + i +"_"+j));
                                }
                                userAnswers = userAnswers23;
                            }
                            ArrayList<String> answersWithouNulls = new ArrayList<String>();
                            for(String ans: userAnswers){
                                if(ans!=null){
                                    answersWithouNulls.add(ans);
                                } else{
                                    answersWithouNulls.add("");
                                }
                            }
                            userAnswer = String.join("<br>", answersWithouNulls);

                        }
                    %>
                    <tr>
                        <td><%= i + 1 %></td>
                        <%if(userAnswer != null){%>
                        <td><%= userAnswer %></td>
                        <%} else{%>
                            <td><%=""%></td>
                        <%}%>

                        <td>
                            <%
                            for (Answer correctAnswer : correctAnswers) {
                                out.print(correctAnswer.answer + "<br>");
                            }
                            %>
                        </td>
                    </tr>
                    <%
                    }
                    %>
                </table>
            </div>
            <div class="table-container">
                <h2>Your Last Tries:</h2>
                <br>
                <table class="last-tries">
                    <tr>
                        <th>Name</th>
                        <th>Score</th>
                        <th>Time Taken</th>
                    </tr>
                    <%
                    ArrayList<QuizHistory> quizHistories = con.GetUserQuizHistoryAndDate(user.getId());
                    ArrayList<QuizHistory> thisQuizHistories = new ArrayList<QuizHistory>();
                    for(int i = 0; i < quizHistories.size(); i++){
                        if(quizID == quizHistories.get(i).getQuiz_id())
                        thisQuizHistories.add(quizHistories.get(i));
                    }
                    Collections.sort(thisQuizHistories, new Comparator<QuizHistory>() {
                        @Override
                        public int compare(QuizHistory qh1, QuizHistory qh2) {
                            return qh2.getTakeDate().compareTo(qh1.getTakeDate());
                        }
                    });
                    for(int i = 0; i < thisQuizHistories.size(); i++){
                    %>
                    <tr>
                        <td>Attempt <%=thisQuizHistories.size() - i%></td>
                        <td><%=thisQuizHistories.get(i).getScore()%>/<%=correctAnswers1.size()%></td>
                        <td><%=TimeInStrings.timeToStrings(thisQuizHistories.get(i).getTime_taken())%></td>
                    </tr>
                    <% } %>
                </table>
            </div>
            <div class="table-container">
                <h2>Top Scorers:</h2>
                <br>
                <table class="top-scorers">
                    <tr>
                        <th>Name</th>
                        <th>Score</th>
                        <th>Time Taken</th>
                    </tr>
                    <%
                    ArrayList<QuizHistory> quizHistoriess = con.GetquizQuizHistoryAndDate(quizID);
                    Comparator<QuizHistory> customComparator = new Comparator<QuizHistory>() {
                        @Override
                        public int compare(QuizHistory q1, QuizHistory q2) {
                            if (q1.getScore() != q2.getScore()) {
                                return Double.compare(q2.getScore(), q1.getScore());
                            } else {
                                return Integer.compare(q1.getTime_taken(), q2.getTime_taken());
                            }
                        }
                    };
                    Collections.sort(quizHistoriess, customComparator);
                    ArrayList<Integer> gavlilebi = new ArrayList<Integer>();
                    for(int i = 0; i < quizHistoriess.size(); i++){
                    if(quizHistoriess.get(i) != null && !gavlilebi.contains(quizHistoriess.get(i).getUser_id())){
                       User currUser = con.getUsers(quizHistoriess.get(i).getUser_id()).get(0);
                       if((currUser.getId() == user.getId())||!currUser.isPrivate()||(currUser.isPrivate() && con.areFriends(user.getId(),currUser.getId()))){
                       %>
                    <tr>
                        <td><%= con.getUsers(quizHistoriess.get(i).getUser_id()).get(0).getUsername()%></td>
                        <td><%=quizHistoriess.get(i).getScore()%>/<%=correctAnswers1.size()%></td>
                        <td><%=TimeInStrings.timeToStrings(quizHistoriess.get(i).getTime_taken())%></td>
                    </tr>

                    <% }}
                       gavlilebi.add(quizHistoriess.get(i).getUser_id());
                     } %>
                </table>
            </div>
            <div class="table-container">
                <h2>Your Friends:</h2>
                <br>
                <table class="friends">
                    <tr>
                        <th>Name</th>
                        <th>Score</th>
                        <th>Time Taken</th>
                    </tr>
                    <%
                    ArrayList<QuizHistory> quizHistoriesss = con.getFriendsQuizHistory(user.getId(), quizID);
                    Collections.sort(quizHistoriesss, customComparator);
                    ArrayList<Integer> gavlilebi1 = new ArrayList<Integer>();
                    for (int i = 0; i < quizHistoriesss.size(); i++) {
                        QuizHistory history = quizHistoriesss.get(i);
                        if (history != null && !gavlilebi1.contains(history.getUser_id())) {
                    %>
                    <tr>
                        <td><%= con.getUsers(history.getUser_id()).get(0).getUsername() %></td>
                        <td><%= history.getScore() %>/<%= correctAnswers1.size() %></td>
                        <td><%= TimeInStrings.timeToStrings(history.getTime_taken()) %></td>
                    </tr>
                    <%
                            gavlilebi1.add(history.getUser_id());
                        }
                    }
                    %>
                </table>
            </div>
        </div>
    </div>
    <div>
        <h2>Rate and Review</h2>
            <form action="./SaveReview" method="post">
                <input type="hidden" name="quizId" value="<%= quizID %>">
                <input type="hidden" name="userId" value="<%= user.getId() %>">
                <label for="rating">Rating:</label>
                <select id="rating" name="rating">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                <br>
                <label for="review">Review:</label>
                <textarea id="review" name="review" rows="4" cols="50"></textarea>
                <br>
                <input type="submit" value="Submit">
            </form>
    </div>
</body>
</html>
