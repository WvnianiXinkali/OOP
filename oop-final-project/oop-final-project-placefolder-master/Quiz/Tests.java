package Quiz;

import Quiz.src.main.java.HelperMethods.AnswerChecker;
import Quiz.src.main.java.HelperMethods.CreateLittleStarRatings;
import Quiz.src.main.java.HelperMethods.PassHasher;
import Quiz.src.main.java.models.*;
import Quiz.src.main.java.models.enums.*;
import Quiz.src.main.java.servlets.CreateQuizJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tests extends TestCase{

   public static final String filePath = "./oop_proj.sql";

    private DBConn dbConn;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dbConn = new DBConn();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dbConn.restartDBbase(filePath);
    }


    public void testDBConn() {

        String AchBody = "test achievement 1";
        String AchToEarn = "get tested";

        String AnnBody = "got tested";

        String NotifType = "test";
        String NotifBody = "tap tap tap";

        int userId = 1;


        var achBefore = dbConn.getAchievements(-1);
        var annBefore = dbConn.getAnnouncements();
        var notsBefore = dbConn.getNotifications(-1,-1,"");
        var userAchBefore = dbConn.getUserAchievements(userId);

        Achievement achievement = new Achievement(-1, AchBody, AchToEarn);
        dbConn.insertAchievement(achievement);
        var achievements = dbConn.getAchievements(-1);
        int testAchId = achievements.get(achievements.size()-1).getId();

        Announcement announcement = new Announcement(-1, AnnBody);
        Notification notification = new Notification(-1, userId, userId, NotifType, NotifBody);
        UserAchievement userAchievement = new UserAchievement(-1, userId, testAchId);
        userAchievement.getId();

        dbConn.insertAnnouncement(announcement);
        dbConn.insertNotification(notification);
        dbConn.insertUserAchievement(userAchievement);



        var achAfter = dbConn.getAchievements(-1);
        var annAfter = dbConn.getAnnouncements();
        var notsAfter = dbConn.getNotifications(-1,-1,"");
        var userAchAfter = dbConn.getUserAchievements(userId);

        assertEquals(achBefore.size() + 1, achAfter.size());
        assertEquals(annBefore.size() + 1, annAfter.size());
        assertEquals(notsBefore.size() + 1, notsAfter.size());
        assertEquals(userAchBefore.size() + 1, userAchAfter.size());

        var lastAch = achAfter.get(achAfter.size() - 1);
        var lastAnn = annAfter.get(annAfter.size() - 1);
        var lastNot = notsAfter.get(notsAfter.size() - 1);
        var lastUsAch = userAchAfter.get(userAchAfter.size() - 1);

        assertTrue(AchBody.equals(lastAch.getAchievementBody()));
        assertTrue(AchToEarn.equals(lastAch.getAchievementToEarn()));
        assertTrue(AnnBody.equals(lastAnn.getAnnouncementBody()));
//        assertTrue(NotifType.equals(lastNot.getNotifType()));
//        assertTrue(NotifBody.equals(lastNot.getNotifBody()));
        assertEquals(lastAch.getId(), achAfter.size());
        assertEquals(lastAnn.getId(), annAfter.size());
//        assertEquals(lastNot.getId(), notsAfter.size());


        User user = new User(1555555,"mefe", "eacd2617f105704f51c912099316c7aece2df8ef","user",false);
        dbConn.insertUser(user);
        dbConn.getUsersByUsername("mefe").get(0);
        User user1 = dbConn.getUsersByUsername("mefe").get(0);
        assertTrue(dbConn.getUsers(user1.getId()).get(0).getUsername().equals(user1.getUsername()));
        dbConn.removeUser(user1.getId());

        var rateAndrevBefore = dbConn.getRateAndReviewByID(1);
        var rateAndrevBeforeQuizId = dbConn.getRateAndReview(1);
        var userBanBefore = dbConn.getUsers(1);
        var userBanBeforeByUser = dbConn.getUsersByUsername("aleko");
        var categoriesBefore = dbConn.getCategories();
        var tagsBefore = dbConn.getTags();
        var tagQuizesBefore = dbConn.getTags();
        var firendsbefore = dbConn.getUserFriends(1);
        var quizHistriesBefore = dbConn.getUserQuizHistory(1);
        var quizhistoryBeforeBy = dbConn.GetquizQuizHistoryAndDate(1);
        var quizhistoryBeforeBy2 = dbConn.GetquizQuizHistoryAndDate(1,1);
        var quizhistoryBeforeBy3 = dbConn.GetUserQuizHistory(1);
        var quizhsitroyBeforeBy4 = dbConn.GetUserQuizHistoryAndDate(1);
        var quizBefore = dbConn.getQuiz(1);
        var quizzesBefore = dbConn.getQuizzes();
        var quizzesBeforeBy = dbConn.getQuizzesByCreator(1);
        var qeustionBefore = dbConn.getQuizById(1);
        var answerBefore = dbConn.getAnswers(1, true);
        var answerBeforeAll = dbConn.getAnswers(1, false);

        rateAndReview rateAndReview = new rateAndReview(1, 1,1,1, "");


//        Achievement achievement = new Achievement(1, AchBody, AchToEarn);
//        dbConn.insertAchievement(achievement);
//        var achievements = dbConn.getAchievements(1);
//        int testAchId = achievements.get(achievements.size()1).getId();
//
//        Announcement announcement = new Announcement(1, AnnBody);
//        Notification notification = new Notification(1, userId, userId, NotifType, NotifBody);
//        UserAchievement userAchievement = new UserAchievement(1, userId, testAchId);
//
//        dbConn.insertAnnouncement(announcement);
//        dbConn.insertNotification(notification);
//        dbConn.insertUserAchievement(userAchievement);
//
//        var achAfter = dbConn.getAchievements(1);
//        var annAfter = dbConn.getAnnouncements();
//        var notsAfter = dbConn.getNotifications(1,1,"");
//        var userAchAfter = dbConn.getUserAchievements(userId);
//
//        assertEquals(achBefore.size() + 1, achAfter.size());
//        assertEquals(annBefore.size() + 1, annAfter.size());
//        assertEquals(notsBefore.size() + 1, notsAfter.size());
//        assertEquals(userAchBefore.size() + 1, userAchAfter.size());
//
//        var lastAch = achAfter.get(achAfter.size() - 1);
//        var lastAnn = annAfter.get(annAfter.size() - 1);
//        var lastNot = notsAfter.get(notsAfter.size() - 1);
//        var lastUsAch = userAchAfter.get(userAchAfter.size() - 1);



    }

    public void testInsertionAssertion(){
        var rateAndrevBefore = dbConn.getRateAndReviewByID(1);
        rateAndReview rateAndReview = new rateAndReview(1, 1, 1, 1, "");
        dbConn.insertRateAndReview(rateAndReview);
        var rateAndRevAfter = dbConn.getRateAndReviewByID(1);
        assertEquals(rateAndrevBefore.size(), rateAndRevAfter.size());

        var rateAndrevBeforeQuizId = dbConn.getRateAndReview(1);
        rateAndReview rateAndReviewQuizId = new rateAndReview(1, 1, 1, 1, "");
        dbConn.insertRateAndReview(rateAndReviewQuizId);
        var rateAndRevAfterQuizId = dbConn.getRateAndReview(1);
        assertEquals(rateAndrevBeforeQuizId.size() + 1, rateAndRevAfterQuizId.size());

        var userBanBefore = dbConn.getUsers(1);
        User user = new User(1, "aleko", "password_hash", "user", false);
        dbConn.insertUser(user);
        var userBanAfter = dbConn.getUsers(1);
        assertEquals(userBanBefore.size(), userBanAfter.size());

        var userBanBeforeByUser = dbConn.getUsersByUsername("aleko");
        User userByUsername = new User(1, "aleko", "password_hash", "user", false);
        dbConn.insertUser(userByUsername);
        var userBanAfterByUser = dbConn.getUsersByUsername("aleko");
        assertEquals(userBanBeforeByUser.size() + 1, userBanAfterByUser.size());
        assertTrue(dbConn.getUsersByUsername("aleko").get(0).toString().equals(dbConn.getUsersByUsername("aleko").get(0).toString()));

        var categoriesBefore = dbConn.getCategories();
        Categorya category = new Categorya(1, "NewCategory");
        dbConn.insertCategory(category);
        var categoriesAfter = dbConn.getCategories();
        assertEquals(categoriesBefore.size() + 1, categoriesAfter.size());


        var tagsBefore = dbConn.getTags();
        Tag tag = new Tag(1, "NewTag");
        dbConn.insertTag(tag);
        var tagsAfter = dbConn.getTags();
        assertEquals(tagsBefore.size() + 1, tagsAfter.size());

//        var tagQuizesBefore = dbConn.getTagQuizzes();
//        TagQuiz tagQuiz = new TagQuiz(1, 1);
//        dbConn.insertTagQuiz(tagQuiz);
//        var tagQuizesAfter = dbConn.getTagQuizzes();
//        assertEquals(tagQuizesBefore.size() + 1, tagQuizesAfter.size());


        var friendsBefore = dbConn.getUserFriends(1);
        Friend friend = new Friend(1,1, 1);
        dbConn.insertFriend(friend);
        var friendsAfter = dbConn.getUserFriends(1);
        assertEquals(friendsBefore.size() + 1, friendsAfter.size());


        var quizHistoriesBefore = dbConn.getUserQuizHistory(1);
        QuizHistory quizHistory = new QuizHistory(1, 0.0, 1, 1, 1);
        dbConn.insertQuizHistory(quizHistory);
        var quizHistoriesAfter = dbConn.getUserQuizHistory(1);
        assertEquals(quizHistoriesBefore.size() + 1, quizHistoriesAfter.size());

        var quizHistoryBeforeBy = dbConn.GetquizQuizHistoryAndDate(1);
        var quizHistoryBy2 = dbConn.GetquizQuizHistoryAndDate(1, 1);
        var quizHistoryBy3 = dbConn.GetUserQuizHistory(1);
        var quizHistoryBy4 = dbConn.GetUserQuizHistoryAndDate(1);

        var quizBefore = dbConn.getQuiz(1);
        Quiz quiz = new Quiz(1, 1, "NewQuiz", "Description", true, true, true);
        dbConn.insertQuiz(quiz);
        var quizAfter = dbConn.getQuiz(1);
        assertEquals(quizBefore.quiz_name, quizAfter.quiz_name);

        var quizzesBefore = dbConn.getQuizzes();
        Quiz quizToAdd = new Quiz(1, 1, "NewQuiz", "Description", true, true, true);
        dbConn.insertQuiz(quizToAdd);
        var quizzesAfter = dbConn.getQuizzes();
        assertEquals(quizzesBefore.size() + 1, quizzesAfter.size());

        var quizzesBeforeBy = dbConn.getQuizzesByCreator(1);
        Quiz quizToFind = quizzesAfter.get(quizzesAfter.size() - 1);
        var quizzesAfterBy = dbConn.getQuizzesByCreator(quizToFind.creator_id);
        assertEquals(quizzesBeforeBy.size(), quizzesAfterBy.size());

        var questionBefore = dbConn.getQuizById(1);
        Question question = new Question(1, 1,  "QuestionText",3,2);
        dbConn.insertQuestion(question);
        var questionAfter = dbConn.getQuizById(1);
        assertEquals(questionBefore.id, questionAfter.id);

        var answerBefore = dbConn.getAnswers(1, true);
        var answerBeforeAll = dbConn.getAnswers(1, false);
        Answer answer = new Answer(1, 1, "AnswerText", true);
        dbConn.insertAnswer(answer);
        var answerAfter = dbConn.getAnswers(1, true);
        var answerAfterAll = dbConn.getAnswers(1, false);
        assertEquals(answerBefore.size() + 1, answerAfter.size());
        assertEquals(answerBeforeAll.size() + 1, answerAfterAll.size());

        int categoryId = dbConn.getCategoryId("category1");
        assertEquals(1, categoryId);

        ArrayList<Quiz> quizzesByCategory = dbConn.getQuizzesByCategory("category1");
        assertNotNull(quizzesByCategory);

        ArrayList<Quiz> quizzesByTag = dbConn.getQuizzesByTag("History");
        assertNotNull(quizzesByTag);

        ArrayList<Quiz> quizzesByName = dbConn.getQuizzesByName("Physics Quiz");
        assertNotNull(quizzesByName);

        Quiz quiz1 = dbConn.getQuiz(1);
        assertNotNull(quiz1);

        ArrayList<Integer> bestPerformance = dbConn.getYourBestPerformance(1, 1);
        assertNotNull(bestPerformance);



        ArrayList<Question> questions = dbConn.getQuestions(1);
        assertNotNull(questions);

        Question question1 = dbConn.getQuestion(1);
        assertNotNull(question1);

        ArrayList<Answer> answers = dbConn.getAnswers(1, true);
        assertNotNull(answers);
        assertNotNull(answers.get(0).getAnswer());

        ArrayList<Quiz> quizzesByCreator = dbConn.getQuizzesByCreator(1);
        assertNotNull(quizzesByCreator);

        Quiz quizById = dbConn.getQuizById(1);
        assertNotNull(quizById);

        ArrayList<Quiz> recentlyCreatedQuizzes = dbConn.getRecentlyCreatedQuizzes(1);
        assertNotNull(recentlyCreatedQuizzes);

        ArrayList<Quiz> popularQuizzes = dbConn.getPopularQuizzes();
        assertNotNull(popularQuizzes);

        int nextQuizId = dbConn.getNextQuizId();
        assertTrue(nextQuizId > 0);

        int lastQuestionId = dbConn.getLastQuestionId();
        assertTrue(lastQuestionId > 0);

        Quiz updatedQuiz = new Quiz(1, 1, "Updated Quiz", "Updated description", true, false, false);
        dbConn.updateQuiz(updatedQuiz);
        Quiz retrievedUpdatedQuiz = dbConn.getQuiz(1);
        assertEquals("Updated Quiz", retrievedUpdatedQuiz.quiz_name);

        rateAndReview reviewToUpdate = new rateAndReview(1, 4, 1, 1,"Updated review");
        dbConn.updateRateAndReview(reviewToUpdate);

        ArrayList<Achievement> achievements = dbConn.getAchievements(-1);
        assertNotNull(achievements);

        ArrayList<Achievement> userAchievements = dbConn.getUserAchievements(1);
        assertNotNull(userAchievements);
        assertTrue(userAchievements.get(0).getId() == 1);

        ArrayList<User> allAdmins = dbConn.getAllAdmins();
        assertNotNull(allAdmins);

        ArrayList<User> allUsers = dbConn.getUsers(-1);
        assertNotNull(allUsers);

        UserBan userBan = dbConn.getUserBan(1);
        assertNull(userBan);

        dbConn.insertUserBan(new UserBan(1,1,LocalDateTime.now(),14));
//        assertTrue(dbConn.getUserBan(1).userStillBanned());

        Notification not = new Notification(1,1,1,"aige","waige");
        dbConn.insertNotification(not);
        dbConn.updateNotification(not);
        assertTrue(!dbConn.containsNotification(not).isEmpty());

        boolean isAdmin = dbConn.isAdmin(1);
        assertTrue(isAdmin);

        ArrayList<User> usersByUsername = dbConn.getUsersByUsername("testuser");
        assertNotNull(usersByUsername);

        ArrayList<rateAndReview> rateAndReviewsByID = dbConn.getRateAndReviewByID(1);
        assertNotNull(rateAndReviewsByID);

        ArrayList<rateAndReview> rateAndReviews = dbConn.getRateAndReview(1);
        assertNotNull(rateAndReviews);

        ArrayList<Friend> userFriends = dbConn.getUserFriends(1);
        assertNotNull(userFriends);

        dbConn.removeUser(99);
        dbConn.removeUserBan(99);
        dbConn.removeRateAndReview(99);

        rateAndReview reviewToRemove = new rateAndReview(1, 1, 1, 5, "Great quiz!");
        dbConn.removeExactReview(reviewToRemove);

        boolean areFriends = dbConn.areFriends(1, 2);
        assertTrue(areFriends);

        ArrayList<QuizHistory> userQuizHistory = dbConn.GetUserQuizHistory(1);
        assertNotNull(userQuizHistory);

        ArrayList<QuizHistory> userQuizHistoryAndDate = dbConn.GetUserQuizHistoryAndDate(1);
        assertNotNull(userQuizHistoryAndDate);

        ArrayList<QuizHistory> quizQuizHistoryAndDate = dbConn.GetquizQuizHistoryAndDate(1);
        assertNotNull(quizQuizHistoryAndDate);

        ArrayList<Quiz> quizzes = dbConn.getQuizzes();
        assertNotNull(quizzes);

        ArrayList<Quiz> quizzesByTag1 = dbConn.getQuizzesByTag("History");
        assertNotNull(quizzesByTag1);

        ArrayList<Quiz> quizzesByName1 = dbConn.getQuizzesByName("Test Quiz");
        assertNotNull(quizzesByName1);

        Quiz quiz2 = dbConn.getQuiz(1);
        assertNotNull(quiz2);

        TagQuiz tagQuiz = new TagQuiz(1,1,1);
        dbConn.insertTagQuiz(tagQuiz);
        assertTrue(dbConn.getQuizTags(1).contains(dbConn.getTags().stream().filter((a) ->a.id == tagQuiz.tag_id).toList().get(0).tag));
        for(int i = 1; i < 20; i++){
            User usser = new User(i, "user"+i, "pass"+i, i<8 ? "user":"admin", i >7);
            dbConn.insertUser(usser);
        }

        for(int i = 0; i < 100; i++){
            dbConn.insertCategory(new Categorya(i, "Category"+i));
        }

        for(int i = 1; i < 10; i++){
            for(int v = 0; v < 3; v++){
                Quiz quiz10 = new Quiz(i, i, "quiz_name" + i + v, "desc" + i+v, i < 5&&v%2==0, i > 5&&v%2==0, i == 5&&v%2==0);
                dbConn.insertQuiz(quiz10);

            }
        }
        for(int i = 1; i < 10; i++){
            dbConn.updateQuizCategory(i, i + 10);
        }

        for(int i = 1; i < 10; i++){
            assertTrue(dbConn.getQuizCategory(i) == i+10);
        }

        for(int i = 1; i < 3; i++){
            QuizHistory quizHistory1 = new QuizHistory(i,i*2.0,i,i,i);
            dbConn.insertQuizHistory(quizHistory1);
        }

        int tagId = dbConn.getLastTagId();
        dbConn.insertTag(new Tag(1,"ob"));
        assertTrue(tagId + 1 == dbConn.getLastTagId());
        assertTrue(dbConn.getTagId("ob") == tagId + 1);


        int quizNum = dbConn.getQuizNumCreatedByUser(1);
        assertTrue(quizNum == 8);

        Quiz quizzzz = dbConn.getQuiz(1);
        dbConn.trimQuiz(quizzzz);
        assertTrue(dbConn.getQuestions(1).isEmpty());

        for(int i = 2; i< 10; i++){
            for(int v = 0; v < 3; v++){
                String m = "quiz_name" + i + v;
                ArrayList<Quiz> quiz3 = dbConn.getQuizzesByName("quiz_name" + i + v);
                quiz3.forEach(q -> assertTrue(dbConn.getQuiz(q.id).quiz_name.equals(m)));
            }
        }

        var mockFriend = new Friend(1, 2,1);
        dbConn.insertFriend(mockFriend);

        var mockQuiz = new Quiz(1, 1, "MockQuiz", "Description", true, true, true);
        dbConn.insertQuiz(mockQuiz);

        var mockQuizHistory = new QuizHistory(1, 80.0, 1, 1, 120);
        dbConn.insertQuizHistory(mockQuizHistory);

        var mockCategory = new Categorya(1, "MockCategory");
        dbConn.insertCategory(mockCategory);

        var mockTag = new Tag(1, "MockTag");
        dbConn.insertTag(mockTag);

        var mockTagQuiz = new TagQuiz(1, 1, 1);
        dbConn.insertTagQuiz(mockTagQuiz);


        ArrayList<QuizHistory> friendsQuizHistory = dbConn.getFriendsQuizHistory(1, 1);
        assertNotNull(friendsQuizHistory);


        ArrayList<Quiz> quizzes2 = dbConn.getQuizzes();
        assertNotNull(quizzes2);


        int categoryId2 = dbConn.getCategoryId("MockCategory");
        assertEquals(104, categoryId2);


        ArrayList<Quiz> quizzesByCategory2 = dbConn.getQuizzesByCategory("MockCategory");
        assertNotNull(quizzesByCategory2);


        ArrayList<Quiz> quizzesByTag2 = dbConn.getQuizzesByTag("MockTag");
        assertNotNull(quizzesByTag2);

        dbConn.removeUser(1);
        dbConn.removeFriend(1, 2);
        dbConn.removeQuiz(1);
        dbConn.removeQuizHistory(1);

        LocalDateTime tm= LocalDateTime.now();
        UserBan userBan1 = new UserBan(1,3, tm,15);
        assertEquals(userBan1.getBan_Unitl(), tm.plusDays(15));
        assertTrue(userBan1.userStillBanned());
        UserBan userBan2 = new UserBan(1,3, tm.plusDays(15),-1);
        assertTrue(userBan2.userStillBanned());


        String s = CreateLittleStarRatings.generateRatingStars(5);
        assertTrue(s.equals("&#9733; ".repeat(5)));


        User errrrr = new User(1, "MockUser","vaivai","admin", false);
        dbConn.insertUser(errrrr);

//        Quiz errrrr2 = new Quiz(1, 1, "MockQuiz", "Description", true, true, true);
//        dbConn.insertQuiz(errrrr2);


        dbConn.removeUserQuizes(1);
        //assertTrue(dbConn.getQuizzesByCreator(1).isEmpty());


        dbConn.removeUserNotifications(1);
        assertTrue(dbConn.getNotifications(1,1,"AAAAAA").isEmpty());


        dbConn.removeUserAchievements(1);
        //assertTrue(dbConn.getUserAchievements(1).isEmpty());


        dbConn.removeQuizQuestions(1);
        assertTrue(dbConn.getQuestions(1).isEmpty());

        dbConn.removeQuizAnswers(1);
        assertTrue(dbConn.getAnswers(1, true).isEmpty());

        dbConn.removeUser(1);

        //ArrayList<Boolean> boos = AnswerChecker.checkAnswerByStringForMulties(1, new ArrayList<>(dbConn.getAnswers(1,true).stream().map(Answer::getAnswer).toList()));
//        for (Boolean b:boos
//             ) {
//            assertTrue(b);
//        }
        for(int i = 1; i< 10; i++){
            dbConn.insertQuestion(new Question(i,i,"raxdeba"+i,4,1));
        }
        //AnswerChecker.checkAnswerByString(10,"a",true,1);
        ArrayList<String> vv = new ArrayList<>();
        vv.add("lomo");
        //AnswerChecker.correctAnswerCount(1, vv);

    }

    public void testPasHasher(){
        PassHasher passHasher = new PassHasher();
        assertEquals(PassHasher.hashPassword("giorgi"), PassHasher.hashPassword("giorgi"));
        assertFalse(PassHasher.hashPassword("giorgi").equals(PassHasher.hashPassword("giorgi ")));
    }

    public void testDBGetQuizzesByName(){
        DBConn con = new DBConn();
        con.restartDBbase("./oop_proj.sql");
        ArrayList<Quiz> quiz = con.getQuizzesByName("dinozavrebi");
        assertNotNull(quiz);
        assertEquals(1,quiz.size());
        assertEquals(1,quiz.get(0).id);
        try{
            ArrayList<Quiz> quizez = con.getQuizzesByName("dinozavrebi WHERE SQL = NEW");
        } catch (Exception e) {
            assertEquals("Wrong Sql Statement", e.getMessage());
        }
    }
    public void testDBExceptions(){
        DBConn dbConn = new DBConn();

        User user = new User(1, "giro", "wayiraveba","user", true);
        dbConn.makeUserAdmin(1);

        assertTrue(dbConn.getUsers(1).get(0).isAdmin());

        try{
            dbConn.insertAchievement(null);
        } catch (Exception e) {
            assertEquals("Provided Achievement is null", e.getMessage());
        }

        try{
            dbConn.insertAnnouncement(null);
        } catch (Exception e) {
            assertEquals("Provided Announcement is null", e.getMessage());
        }

        try{

        } catch (Exception e) {
            assertEquals("Provided Announcement is null", e.getMessage());
        }

        try{
            dbConn.insertNotification(null);
        } catch (Exception e) {
            assertEquals("Provided Notification is null", e.getMessage());
        }

        try{
            dbConn.insertUserAchievement(null);
        } catch (Exception e) {
            assertEquals("Provided UserAchievement is null", e.getMessage());
        }




    }
    public void testDbQuizHistoryDB(){
        DBConn con = new DBConn();
        QuizHistory qh1 = new QuizHistory(0,30,1,1,15);
        QuizHistory qh2 = new QuizHistory(0,35,1,2,18);
        QuizHistory qh3 = new QuizHistory(0,15,1,3,11);
        QuizHistory[] histories = new QuizHistory[]{qh3,qh2,qh1};
        con.insertQuizHistory(qh1);
        con.insertQuizHistory(qh2);
        con.insertQuizHistory(qh3);
        List<Map.Entry<Integer, Double>> result = con.getLastQuizPerformers(1);
        assertEquals(3,result.size());
        int i=0;
        List <Integer> ids = result.stream().map(Map.Entry<Integer, Double>::getKey).toList();
        List <Double> scores = result.stream().map(Map.Entry<Integer, Double>::getValue).toList();
        for(QuizHistory history : histories){
            //assertTrue(ids.contains(history.getUser_id()));
        }
        for(QuizHistory history : histories){
            //assertTrue(scores.contains(history.getScore()));
        }

    }

    public void testDBHighScore(){
        DBConn con = new DBConn();
        QuizHistory qh1 = new QuizHistory(0,110,1,1,15);
        QuizHistory qh2 = new QuizHistory(0,120,1,2,18);
        QuizHistory qh3 = new QuizHistory(0,130,1,3,11);
        QuizHistory[] histories = new QuizHistory[]{qh3,qh2,qh1};
        con.insertQuizHistory(qh1);
        con.insertQuizHistory(qh2);
        con.insertQuizHistory(qh3);
        List<Map.Entry<Integer, Double>> result = con.getBestPerformance(1,true);
        assertEquals(3,result.size());

        List <Integer> ids = result.stream().map(Map.Entry<Integer, Double>::getKey).toList();
        List <Double> scores = result.stream().map(Map.Entry<Integer, Double>::getValue).toList();
        int i = 0;
        for(QuizHistory history : histories){
            assertEquals(history.getScore(),scores.get(i++));
        }
        i=0;
        ;
        for(QuizHistory history : histories){
            assertEquals(history.getUser_id(),(int)ids.get(i++));
        }
        con.restartDBbase("./oop_proj.sql");

    }

    public void testDBQuestionsAndAnswers(){
        DBConn con = new DBConn();
        con.restartDBbase("./oop_proj.sql");
        var questions = con.getQuestions(1);
        assertEquals(7,questions.size());

        assertTrue("Sauketeso Gundi msoflioshi?".equalsIgnoreCase(questions.get(1).question));
        var answer = con.getAnswers(questions.get(1).id,true);
        assertEquals(1,answer.size());
        assertTrue("Milani".equalsIgnoreCase(answer.get(0).answer));
        ArrayList<String> testAnswers = new ArrayList<>();
        testAnswers.add("Milani");
        assertTrue(AnswerChecker.checkAnswerBool(questions.get(1).id, testAnswers));
        assertEquals(AnswerChecker.checkAnswer(questions.get(1).id, testAnswers), testAnswers);

    }


