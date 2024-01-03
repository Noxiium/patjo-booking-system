DROP TABLE users_booking;
DROP TABLE booking_list;
DROP TABLE list;
--DROP TABLE booking_course;
DROP TABLE booking;
DROP TABLE users_course;
DROP TABLE course;
DROP TABLE USERS;


CREATE SCHEMA patjodb;
SET CURRENT SCHEMA patjodb;

CREATE TABLE users (
	users_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	usersname VARCHAR(32) UNIQUE NOT NULL,
	password VARCHAR(32) NOT NULL,
	admin INT NOT NULL -- 0 users, 1 -- admin
);


CREATE TABLE course(
	course_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	course_name VARCHAR(64) NOT NULL
);

CREATE TABLE booking(
	booking_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	booking_type_of_session VARCHAR(64) NOT NULL, -- en sträng som beskriver bokningarna, t ex "labbar"
	booking_location VARCHAR(16) NOT NULL, -- Sal på campus eller via zoom.
	start_time VARCHAR(19) NOT NULL, -- Vilket datum och tidpunkt redovisningarna börjar 
	available BOOLEAN  
);

CREATE TABLE list(
    list_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    users_id INT REFERENCES users(users_id),
	course_id INT NOT NULL REFERENCES course(course_id)
);

--CREATE TABLE booking_course (
--  booking_id  INT NOT NULL REFERENCES booking(booking_id),
--  course_id INT NOT NULL REFERENCES course(course_id)
--);

--ALTER TABLE booking_course ADD CONSTRAINT PK_booking_course PRIMARY KEY (booking_id,course_id);

CREATE TABLE users_booking (
    users_id  INT NOT NULL REFERENCES users(users_id) ON DELETE CASCADE,
    booking_id INT NOT NULL REFERENCES booking(booking_id) ON DELETE CASCADE
);

ALTER TABLE users_booking ADD CONSTRAINT PK_users_booking PRIMARY KEY (users_id,booking_id);
ALTER TABLE users_booking ADD CONSTRAINT FK_users_booking_0 FOREIGN KEY (users_id) REFERENCES users (users_id) ON DELETE CASCADE;
ALTER TABLE users_booking ADD CONSTRAINT FK_users_booking_1 FOREIGN KEY (booking_id) REFERENCES booking (booking_id) ON DELETE CASCADE;


CREATE TABLE users_course (
 users_id INT NOT NULL REFERENCES users(users_id) ON DELETE CASCADE,
 course_id INT NOT NULL REFERENCES course(course_id) ON DELETE CASCADE
);

ALTER TABLE users_course ADD CONSTRAINT PK_users_course PRIMARY KEY (users_id,course_id);
ALTER TABLE users_course ADD CONSTRAINT FK_users_course_0 FOREIGN KEY (users_id) REFERENCES users (users_id) ON DELETE CASCADE;
ALTER TABLE users_course ADD CONSTRAINT FK_users_course_1 FOREIGN KEY (course_id) REFERENCES course (course_id) ON DELETE CASCADE;

CREATE TABLE booking_list(
    list_id INT NOT NULL REFERENCES list(list_id) ON DELETE CASCADE,
    booking_id INT NOT NULL REFERENCES booking(booking_id) ON DELETE CASCADE
);

ALTER TABLE booking_list ADD CONSTRAINT PK_booking_list PRIMARY KEY (list_id,booking_id);
ALTER TABLE booking_list ADD CONSTRAINT FK_booking_list_0 FOREIGN KEY (list_id) REFERENCES list (list_id) ON DELETE CASCADE;
ALTER TABLE booking_list ADD CONSTRAINT FK_booking_list_1 FOREIGN KEY (booking_id) REFERENCES booking (booking_id) ON DELETE CASCADE;


-- Tre användare där beda med id 2 är admin.
INSERT INTO users (usersname, password, admin) 
VALUES 
('ada@kth.se', 'qwerty', 0),
('beda@kth.se', 'qwerty', 1),
('test@kth.se', 'test', 0),
('admin@kth.se', 'test', 1); 

-- Två kurser
INSERT INTO course (course_name) 
VALUES 
('ID1212'),
('DD1331');

-- users -> id=3 (test) is registered in course id=1 (id1212)
INSERT INTO users_course(users_id,course_id)
VALUES
(3,1),
(3,2);

-- Beda creates two lists for course:id 1 (id1212)
INSERT INTO list(users_id,course_id) 
VALUES
(2,1),
(2,1);

-- insert bookings
INSERT INTO booking(booking_type_of_session, booking_location, start_time, available) 
VALUES 
('lab', 'zoom', '2023-12-23 10:15', FALSE),
('lab', 'zoom', '2023-12-23 10:30', TRUE),
('presentation', 'Room 203', '2024-01-19 10:15', TRUE),
('presentation', 'Room 203', '2023-01-18 10:30', TRUE);


-- cross reference the added bookings with the list
INSERT INTO booking_list(list_id,booking_id)
VALUES
(1,1),
(1,2),
(2,3),
(2,4);


-- users -> id=3 (test) is registered in booking id=1
INSERT INTO users_booking(users_id,booking_id)
VALUES
(3,1);
