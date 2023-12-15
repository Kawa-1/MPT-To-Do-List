CREATE DATABASE IF NOT EXISTS todo_calendar;

USE todo_calendar;

CREATE TABLE users (
	uid INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(65) NOT NULL,
	role VARCHAR(10) DEFAULT 'CLIENT',
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cars (
	cid INT PRIMARY KEY AUTO_INCREMENT,
	uid INT,
	name VARCHAR(100) NOT NULL,
	description TEXT,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 	FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE SET NULL
);

CREATE TABLE tasks (
	tid INT PRIMARY KEY AUTO_INCREMENT,
	uid INT,
	cid INT,
	name VARCHAR(100) NOT NULL,
	description TEXT,
	end_date DATETIME NOT NULL,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 	FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE SET NULL,
 	FOREIGN KEY (cid) REFERENCES cars(cid) ON DELETE SET NULL
);

CREATE TABLE subtasks (
	sid INT PRIMARY KEY AUTO_INCREMENT,
	tid INT,
	name VARCHAR(100) NOT NULL,
	description TEXT,
	status VARCHAR(5) NOT NULL,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 	FOREIGN KEY (tid) REFERENCES tasks(tid) ON DELETE SET NULL
);

-- INSERT INTO users (username, password) VALUES
-- ('test', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a0'); /* test */
--
-- INSERT INTO users (username, password, is_workshop) VALUES
-- ('warsztat', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a0', true); /* test */
--
-- INSERT INTO cars (uid, name, description) VALUES
-- (1, 'civic', 'gruz do latania pod tesco');
--
-- INSERT INTO tasks(uid, cid, name, description, end_date) VALUES
-- (2, 1, 'naprawa hondy', 'przy***alem w latarnie', '2023-12-24 16:00:00');
--
-- INSERT INTO subtasks(tid, name, description, status) VALUES
-- (1, 'przedni zderzak', 'rozwalony po lewej stronie', 'todo'),
-- (1, 'lewe koło', 'odpadło przy kontakcie z kraweznikiem', 'doing'),
-- (1, 'karoseria', 'do wyklepania', 'done');

DELIMITER //
CREATE TRIGGER before_insert_cars
BEFORE INSERT ON cars
FOR EACH ROW
BEGIN
    DECLARE is_workshop_value VARCHAR(10);
    SELECT role INTO is_workshop_value FROM users WHERE uid = NEW.uid;
    IF is_workshop_value NOT IN ('CLIENT') THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot insert into cars table for workshop users';
    END IF;
END;
//
CREATE TRIGGER subtasks_before_insert
BEFORE INSERT ON subtasks
FOR EACH ROW
BEGIN
    IF NEW.status NOT IN ('todo', 'doing', 'done') THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid value for status column. Allowed values are: todo, doing, done';
    END IF;
END;
//
CREATE TRIGGER subtasks_before_update
BEFORE UPDATE ON subtasks
FOR EACH ROW
BEGIN
    IF NEW.status NOT IN ('todo', 'doing', 'done') THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid value for status column. Allowed values are: todo, doing, done';
    END IF;
END;
//
DELIMITER ;

CREATE USER serviceaccount IDENTIFIED BY 'cZtx7b$xwkSL';

GRANT ALL PRIVILEGES ON todo_calendar.* TO serviceaccount;
