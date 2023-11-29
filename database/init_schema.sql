CREATE DATABASE IF NOT EXISTS todo_calendar;

USE todo_calendar;

CREATE TABLE users (
	uid INT(4) PRIMARY KEY auto_increment, /* user id */
	login VARCHAR(20) NOT NULL,
	password VARCHAR(65) NOT NULL /* sha256 */
);

CREATE TABLE tasks (
	tid INT(4) PRIMARY KEY auto_increment, /* task id */
	uid INT(4), /* task owner - user id */
	title VARCHAR(255) NOT NULL,
	descr TEXT,
	start_date DATETIME NOT NULL,
	end_date DATETIME NOT NULL,
 	FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE SET NULL
);

CREATE TABLE links (
	lid INT(4) primary key auto_increment, /* link id */
	tid INT(4), /* task id */
	gid INT(4), /* guest - user id */
	FOREIGN KEY (tid) REFERENCES tasks(tid), 
	FOREIGN KEY (gid) REFERENCES users(uid) ON DELETE SET NULL
);

DELIMITER $$
CREATE TRIGGER delete_links_after_task_delete
AFTER DELETE ON tasks
FOR EACH ROW
BEGIN
    DELETE FROM links WHERE tid = OLD.tid;
END$$
DELIMITER ;

INSERT INTO users (login, password) VALUES
('test', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a0'), /* test */
('test2', '60303ae22b998861bce3b28f33eec1be758a213c86c93c076dbe9f558c11c75'); /* test2 */

INSERT INTO tasks(uid, title, descr, start_date, end_date) VALUES
(1, 'Testowy task', 'Polega na sparwdzeniu czy dziala', '2023-11-20 15:00:00', '2023-11-21 12:00:00');

INSERT INTO links(tid, gid) VALUES
(1, 2);

CREATE USER serviceaccount IDENTIFIED BY 'cZtx7b$xwkSL'; 

GRANT ALL PRIVILEGES ON todo_calendar.* TO serviceaccount; 
