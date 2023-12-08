CREATE DATABASE IF NOT EXISTS todo_calendar;

USE todo_calendar;

CREATE TABLE users (
	uid INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(65) NOT NULL,
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
	end_date DATETIME NOT NULL,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 	FOREIGN KEY (tid) REFERENCES tasks(tid) ON DELETE SET NULL
);

INSERT INTO users (username, password) VALUES
('test', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a0'); /* test */

INSERT INTO cars (uid, name, description) VALUES
(1, 'civic', 'gruz do latania pod tesco');

INSERT INTO tasks(uid, cid, name, description, end_date) VALUES
(1, 1, 'naprawa hondy', 'przy***alem w latarnie', '2023-12-24 16:00:00');

INSERT INTO subtasks(tid, name, description, end_date) VALUES
(1, 'przedni zderzak', 'rozwalony po lewej stronie', '2023-12-22 13:00:00'),
(1, 'lewe koło', 'odpadło przy kontakcie z kraweznikiem', '2023-12-23 15:00:00'),
(1, 'karoseria', 'do wyklepania', '2023-12-24 14:00:00');

CREATE USER serviceaccount IDENTIFIED BY 'cZtx7b$xwkSL';

GRANT ALL PRIVILEGES ON todo_calendar.* TO serviceaccount;