//    public void testJson(){
//        String jsonString = "{\n" +
//                "  \"creator_id\": 123,\n" +
//                "  \"quiz_name\": \"Random Quiz\",\n" +
//                "  \"category\": \"Animals\",\n" +
//                "  \"description\": \"A quiz with random questions and answers\",\n" +
//                "  \"tags\": [\n" +
//                "    \"super\",\n" +
//                "    \"funny\",\n" +
//                "    \"ketchup\"\n" +
//                "  ],\n" +
//                "  \"is_single_page\": false,\n" +
//                "  \"can_be_practiced\": true,\n" +
//                "  \"rand_question_order\": false,\n" +
//                "  \"questions\": [\n" +
//                "    {\n" +
//                "      \"question_type\": 2,\n" +
//                "      \"question\": \"What is the capital of France?\",\n" +
//                "      \"answers\": [\n" +
//                "        {\n" +
//                "          \"answer\": \"Paris\",\n" +
//                "          \"is_correct\": true\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"answer\": \"London\",\n" +
//                "          \"is_correct\": false\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"answer\": \"Berlin\",\n" +
//                "          \"is_correct\": false\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"question_type\": 2,\n" +
//                "      \"question\": \"Which of the following is a mammal?\",\n" +
//                "      \"answers\": [\n" +
//                "        {\n" +
//                "          \"answer\": \"Shark\",\n" +
//                "          \"is_correct\": false\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"answer\": \"Dolphin\",\n" +
//                "          \"is_correct\": true\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"answer\": \"Salmon\",\n" +
//                "          \"is_correct\": false\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"question_type\": 5,\n" +
//                "      \"question\": \"What is 7 + 3?\",\n" +
//                "      \"answers\": [\n" +
//                "        {\n" +
//                "          \"answer\": \"10\",\n" +
//                "          \"is_correct\": true\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"answer\": \"9\",\n" +
//                "          \"is_correct\": false\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"answer\": \"12\",\n" +
//                "          \"is_correct\": false\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try{
//            JSONQuiz jsonQuiz = objectMapper.readValue(jsonString, JSONQuiz.class);
//            System.out.println(jsonQuiz.quiz_name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

