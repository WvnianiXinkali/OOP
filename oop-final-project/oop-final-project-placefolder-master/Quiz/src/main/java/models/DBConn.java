package Quiz.src.main.java.models;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DBConn{
    private static final String account = "oopUser";
    private static final String password = "oopUserPasswrd";
    private static final String server = "localhost";
    private static final String database = "oop_proj";

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public DBConn() {
        try{
            conn = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
            stmt = conn.createStatement();

            executeUpdate("USE " + database);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartDBbase(String filePath){
        try {
            String sqlContent = new String(Files.readAllBytes(Paths.get(filePath)));

            String[] sqlStatements = sqlContent.split(";");
            for (String statement : sqlStatements) {
                executeUpdate(statement);
            }

            System.out.println("Database restarted successfully.");
        } catch (Exception e) {
            System.out.println("Error restarting database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeDBConn() {
        try {
            if(rs != null)
                rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    private void executeQuery(String q) {
        try{
            System.out.println(String.format("Executing querry: %s", q));

            rs = stmt.executeQuery(q);
        } catch (Exception e){
            System.out.println("query: " + q + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeUpdate(String u) {
        try{
            System.out.println(String.format("Executing update: %s", u));
            stmt.executeUpdate(u);
        } catch (Exception e){
            System.out.println("update: " + u + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertNotification(Notification n){
        if(n == null)
            throw new RuntimeException("Provided Notification is null");

        String q = String.format("INSERT INTO notifications (receiver_id, sender_id, notif_type, notif_body)  VALUES(%d, %d, '%s', '%s')", n.getReceiverId(), n.getSenderId(), n.getNotifType(), n.getNotifBody());
        executeUpdate(q);
    }

    public void insertRateAndReview(rateAndReview r){
        if(r == null)
            throw new RuntimeException("Provided insertRateAndReview is null");

        String q = String.format("INSERT INTO rateAndReview (quiz_id, rating, user_id, review)  VALUES(%d, %d, %d, '%s')", r.quizId, r.rating, r.userId, r.review);
        executeUpdate(q);
    }

    public void insertUserBan(UserBan u){
        if(u == null)
            throw new RuntimeException("Provided userBan is null");

        String query = "INSERT INTO userBan (user_id, ban_until) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, u.user_id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(u.getBan_Unitl()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executeUpdate(query);
    }

    public void insertCategory(Categorya c){
        if(c == null)
            throw new RuntimeException("Provided Category is null");

        String q = String.format("INSERT INTO quiz_categories (category)  VALUES('%s')", c.category);
        executeUpdate(q);
    }
    public void insertTag(Tag t){
        if(t == null)
            throw new RuntimeException("Provided Tag is null");

        String q = String.format("INSERT INTO quiz_tags (tag)  VALUES('%s')", t.tag);
        executeUpdate(q);
    }

    public void insertTagQuiz(TagQuiz t){
        if(t == null)
            throw new RuntimeException("Provided TagQuiz is null");

        String q = String.format("INSERT INTO tag_quiz (quiz_id, tag_id)  VALUES(%d, %d)", t.quiz_id, t.tag_id);
        executeUpdate(q);
    }

    public void insertAnnouncement(Announcement a){
        if(a == null)
            throw new RuntimeException("Provided Announcement is null");

        String q = String.format("INSERT INTO announcements (announcement)  VALUES('%s')", a.getAnnouncementBody());
        executeUpdate(q);
    }

    public void insertAchievement(Achievement a){
        if(a == null)
            throw new RuntimeException("Provided Achievement is null");

        String q = String.format("INSERT INTO achievements (achievement, to_earn)  VALUES('%s', '%s')", a.getAchievementBody(), a.getAchievementToEarn());
        executeUpdate(q);
    }

    public void insertUserAchievement(UserAchievement u){
        if(u == null)
            throw new RuntimeException("Provided UserAchievement is null");

        String q = String.format("INSERT INTO user_achievements (user_id, achievement_id)  VALUES(%d, %d)", u.getUserId(), u.getAchievementId());
        executeUpdate(q);
    }

    public void insertUser(User u){
        if(u == null)
            throw new RuntimeException("Provided UserAchievement is null");

        String q = String.format("INSERT INTO users (username, password_hash, role, isPrivate)  VALUES('%s', '%s', '%s', %b)", u.getUsername(), u.getPasswordHash(), u.getRole(), false);
        executeUpdate(q);
    }

    public void insertFriend(Friend f){
        if(f == null)
            throw new RuntimeException("Provided Friend is null");

        String q = String.format("INSERT INTO friends (user_id, friend_id)  VALUES(%d, %d)", f.getUser_id(), f.getFriend_id());
        executeUpdate(q);
    }

    public void insertQuizHistory(QuizHistory qh) {
        if (qh == null) {
            throw new RuntimeException("Provided Quiz History is null");
        }

        String query = "INSERT INTO quiz_history (score, quiz_id, user_id, time_taken, take_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDouble(1, qh.getScore());
            preparedStatement.setInt(2, qh.getQuiz_id());
            preparedStatement.setInt(3, qh.getUser_id());
            preparedStatement.setInt(4, qh.getTime_taken());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertQuiz(Quiz qz){
        if(qz == null)
            throw new RuntimeException("Provided Quiz is null");

        String q = String.format("INSERT INTO quizzes(creator_id, quiz_name, description, is_single_page, can_be_practiced, rand_question_order) VALUES(%d, '%s', '%s', %b, %b, %b)", qz.creator_id, qz.quiz_name, qz.description, qz.is_single_page, qz.can_be_practiced, qz.rand_question_order);
        executeUpdate(q);
    }

    public void insertQuestion(Question qu){
        if(qu == null)
            throw new RuntimeException("Provided Question is null");

        String q = String.format("INSERT INTO questions(question_num, quiz_id, question_type, question) VALUES(%d, %d, %d, '%s')", qu.num, qu.quiz_id, qu.type.value, qu.question);
        executeUpdate(q);
    }

    public void insertAnswer(Answer ans){
        if(ans == null)
            throw new RuntimeException("Provided Question is null");

        String q = String.format("INSERT INTO answers(question_id, answer, is_correct) VALUES(%d, '%s', %b)", ans.question_id, ans.answer, ans.isCorrect);
        executeUpdate(q);
    }

    public void updateNotification(Notification n){
        int nId = n.getId();
        int targetId = n.getReceiverId();
        int senderId = n.getSenderId();
        String notifType = n.getNotifType();
        String notifBody = n.getNotifBody();

        String q = "UPDATE notifications n\n" +
                "SET " + String.format("n.receiver_id = %d, n.sender_id = %d, n.notif_type = '%s', n.notif_body = '%s'", targetId, senderId, notifType, notifBody) +
                "WHERE " + String.format("n.id = %d", nId);
        executeUpdate(q);
    }



    public ArrayList<Notification> getNotifications(int receiverId, int senderId, String notifType) {
        String q = "SELECT * FROM notifications n";

        boolean needsAnd = false;

        if(receiverId != -1 || senderId != -1 || notifType != ""){
            q += " WHERE";
        }
        if(receiverId != -1){
            q += String.format(" n.receiver_id = %d", receiverId);
            needsAnd = true;
        }
        if(senderId != -1){
            if(needsAnd){
                q += " AND";
            }
            q += String.format(" n.sender_id = %d", senderId);
            needsAnd = true;
        }
        if(notifType != ""){
            if(needsAnd){
                q += " AND";
            }
            q += String.format(" n.notif_type = '%s'", notifType);
        }
        q += " ORDER BY n.id DESC";

        ArrayList<Notification> selection = new ArrayList<>();

        try{
            executeQuery(q);

            while (rs.next()) {
                Notification notification = new Notification(rs.getInt("id"),
                        rs.getInt("receiver_id"),
                        rs.getInt("sender_id"),
                        rs.getString("notif_type"),
                        rs.getString("notif_body"));
                selection.add(notification);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

    public ArrayList<Notification> containsNotification(Notification n) {
        String q = String.format("SELECT * FROM notifications n where receiver_id = %d &&" +
                "sender_id = %d && notif_type = '%s' && notif_body = '%s'", n.getReceiverId(), n.getSenderId(), n.getNotifType(),n.getNotifBody());

        ArrayList<Notification> notifications = new ArrayList<>();
        try{
            executeQuery(q);

            while (rs.next()) {
                Notification qh = new Notification(rs.getInt("id"), rs.getInt("receiver_id"), rs.getInt("sender_id"), rs.getString("notif_type"), rs.getString("notif_body"));
                notifications.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return notifications;
    }

    public ArrayList<Announcement> getAnnouncements() {
        String q = "SELECT * FROM announcements";
        ArrayList<Announcement> selection = new ArrayList<>();

        try{
            executeQuery(q);

            while (rs.next()) {
                Announcement announcement = new Announcement(rs.getInt("id"), rs.getString("announcement"));
                selection.add(announcement);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }
    public ArrayList<QuizHistory> getUserRecentQuizHistory(int user_id) {
        String query = "SELECT * FROM quiz_history ORDER BY id DESC LIMIT 5";
        if(user_id != -1){
            query = String.format("SELECT * FROM quiz_history q where q.user_id = %d ORDER BY id DESC LIMIT 5", user_id);
        }

        ArrayList<QuizHistory> quizHistory = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                QuizHistory qh = new QuizHistory(rs.getInt("id"), rs.getDouble("score"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("time_taken"));
                quizHistory.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return quizHistory;
    }
    public ArrayList<QuizHistory> getUserQuizHistory(int user_id) {
        String query = "SELECT * FROM quiz_history";
        if(user_id != -1){
            query = String.format("SELECT * FROM quiz_history q where q.user_id = %d", user_id);
        }

        ArrayList<QuizHistory> quizHistory = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                QuizHistory qh = new QuizHistory(rs.getInt("id"), rs.getDouble("score"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("time_taken"));
                quizHistory.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return quizHistory;
    }

    public ArrayList<Achievement> getAchievements(int id) {
        String q = "SELECT * FROM achievements";
        if(id != -1){
            q = String.format("SELECT * FROM achievements a where a.id = %d", id);
        }

        ArrayList<Achievement> selection = new ArrayList<>();
        try{
            executeQuery(q);

            while (rs.next()) {
                Achievement achievement = new Achievement(rs.getInt("id"), rs.getString("achievement"), rs.getString("to_earn"));
                achievement.setAchievementIcon(rs.getString("icon"));
                selection.add(achievement);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

    public ArrayList<Achievement> getUserAchievements(int user_id) {
        String q = String.format("SELECT a.id, a.achievement, a.to_earn, a.icon \n" +
                "FROM user_achievements u JOIN achievements a ON(u.achievement_id = a.id) \n" +
                "WHERE u.user_id = %d;", user_id);

        ArrayList<Achievement> selection = new ArrayList<>();
        try{
            executeQuery(q);

            while (rs.next()) {
                Achievement userAchievement = new Achievement(rs.getInt("id"), rs.getString("achievement"), rs.getString("to_earn"));
                userAchievement.setAchievementIcon(rs.getString("icon"));
                selection.add(userAchievement);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

    public ArrayList<User> getAllAdmins() {
        String query = "SELECT * FROM users u where u.role = 'admin'";

        ArrayList<User> selection = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"), rs.getString("role"), rs.getBoolean("isPrivate"));
                user.setPfpLink(rs.getString("pfp"));
                selection.add(user);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }


    public ArrayList<User> getUsers(int id) {
        String query = "SELECT * FROM users";
        if(id != -1){
            query = String.format("SELECT * FROM users u where u.id = %d", id);
        }

        ArrayList<User> selection = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"), rs.getString("role"), rs.getBoolean("isPrivate"));
                user.setPfpLink(rs.getString("pfp"));
                selection.add(user);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

    public UserBan getUserBan(int userId) {
        String query = "SELECT * FROM UserBan";
        if(userId != -1){
            query = String.format("SELECT * FROM UserBan u where u.user_id = %d", userId);
        }

        UserBan userBan = null;
        try{
            executeQuery(query);

            if(rs.next()){
                userBan = new UserBan(rs.getInt("id"), rs.getInt("user_id"),rs.getTimestamp("ban_until").toLocalDateTime(),-1);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return userBan;
    }

    public boolean isAdmin(int id) {
        String query = "SELECT * FROM users";
        if(id != -1){
            query = String.format("SELECT * FROM users u where u.id = %d", id);
        }

        ArrayList<User> selection = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"), rs.getString("role"), rs.getBoolean("isPrivate"));
                user.setPfpLink(rs.getString("pfp"));
                selection.add(user);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if (selection.isEmpty()){
            return false;
        }
        return selection.get(0).isAdmin();
    }

    public ArrayList<User> getUsersByUsername(String username) {
        String query = "SELECT * FROM users";
        query = String.format("SELECT * FROM users u where u.username = '%s'", username);

        ArrayList<User> selection = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"), rs.getString("role"), rs.getBoolean("isPrivate"));
                user.setPfpLink(rs.getString("pfp"));
                selection.add(user);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

    public ArrayList<rateAndReview> getRateAndReviewByID(int reviewId) {
        String query = String.format("SELECT * FROM rateAndReview u where u.id = %d", reviewId);

        ArrayList<rateAndReview> selection = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                rateAndReview user = new rateAndReview(rs.getInt("id"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("rating"), rs.getString("review"));
                selection.add(user);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

    public ArrayList<rateAndReview> getRateAndReview(int quizId) {
        String query = "SELECT * FROM rateAndReview";
        if(quizId != -1){
            query = String.format("SELECT * FROM rateAndReview u where u.quiz_id = %d", quizId);
        }

        ArrayList<rateAndReview> selection = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                rateAndReview user = new rateAndReview(rs.getInt("id"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("rating"), rs.getString("review"));
                selection.add(user);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return selection;
    }

//    public boolean areFriends(int user_id, int target_id) {
//        ArrayList<Friend> friends = getUserFriends(user_id);
//        List<Integer> friend_id = friends.stream().map(Friend::getFriend_id).toList();
//        if(friend_id.contains(target_id)) return true;
//        return false;
//    }

    public ArrayList<Friend> getUserFriends(int user_id) {
        String query = "SELECT * FROM friends";
        if(user_id != -1){
            query = String.format("SELECT * FROM friends f where f.user_id = %d", user_id);
        }

        ArrayList<Friend> friends = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                Friend friend = new Friend(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("friend_id"));
                friends.add(friend);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return friends;
    }

    public void removeUser(int user_id){
        String q = String.format("DELETE FROM users u WHERE (u.id = %d)", user_id);
        executeUpdate(q);
    }

    public void removeUserBan(int userId){
        String q = String.format("DELETE FROM userBan u WHERE (u.user_id = %d)", userId);
        executeUpdate(q);
    }

    public void removeRateAndReview(int revId){
        String q = String.format("DELETE FROM rateAndReview r WHERE (r.id = %d)", revId);
        executeUpdate(q);
    }

    public void removeExactReview(rateAndReview review){
        String q = String.format("DELETE FROM rateAndReview r WHERE (r.user_id = %d) &&" +
                                    "(r.quiz_id = %d) && (r.rating = %d) && (r.review = '%s')",
                                    review.userId, review.quizId, review.rating, review.review);
        executeUpdate(q);
    }

    public void removeQuiz(int quiz_id){
        String q = String.format("DELETE FROM quizzes q WHERE (q.id = %d)", quiz_id);
        executeUpdate(q);
    }

    public void makeUserAdmin(int user_id){
        String q = String.format("UPDATE users SET role = 'admin' WHERE id = %d;", user_id);
        executeUpdate(q);
    }

    public void makeUserPrivate(int user_id, boolean priv){
        String q = String.format("UPDATE users SET isPrivate = %b WHERE id = %d;", priv, user_id);
        executeUpdate(q);
    }

    public void updateUserPicture(int user_id, String pfp){
        String q = String.format("UPDATE users SET pfp = '%s' WHERE id = %d;", pfp, user_id);
        executeUpdate(q);
    }

    public void removeUserQuizes(int user_id){
        String q = String.format("DELETE FROM quizzes q WHERE (q.creator_id = %d)", user_id);
        executeUpdate(q);
    }

    public void removeUserNotifications(int user_id){
        String q = String.format("DELETE FROM notifications n WHERE (n.receiver_id = %d) OR (n.sender_id = %d)", user_id, user_id);
        executeUpdate(q);
    }

    public void removeUserAchievements(int user_id){
        String q = String.format("DELETE FROM user_achievements a WHERE (a.user_id = %d)", user_id);
        executeUpdate(q);
    }

    public void removeQuizQuestions(int quiz_id){
        String q = String.format("DELETE FROM questions q WHERE (q.quiz_id = %d)", quiz_id);
        executeUpdate(q);
    }

    public void removeQuizAnswers(int quiz_id){
        String q = String.format("DELETE FROM answers q WHERE (q.question_id = %d)", quiz_id);
        executeUpdate(q);
    }

    public void removeQuizHistory(int quiz_id){
        String q = String.format("DELETE FROM quiz_history q WHERE (q.quiz_id = %d)", quiz_id);
        executeUpdate(q);
    }

    public void removeFriend(int user_id, int friend_id){
        String q = String.format("DELETE FROM friends WHERE (user_id = %d AND friend_id = %d) OR (user_id = %d AND friend_id = %d)", user_id, friend_id, friend_id, user_id);
        executeUpdate(q);
    }

    public boolean areFriends(int user_id, int friend_id){
        String q = "SELECT COUNT(f.friend_id) AS are_friends\n" +
                "FROM friends f \n" +
                "WHERE " + String.format("f.user_id = %d AND f.friend_id = %d", user_id, friend_id);
        try{
            executeQuery(q);
            rs.next();

            int friends = rs.getInt("are_friends");
            System.out.println(friends);

            return friends > 0 ? true : false;
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<QuizHistory> GetUserQuizHistory(int user_id) {
        String query = "SELECT qh.id,\n" +
                              "qh.score, \n" +
                              "qh.quiz_id, \n" +
                              "qh.time_taken, \n" +
                              "q.creator_id,\n" +
                              "q.quiz_name,\n" +
                              "q.is_single_page,\n" +
                              "q.can_be_practiced,\n" +
                              "q.rand_question_order,\n" +
                              "q.description,\n" +
                              "u.username\n" +
                       "FROM quiz_history qh JOIN quizzes q ON(qh.quiz_id = q.id) JOIN users u ON(q.creator_id = u.id)";
        if(user_id != -1){
            query += String.format(" WHERE qh.user_id = %d", user_id);
        }
        query += " ORDER BY qh.id DESC";

        ArrayList<QuizHistory> quizHistory = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                QuizHistory qh = new QuizHistory(rs.getInt("id"), rs.getDouble("score"), rs.getInt("quiz_id"), rs.getInt("creator_id"), rs.getInt("time_taken"));
                Quiz q = new Quiz(qh.getQuiz_id(), rs.getInt("creator_id"), rs.getString("quiz_name"), rs.getString("description"), rs.getBoolean("is_single_page"), rs.getBoolean("can_be_practiced"), rs.getBoolean("rand_question_order"));
                q.creatorName = rs.getString("username");
                qh.setQuiz(q);
                quizHistory.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return quizHistory;
    }

    public ArrayList<QuizHistory> GetUserQuizHistoryAndDate(int user_id) {
        String query = "SELECT qh.id,\n" +
                "qh.score, \n" +
                "qh.quiz_id, \n" +
                "qh.time_taken, \n" +
                "qh.take_date, \n" +
                "q.creator_id,\n" +
                "q.quiz_name,\n" +
                "q.is_single_page,\n" +
                "q.can_be_practiced,\n" +
                "q.rand_question_order,\n" +
                "q.description,\n" +
                "u.username\n" +
                "FROM quiz_history qh JOIN quizzes q ON(qh.quiz_id = q.id) JOIN users u ON(q.creator_id = u.id)";
        if(user_id != -1){
            query += String.format(" WHERE qh.user_id = %d", user_id);
        }
        query += " ORDER BY qh.id DESC";

        ArrayList<QuizHistory> quizHistory = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                QuizHistory qh = new QuizHistory(rs.getInt("id"), rs.getDouble("score"), rs.getInt("quiz_id"), rs.getInt("creator_id"), rs.getInt("time_taken"));
                if(rs.getTimestamp("take_date")!= null)
                qh.setTakeDate(rs.getTimestamp("take_date").toLocalDateTime());
                Quiz q = new Quiz(qh.getQuiz_id(), rs.getInt("creator_id"), rs.getString("quiz_name"), rs.getString("description"), rs.getBoolean("is_single_page"), rs.getBoolean("can_be_practiced"), rs.getBoolean("rand_question_order"));
                q.creatorName = rs.getString("username");
                qh.setQuiz(q);
                quizHistory.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return quizHistory;
    }

    public ArrayList<QuizHistory> GetquizQuizHistoryAndDate(int quiz_id) {
        String query = "SELECT * FROM quiz_history";
        if(quiz_id != -1){
            query = String.format("SELECT * FROM quiz_history q where q.quiz_id = %d", quiz_id);
        }

        ArrayList<QuizHistory> quizHistory = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                QuizHistory qh = new QuizHistory(rs.getInt("id"), rs.getDouble("score"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("time_taken"));
                qh.setTakeDate(rs.getTimestamp("take_date").toLocalDateTime());
                quizHistory.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return quizHistory;
    }

    public ArrayList<QuizHistory> getFriendsQuizHistory(int user_id, int quiz_id){
        ArrayList<Friend> friends = getUserFriends(user_id);
        ArrayList<QuizHistory> quizHistories = GetquizQuizHistoryAndDate(quiz_id);
        Set<Integer> friendUserIds = new HashSet<>();
        for (Friend friend : friends) {
            friendUserIds.add(friend.getFriend_id());
        }
        List<QuizHistory> friendQuizHistories = quizHistories.stream()
                .filter(history -> friendUserIds.contains(history.getUser_id()))
                .toList();
        return new ArrayList<>(friendQuizHistories);
    }

    public ArrayList<QuizHistory> GetquizQuizHistoryAndDate(int quiz_id, int user_id) {
        String query = "SELECT * FROM quiz_history";
        if(quiz_id != -1){
            query = String.format("SELECT * FROM quiz_history q where q.quiz_id = %d", quiz_id);
        }

        ArrayList<QuizHistory> quizHistory = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                QuizHistory qh = new QuizHistory(rs.getInt("id"), rs.getDouble("score"), rs.getInt("quiz_id"), rs.getInt("user_id"), rs.getInt("time_taken"));
                qh.setTakeDate(rs.getTimestamp("take_date").toLocalDateTime());
                quizHistory.add(qh);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return quizHistory;
    }



    public ArrayList<Quiz> getQuizzes(){
        String quizzesQuery = "SELECT * FROM quizzes;";
        ArrayList<Quiz> selection = new ArrayList<>();
        try{
            executeQuery(quizzesQuery);

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("id"),
                        rs.getInt("creator_id"),
                        rs.getString("quiz_name"),
                        rs.getString("description"),
                        rs.getBoolean("is_single_page"),
                        rs.getBoolean("can_be_practiced"),
                        rs.getBoolean("rand_question_order")
                );
                selection.add(quiz);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return selection;
    }

    public int getCategoryId(String category){
        String quizzesQuery = String.format("SELECT * FROM quiz_categories where category = '%s';", category);
        int id = -1;
        try{
            executeQuery(quizzesQuery);
            rs.next();

            id = rs.getInt("id");
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Quiz> getQuizzesByCategory(String category){
        int categoryId = getCategoryId(category);
        String quizzesQuery = String.format("SELECT * FROM quizzes where category_id = %d;", categoryId);
        ArrayList<Quiz> selection = new ArrayList<>();
        try{
            executeQuery(quizzesQuery);

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("id"),
                        rs.getInt("creator_id"),
                        rs.getString("quiz_name"),
                        rs.getString("description"),
                        rs.getBoolean("is_single_page"),
                        rs.getBoolean("can_be_practiced"),
                        rs.getBoolean("rand_question_order")
                );
                selection.add(quiz);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return selection;
    }

    public ArrayList<Quiz> getQuizzesByTag(String tag){
        String quizzesQuery = String.format("SELECT q.* FROM quizzes q JOIN tag_quiz tq ON q.id = tq.quiz_id\n" +
                "                JOIN quiz_tags qt ON tq.tag_id = qt.id\n" +
                "                WHERE qt.tag = '%s';", tag);
        ArrayList<Quiz> selection = new ArrayList<>();
        try{
            executeQuery(quizzesQuery);

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("id"),
                        rs.getInt("creator_id"),
                        rs.getString("quiz_name"),
                        rs.getString("description"),
                        rs.getBoolean("is_single_page"),
                        rs.getBoolean("can_be_practiced"),
                        rs.getBoolean("rand_question_order")
                );
                selection.add(quiz);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return selection;
    }

    public ArrayList<Quiz> getQuizzesByName(String quizName){
        String quizzesQuery = String.format("SELECT * FROM quizzes q WHERE q.quiz_name = '%s';", quizName);
        ArrayList<Quiz> selection = new ArrayList<>();
        try{
            executeQuery(quizzesQuery);

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("id"),
                        rs.getInt("creator_id"),
                        rs.getString("quiz_name"),
                        rs.getString("description"),
                        rs.getBoolean("is_single_page"),
                        rs.getBoolean("can_be_practiced"),
                        rs.getBoolean("rand_question_order")
                );
                selection.add(quiz);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return selection;
    }
    public Quiz getQuiz(int id){
        String quizzesQuery = String.format("SELECT * FROM quizzes WHERE id = %d",id);
        Quiz result = null;
        try{
            executeQuery(quizzesQuery);
            rs.next();
             result = new Quiz(rs.getInt("id"),
                    rs.getInt("creator_id"),
                    rs.getString("quiz_name"),
                    rs.getString("description"),
                    rs.getBoolean("is_single_page"),
                    rs.getBoolean("can_be_practiced"),
                     rs.getBoolean("rand_question_order")
                );

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return result;
    }

    public ArrayList<Integer> getYourBestPerformance(int quiz_id,int userid){
        String query=String.format("SELECT score " +
                "FROM quiz_history " +
                "WHERE quiz_id = %d AND user_id = %d " +
                "ORDER BY score DESC " +
                "LIMIT 3;",quiz_id,userid);

        ArrayList<Integer> scores = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                int result = rs.getInt("score");;
                scores.add(result);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return scores;
    }
    public List<Map.Entry<Integer, Double>> getLastQuizPerformers(int quiz_id){
        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        String query = String.format("SELECT user_id, score " +
                "FROM quiz_history WHERE quiz_id = %d " +
                "ORDER BY take_date DESC " +
                "LIMIT 3;", quiz_id);

        ArrayList<Integer> users = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                int us_id = rs.getInt("user_id");
                System.out.println(us_id);
                double score = rs.getDouble("score");
                result.add(new AbstractMap.SimpleEntry<>(us_id,score));
            }

        } catch (Exception e){System.out.println(e.getMessage());e.printStackTrace();return  null;}
        return result;
    }
    public List<Map.Entry<Integer, Double>> getBestPerformance(int quiz_id,boolean today){
        String query;
        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        if (today){
            query = String.format("SELECT user_id, MAX(score) AS max_score " +
                    "FROM quiz_history " +
                    "WHERE  DATE(take_date) = CURDATE() AND quiz_id = %d " +
                    "GROUP BY user_id " +
                    "ORDER BY max_score DESC " +
                    "LIMIT 3;",quiz_id);
        }else{
            query = String.format("SELECT user_id, MAX(score) AS max_score " +
                    "FROM quiz_history " +
                    "WHERE   quiz_id = %d  " +
                    "GROUP BY user_id " +
                    "ORDER BY max_score DESC " +
                    "LIMIT 3;",quiz_id);

        }

        try{
            executeQuery(query);

            while (rs.next()) {
                int us_id = rs.getInt("user_id");
                double score = rs.getDouble("max_score");
                result.add(new AbstractMap.SimpleEntry<>(us_id,score));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return result;
    }
    public ArrayList<Question> getQuestions(int quiz_id){
        String questionQuery = String.format("SELECT * FROM questions where quiz_id = %d  ORDER BY question_num;",quiz_id);
        ArrayList<Question> selection = new ArrayList<>();
        try{
            executeQuery(questionQuery);

            while (rs.next()) {
                Question question  = new Question(rs.getInt("id"),
                        rs.getInt("quiz_id"),
                        rs.getString("question"),
                        rs.getInt("question_type"),
                        rs.getInt("question_num")
                );
                selection.add(question);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return selection;
    }
    public Question getQuestion(int question_id){
        String questionQuery = String.format("SELECT * FROM questions where id = %d ;",question_id);
        Question selection;
        try{
            executeQuery(questionQuery);
                rs.next();
                Question question  = new Question(rs.getInt("id"),
                        rs.getInt("quiz_id"),
                        rs.getString("question"),
                        rs.getInt("question_type"),
                        rs.getInt("question_num"));
                selection = question;

        } catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return selection;
    }
    public ArrayList<Answer> getAnswers( int question_id,boolean onlyCorrect){
        String questionQuery;
        if(onlyCorrect){
             questionQuery = String.format("SELECT * FROM answers where question_id = %d AND is_correct = TRUE;",question_id);
        }else{
            questionQuery = String.format("SELECT * FROM answers where question_id = %d",question_id);
        }

        ArrayList<Answer> selection = new ArrayList<>();
        try{
            executeQuery(questionQuery);

            while (rs.next()) {
                Answer answer  = new Answer(rs.getInt("id"),
                        rs.getInt("question_id"),
                        rs.getString("answer"),
                        rs.getBoolean("is_correct")
                );
                selection.add(answer);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return  null;
        }
        return selection;
    }


    public ArrayList<Quiz> getQuizzesByCreator(int creator_id){
        String query = String.format("SELECT * FROM quizzes q where q.creator_id = %d", creator_id);

        ArrayList<Quiz> createdQuizzes = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                Quiz q = new Quiz(rs.getInt("id"), rs.getInt("creator_id"), rs.getString("quiz_name"), rs.getString("description"), rs.getBoolean("is_single_page"), rs.getBoolean("can_be_practiced"), rs.getBoolean("rand_question_order"));
                createdQuizzes.add(q);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return createdQuizzes;
    }

    public Quiz getQuizById(int id){
        String query = String.format("SELECT * FROM quizzes q where q.id = %d", id);

        Quiz q = null;
        try{
            executeQuery(query);

            while (rs.next()) {
                q = new Quiz(rs.getInt("id"), rs.getInt("creator_id"), rs.getString("quiz_name"), rs.getString("description"), rs.getBoolean("is_single_page"), rs.getBoolean("can_be_practiced"), rs.getBoolean("rand_question_order"));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return q;
    }

    public Quiz getlastQuizadded(){
        int id = getNextQuizId();
        Quiz q = getQuiz(id - 1);
        return q;
    }

    public ArrayList<Quiz> getRecentlyCreatedQuizzes(int creator_id){
        String query = String.format("SELECT * FROM quizzes ORDER BY id DESC");
        if(creator_id != -1) {
            query = String.format("SELECT * FROM quizzes WHERE creator_id = %d ORDER BY id DESC LIMIT 5", creator_id);
        }

        ArrayList<Quiz> rq = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                Quiz q = new Quiz(rs.getInt("id"), rs.getInt("creator_id"), rs.getString("quiz_name"), rs.getString("description"), rs.getBoolean("is_single_page"), rs.getBoolean("can_be_practiced"), rs.getBoolean("rand_question_order"));
                rq.add(q);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rq;
    }

    public ArrayList<Quiz> getPopularQuizzes(){
        String query = String.format("SELECT quiz_id, COUNT(*) AS frequency FROM quiz_history GROUP BY quiz_id ORDER BY frequency DESC");
        Map<Integer, Integer> quizFrequencyMap = new HashMap<>();
        ArrayList<Quiz> popularQuizzes = new ArrayList<>();
        try{
            executeQuery(query);

            while (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                int frequency = rs.getInt("frequency");
                quizFrequencyMap.put(quizId, frequency);
            }
            for (int quizId : quizFrequencyMap.keySet()) {
                Quiz quiz = getQuizById(quizId);
                if (quiz != null) {
                    popularQuizzes.add(quiz);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return popularQuizzes;
    }

    public int getNextQuizId(){
        String q = "SELECT MAX(id) + 1 as next_id FROM quizzes";
        try{
            executeQuery(q);
            rs.next();

            int nextId = rs.getInt("next_id");
            return  nextId;
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public int getLastQuestionId(){
        String q = "SELECT MAX(id) as cur_id FROM questions";
        try{
            executeQuery(q);
            rs.next();
            int cur_id = rs.getInt("cur_id");
            return  cur_id;
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public void updateQuiz(Quiz qu){
        String q = "UPDATE quizzes q\n" +
                   String.format("SET q.quiz_name = '%s', q.description = '%s', q.is_single_page = %b, q.can_be_practiced = %b, q.rand_question_order = %b WHERE q.id = %d;", qu.quiz_name, qu.description, qu.is_single_page, qu.can_be_practiced, qu.rand_question_order, qu.id);
        executeUpdate(q);
    }
    public void updateRateAndReview(rateAndReview r){
        if(r == null)
            throw new RuntimeException("Provided insertRateAndReview is null");
        String q = "UPDATE rateAndReview q\n" +
                String.format("SET q.rating = %d, q.review = '%s' WHERE q.id = %d;", r.rating, r.review, r.id);
        executeUpdate(q);
    }

    public void trimQuiz(Quiz qu){
        String delete_questions = String.format("DELETE FROM questions WHERE quiz_id = %d", qu.id);
        String delete_tags = String.format("DELETE FROM tag_quiz WHERE quiz_id = %d", qu.id);

        try {
            executeUpdate(delete_questions);
            executeUpdate(delete_tags);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public ArrayList<Categorya> getCategories(){
        ArrayList<Categorya> categories = new ArrayList<>();
        String q = "SELECT * FROM quiz_categories";
        try{
            executeQuery(q);

            while (rs.next()) {
                Categorya categorya = new Categorya(rs.getInt("id"), rs.getString("category"));
                categories.add(categorya);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return categories;
    }
    public int getTagId(String tag){
        String q = String.format("SELECT * FROM quiz_tags qt WHERE qt.tag = '%s'", tag);
        try{
            executeQuery(q);

            while(rs.next()){
                int id = rs.getInt("id");
                return id;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    public int getLastTagId(){
        String q = "SELECT MAX(id) as cur_id FROM quiz_tags";
        try{
            executeQuery(q);
            rs.next();

            int cur_id = rs.getInt("cur_id");
            return  cur_id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    public void updateQuizCategory(int quiz_id, int category_id){
        String u = String.format("UPDATE quizzes q SET q.category_id = %d WHERE q.id = %d", category_id, quiz_id);
        if(category_id == 0){
            u = String.format("UPDATE quizzes q SET q.category_id = null WHERE q.id = %d", quiz_id);
        }
        executeUpdate(u);
    }
    public int getQuizCategory(int quiz_id){
        String q = String.format("select category_id from quizzes q where q.id=%d", quiz_id);
        try{
            executeQuery(q);
            rs.next();


            int category_id = rs.getInt("category_id");
            System.out.println("quiz category ID: " + category_id);
            return category_id;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    public ArrayList<Tag> getTags(){
        String q = "SELECT * FROM quiz_tags";
        ArrayList<Tag> tags = new ArrayList<>();
        try{
            executeQuery(q);

            while (rs.next()) {
                Tag tag = new Tag(rs.getInt("id"), rs.getString("tag"));
                tags.add(tag);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tags;
    }
    public ArrayList<String> getQuizTags(int quiz_id){
        String q = String.format("SELECT tag FROM tag_quiz tq JOIN quiz_tags qt ON(tq.tag_id = qt.id) WHERE tq.quiz_id = %d", quiz_id);
        ArrayList<String> tags = new ArrayList<>();
        try{
            executeQuery(q);
            while(rs.next()){
                String tag = rs.getString("tag");
                tags.add(tag);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return tags;
    }
    public int getQuizNumCreatedByUser(int user_id){
        String q = String.format("SELECT COUNT(*) AS num FROM quizzes q WHERE q.creator_id = %d", user_id);
        try{
            executeQuery(q);
            while(rs.next()){
                int num = rs.getInt("num");
                return num;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
}
