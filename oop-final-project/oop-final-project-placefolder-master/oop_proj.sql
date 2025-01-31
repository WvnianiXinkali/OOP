-- CREATE USER 'oopUser'@'localhost' IDENTIFIED BY 'oopUserPasswrd';
-- GRANT ALL PRIVILEGES ON *.* TO 'oopUser'@'localhost';
-- CREATE DATABASE oop_proj;

use oop_proj;
SET GLOBAL information_schema_stats_expiry = 0;

DROP TABLE IF EXISTS announcements;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS quiz_history;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS user_achievements;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS tag_quiz;
DROP TABLE IF EXISTS quiz_tags;
DROP TABLE IF EXISTS rateAndReview;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS quiz_categories;
DROP TABLE IF EXISTS userBan;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password_hash VARCHAR(255),
    role VARCHAR(255),
    pfp VARCHAR(1000),
    isPrivate bool,
    event_time TIMESTAMP
);

INSERT INTO users (username, password_hash, role, isPrivate)
VALUES ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', false);

CREATE TABLE userBan (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	user_id INT,
	ban_until TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE friends (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	user_id INT,
    friend_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE quiz_categories (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    category VARCHAR(2000)
);

CREATE TABLE quiz_tags (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    tag VARCHAR(2000)
);

CREATE TABLE quizzes (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    creator_id INT,
    quiz_name VARCHAR(2000),
    description VARCHAR(2000),
    is_single_page bool,
    can_be_practiced bool,
    rand_question_order bool,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES quiz_categories(id),
    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tag_quiz (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    quiz_id INT,
    tag_id INT,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES quiz_tags(id) ON DELETE CASCADE
);

CREATE TABLE rateAndReview (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	quiz_id INT,
    rating INT,
    user_id INT,
    review VARCHAR(2000),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE questions (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    question_num INT,
    quiz_id INT,
    question_type INT,
    question VARCHAR(2000),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE answers (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    question_id INT,
    answer VARCHAR(2000),
    is_correct bool,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE quiz_history (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    score DECIMAL(5, 2),
    quiz_id INT,
    user_id INT,
    time_taken VARCHAR(2000),
    take_date TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	receiver_id INT,
    sender_id INT,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    notif_type VARCHAR(20),
    notif_body VARCHAR(2000)
);

CREATE TABLE announcements (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    announcement VARCHAR(2000)
);

CREATE TABLE achievements (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    achievement VARCHAR(2000),
    to_earn VARCHAR(2000),
    icon VARCHAR(1000)
);

INSERT INTO achievements (achievement, to_earn, icon)
VALUES ('Amateur Author', 'Create your first quiz', 'https://cdn-icons-png.flaticon.com/512/3601/3601002.png'),
	   ('Prolific Author', 'Create 5 quizzes', 'https://cdn-icons-png.flaticon.com/128/3601/3601646.png'),
       ('Prodigious Author', 'Create 10 quizzes', 'https://cdn-icons-png.flaticon.com/128/7601/7601746.png'),
       ('Quiz Machine', 'Take 10 quizzes', 'https://cdn-icons-png.flaticon.com/128/5289/5289226.png'),
       ('I am the Greatest', 'Get the highest score on a quiz', 'https://cdn-icons-png.flaticon.com/128/744/744922.png'),
       ('Practice Makes Perfect', 'Take your first practice quiz', 'https://cdn-icons-png.flaticon.com/128/1330/1330208.png');

CREATE TABLE user_achievements (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT,
    achievement_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (achievement_id) REFERENCES achievements(id)
);


-- TEST DATA:

 INSERT INTO users (username, password_hash, role, isPrivate)
 VALUES ('John Deer', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'user', false),
	   ('Serena Anderson', '2abd55e001c524cb2cf6300a89ca6366848a77d5', 'user', false),
       ('Xavier Patel', 'd5f12e53a182c062b6bf30c1445153faff12269a', 'user', false);

 INSERT INTO userBan (user_id, ban_until)
 VALUES (3, '2023-08-30 12:00:00'),
        (4, '2023-08-30 11:00:00');

 INSERT INTO quiz_categories (category)
 VALUES ("category1"),
        ("category2");

 INSERT INTO notifications (receiver_id, sender_id, notif_type, notif_body)
 VALUES (1, 2, 'note', 'Hello'),
	   (2, 1, 'note', 'Hi'),
	   (1, 2, 'note', 'Test1'),
	   (1, 3, 'note', 'Test2'),
       (3, 2, 'note', 'qwer'),
	   (3, 1, 'note', 'tyui');

 INSERT INTO quizzes(creator_id, quiz_name, description, is_single_page, can_be_practiced, rand_question_order)
 VALUES  (1,'dinozavrebi','Dinosaurs are a diverse group of reptiles of the clade Dinosauria. They first appeared during the Triassic period, between 245 and 233.23 million years ago (mya), although the exact origin and timing of the evolution of dinosaurs is a subject of active research. They became the dominant terrestrial vertebrates after the Triassic–Jurassic extinction event 201.3 mya and their dominance continued throughout the Jurassic and Cretaceous periods. The fossil record shows that birds are feathered dinosaurs, having evolved from earlier theropods during the Late Jurassic epoch, and are the only dinosaur lineage known to have survived the Cretaceous–Paleogene extinction event approximately 66 mya. Dinosaurs can therefore be divided into avian dinosaurs—birds—and the extinct non-avian dinosaurs, which are all dinosaurs other than birds.',
         true, false, false);

 INSERT INTO user_achievements(user_id, achievement_id)
 VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (1,5),
       (1,6),
       (2,4),
       (2,5),
       (2,6),
       (3,1),
       (3,2);

 INSERT INTO announcements(announcement)
 VALUES ('New quiz added'),
	   ('Create quiz and get achievement');

 INSERT INTO friends(user_id, friend_id)
 VALUES (1,2),
       (2,1),
       (1,3),
       (3,1);

 INSERT INTO quizzes (creator_id, quiz_name, is_single_page, can_be_practiced, rand_question_order)
 VALUES (1, 'History', true, false, false),
	   (1, 'Geography', true, false, false),
       (2, 'Physics', true, false, false),
       (3, 'Chemistry', true, false, false);

 INSERT INTO quiz_history(score, quiz_id, user_id, take_date)
 VALUES (80.6,1,1, '2023-08-30 13:00:00'),
       (95.2,1,2, '2023-08-30 10:00:00'),
       (12.4,3,1,'2023-08-30 9:00:00');

 INSERT INTO rateAndReview (quiz_id, rating, user_id, review)
 VALUES (1, 5, 1, 'kargia xoici'),
       (1, 5, 1, 'kargia?'),
       (1, 5, 1, 'mgoni ki'),
       (1, 5, 1, 'naah');

 INSERT INTO questions(question_num, quiz_id, question_type, question)
     VALUES (1,1,0,'saqartvelos dedaqalaqia raari'),
        (2,1,0,'Sauketeso Gundi msoflioshi?'),
        (3,1,0,'ra aris veqtoruli velis potenciali?'),
        (4,1,1,'GO yvelaze  magari .... .'),
        (5,1,2,'koleidoskopu magrad adidebs.'),
        (6,1,4,'chamotvalet top kanonieri qurdebi'),
        (6,1,5,'shemoxaset top kanonieri qurdebi');

 INSERT INTO answers( question_id, answer, is_correct)
 VALUES (5,'TRUE',false),
       (5,'FALSE',true),
       (2,'Milani',true),
       (3,'veqtoruli veli',true),
       (4,'enaa',true),
       (6,'tornike ramishvili',TRUE),
       (7,'tornike ramishvili',TRUE),
       (7,'nika tarkashvili',TRUE),
       (7,'giorgi javakhishvili',TRUE),
       (7,'mariam gamrekelashvili',TRUE),
       (7,'Don corleone',FALSE),
        (1,'tbilisi',TRUE);