package Quiz;

import Quiz.src.main.java.models.*;
import Quiz.src.main.java.models.enums.QuestionType;
import junit.framework.TestCase;
import java.time.LocalDateTime;

public class TestClasses extends TestCase{
    public void testAchievement() {
        Achievement achievement = new Achievement(1, "Prolific Author", "Create 5 quizzes");
        assertEquals(1, achievement.getId());
        assertEquals("Prolific Author", achievement.getAchievementBody());
        assertEquals("Create 5 quizzes", achievement.getAchievementToEarn());

        Achievement achievement1 = new Achievement(1, "Amateur Author", "Create your first quiz");
        assertNull(achievement1.getAchievementIcon());

        achievement1.setAchievementIcon("icon.png");
        assertEquals("icon.png", achievement1.getAchievementIcon());
    }

    public void testAnnouncement() {
        Announcement announcement = new Announcement(3, "Welcome to the quiz page!");
        assertEquals(3, announcement.getId());
        assertEquals("Welcome to the quiz page!", announcement.getAnnouncementBody());
    }

    public void testAnswer() {
        Answer answer = new Answer(1, 2, "A", true);
        assertEquals(1, answer.getId());
        assertEquals(2, answer.question_id);
        assertEquals("A", answer.answer);
        assertTrue(answer.isCorrect);

        Answer answer1 = new Answer(1, 2, "B", false);
        assertEquals("Answer{answer='B', isCorrect=false}", answer1.toString());
        assertFalse(answer1.question_id == 3);
        assertEquals("B", answer1.getAnswer());
    }

    public void testCategorya() {
        Categorya categorya = new Categorya(1, "OOP");
        assertEquals(1, categorya.id);
        assertEquals("OOP", categorya.category);
    }

    public void testFriend() {
        Friend friend = new Friend(1, 1, 2);
        assertEquals(1, friend.getId());
        assertEquals(1, friend.getUser_id());
        assertEquals(2, friend.getFriend_id());
        assertFalse(friend.getFriend_id() == 3);
    }

    public void testNotification() {
        Notification notification = new Notification(1, 1, 2, "Friend Request", "You have a new friend request.");
        assertEquals(1, notification.getId());
        assertEquals(1, notification.getReceiverId());
        assertEquals(2, notification.getSenderId());
        assertEquals("Friend Request", notification.getNotifType());
        assertFalse(notification.getNotifType().equals("Challenge"));
        assertEquals("You have a new friend request.", notification.getNotifBody());
    }

    public void testIsMultiAnswerType() {
        Question questionResponse = new Question(1, 1, "What is the capital of Georgia?", 0, 1);
        Question multiAnswer = new Question(2, 1, "Choose the correct answer.", 4, 2);
        Question fillInTheBlank = new Question(3, 1, "Fill in the blank.", 1, 3);
        Question pictureResponse = new Question(4, 1, "Picture & Response", 3, 4);

        assertFalse(questionResponse.isMultiAnswerType());
        assertTrue(multiAnswer.isMultiAnswerType());
        assertTrue(fillInTheBlank.isMultiAnswerType());
        assertFalse(pictureResponse.isMultiAnswerType());

        Question question1 = new Question(1, 1, "Question 1", 1, 1);
        Question question2 = new Question(1, 2, "Question 2", 2, 2);
        Question question3 = new Question(3, 1, "Question 3", 3, 3);

        assertEquals(question1, question2);
        assertFalse(question1.equals(question3));

        assertEquals(question1.hashCode(), question2.hashCode());
        assertFalse(question1.hashCode() == question3.hashCode());
    }

    public void testQuiz() {
        Quiz quiz = new Quiz(1, 11, "Quiz1", "Testing quiz", true, false, true);

        assertEquals(1, quiz.id);
        assertEquals(11, quiz.creator_id);
        assertEquals("Quiz1", quiz.quiz_name);
        assertEquals("Testing quiz", quiz.description);
        assertTrue(quiz.is_single_page);
        assertFalse(quiz.can_be_practiced);
        assertTrue(quiz.rand_question_order);
    }

    public void testQuizHistory() {
        QuizHistory quizHistory = new QuizHistory(1, 7.5, 1, 1, 10);
        LocalDateTime takeDate = LocalDateTime.now();
        quizHistory.setTakeDate(takeDate);
        assertEquals(1, quizHistory.getId());
        assertEquals(7.5, quizHistory.getScore());
        assertEquals(1, quizHistory.getQuiz_id());
        assertEquals(1, quizHistory.getUser_id());
        assertEquals(10, quizHistory.getTime_taken());
        assertEquals(takeDate, quizHistory.getTakeDate());

        QuizHistory quizHistory2 = new QuizHistory(2, 7, 2, 2, 23);
        Quiz quiz = new Quiz(2, 2, "Test Quiz", "Testing", true, false, true);
        quizHistory2.setQuiz(quiz);
        assertTrue(quiz.equals(quizHistory2.getQuiz()));
    }

    public void testRateAndReview() {
        rateAndReview r = new rateAndReview(1,1,1,5,"Good quiz");
        assertEquals(1, r.id);
        assertEquals(1, r.quizId);
        assertEquals(1, r.userId);
        assertEquals(5, r.rating);
        assertEquals("Good quiz", r.review);
    }

    public void testTag() {
        Tag t = new Tag(1,"Test");
        assertEquals(1, t.id);
        assertEquals("Test", t.tag);
        assertFalse(t.tag.equals("test"));
    }

    public void testTagQuiz() {
        TagQuiz t = new TagQuiz(1, 2, 3);
        assertEquals(1, t.id);
        assertEquals(2, t.tag_id);
        assertEquals(3, t.quiz_id);
    }

    public void testUser() {
        int id = 1;
        String username = "testUser";
        String passwordHash = "hashedPassword";
        String role = "user";
        boolean isPrivate = true;

        User testUser = new User(id, username, passwordHash, role, isPrivate);

        assertEquals(id, testUser.getId());
        assertEquals(username, testUser.getUsername());
        assertEquals(passwordHash, testUser.getPasswordHash());
        assertEquals(role, testUser.getRole());
        assertTrue(testUser.isPrivate());

        User admin = new User(1, "admin", "adminpass", "admin", false);
        assertTrue(admin.isAdmin());

        User user = new User(2, "user", "userpass", "user", false);
        assertFalse(user.isAdmin());

        user.setPfpLink("Profile Picture");
        assertFalse(user.getPfpLink().equals(null));
        assertTrue(user.getPfpLink().equals("Profile Picture"));
    }

    public void testUserAchievement() {
        UserAchievement userAchievement = new UserAchievement(1, 1, 3);
        assertEquals(1, userAchievement.getId());
        assertEquals(1, userAchievement.getUserId());
        assertEquals(3, userAchievement.getAchievementId());
    }

    public void testQuestionType() {
        int validValue = 2;
        QuestionType questionType = QuestionType.fromInt(validValue);
        assertEquals(QuestionType.MULTIPLE_CHOICE, questionType);

        int invalidValue = 7;
        try {
            QuestionType.fromInt(invalidValue);
        } catch(IllegalArgumentException e) {
            assertEquals(" 7", e.getMessage());
        }
    }
}
