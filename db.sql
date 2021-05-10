DROP DATABASE booksys ;

CREATE DATABASE booksys ;

USE booksys ;

CREATE TABLE Oid (
       last_id	 INT NOT NULL
) ;

CREATE TABLE `Table` (
       oid	     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       number	 INT NOT NULL,
       places	 INT NOT NULL
) ;

CREATE TABLE Customer (
       oid	     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
       name	     VARCHAR(32) NOT NULL,
       phoneNumber  CHAR(13) NOT NULL
) ;

CREATE TABLE WalkIn (
       oid	     int NOT NULL PRIMARY KEY,
       covers	 int,
       date	     DATE,
       time	     TIME,
       table_id	 int
) ;

CREATE TABLE Reservation (
       oid	        int NOT NULL PRIMARY KEY,
       covers	    int,
       date	        DATE,
       time	        TIME,
       table_id	    int,
       customer_id  int,
       arrivalTime  TIME
) ;


-- 사용자 table 추가
create table user (
	oid	     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	admin		boolean		not null DEFAULT 0,
	id 			varchar(20) not null UNIQUE,
	password 	varchar(16) not null,
	name 		varchar(16) not null,
	phoneNumber varchar(11) not null
);

-- DATABASE와 USER테이블의 charset=utf8 로 설정합니다. 한글깨짐을 방지하기 위함.
ALTER DATABASE booksys DEFAULT CHARACTER SET utf8;
ALTER TABLE user CONVERT TO CHARACTER SET utf8;
-- SELECT schema_name, default_character_set_name FROM information_schema.schemata;
-- SELECT CCSA.character_set_name FROM information_schema.`TABLES` T, information_schema.`COLLATION_CHARACTER_SET_APPLICABILITY` CCSA WHERE CCSA.collation_name = T.table_collation AND T.table_schema = "booksys" AND T.table_name = "user";


-- Table 5개 기본으로 추가 (번호, 좌석)
INSERT INTO `TABLE` (number, places) VALUES (1, 4);
INSERT INTO `TABLE` (number, places) VALUES (2, 4);
INSERT INTO `TABLE` (number, places) VALUES (3, 4);
INSERT INTO `TABLE` (number, places) VALUES (4, 4);
INSERT INTO `TABLE` (number, places) VALUES (5, 4);