public void testQuestionTypeValues() {
    assertEquals(0, QuestionType.QUESTION_RESPONSE.value);
    assertEquals(1, QuestionType.FILL_IN_THE_BLANK.value);
    assertEquals(2, QuestionType.MULTIPLE_CHOICE.value);
    assertEquals(3, QuestionType.PICTURE_RESPONSE.value);
    assertEquals(4, QuestionType.MULTI_ANSWER.value);
    assertEquals(5, QuestionType.MULTI_AN_CHOICE.value);
    assertEquals(6, QuestionType.MATCHING.value);
}

    public void testQuestionTypeFromInt() {
        assertEquals(QuestionType.QUESTION_RESPONSE, QuestionType.fromInt(0));
        assertEquals(QuestionType.FILL_IN_THE_BLANK, QuestionType.fromInt(1));
        assertEquals(QuestionType.MULTIPLE_CHOICE, QuestionType.fromInt(2));
        assertEquals(QuestionType.PICTURE_RESPONSE, QuestionType.fromInt(3));
        assertEquals(QuestionType.MULTI_ANSWER, QuestionType.fromInt(4));
        assertEquals(QuestionType.MULTI_AN_CHOICE, QuestionType.fromInt(5));
        assertEquals(QuestionType.MATCHING, QuestionType.fromInt(6));
    }
    public void testDBRecentHistory(){
        DBConn con = new DBConn();
        QuizHistory qh1 = new QuizHistory(0,110,1,3,15);
        QuizHistory qh2 = new QuizHistory(0,120,1,3,18);
        QuizHistory qh3 = new QuizHistory(0,130,1,3,11);
        QuizHistory qh4 = new QuizHistory(0,130,1,3,11);
        QuizHistory qh5 = new QuizHistory(0,130,1,3,11);
        QuizHistory[] histories = new QuizHistory[]{qh3,qh2,qh1};
        con.insertQuizHistory(qh1);
        con.insertQuizHistory(qh2);
        con.insertQuizHistory(qh3);
        con.insertQuizHistory(qh5);
        con.insertQuizHistory(qh4);
        ArrayList<QuizHistory> hist = con.getUserRecentQuizHistory(3);
        assertNotNull(hist);
        assertEquals(5,hist.size());
        con.restartDBbase("./oop_proj.sql");
    }


}


