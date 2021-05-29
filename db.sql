DROP DATABASE booksys ;

CREATE DATABASE booksys ;
ALTER DATABASE booksys DEFAULT CHARACTER SET UTF8;
-- SELECT schema_name, default_character_set_name FROM information_schema.schemata;

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
       oid	        int NOT NULL AUTO_INCREMENT PRIMARY KEY,
       covers	    int NOT NULL,
       date	        DATE NOT NULL,
       time	        TIME NOT NULL,
       table_id	    int NOT NULL,
       customer_id  int NOT NULL,
       arrivalTime  TIME
) ;


-- 사용자 table 추가
create table user (
	oid	     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	admin	    boolean		not null DEFAULT 0,
	id 			varchar(20) not null UNIQUE,
	password 	varchar(16) not null,
	name 		varchar(16) not null,
	phoneNumber varchar(11) not null
);
ALTER TABLE user CONVERT TO CHARACTER SET utf8;
-- SELECT CCSA.character_set_name FROM information_schema.`TABLES` T, information_schema.`COLLATION_CHARACTER_SET_APPLICABILITY` CCSA WHERE CCSA.collation_name = T.table_collation AND T.table_schema = "booksys" AND T.table_name = "user";


-- 사용자 리뷰 추가
create table review (
	oid	     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id 			varchar(20) not null,
	date		varchar(12) not null,
	comment		varchar(200) not null,
	foreign key (id) references user (id)
);


-- Data set

insert into user (id,password,name, phoneNumber) value ('AlienSloth','1234','박성완','01066250251');
insert into user (id,password,name, phoneNumber) value ('Sloth','1234','나무늘보','01112341234');
insert into review (id,date,comment) value ('AlienSloth','2021-05-20','잘 먹고 갑니다');
insert into review (id,date,comment) value ('Sloth','2021-04-30','가성비 최고');
insert into review (id,date,comment) value ('AlienSloth','2021-05-24','글자수 제한이 몇인지 궁금해서 어디까지 가능한지 적어보려 하는 킹갓 제네럴 엠페러 외계 나무늘보가 악질이라고 생각한다면 머리위로 똥골뱅이를 그려주세요');
insert into Reservation (covers, date, time, table_id, customer_id) value (4,'2021-05-19','18:00',5,1);
insert into Reservation (covers, date, time, table_id, customer_id) value (2,'2021-04-21','20:00',8,2);
insert into Reservation (covers, date, time, table_id, customer_id) value (3,'2021-05-23','22:00',3,1);


INSERT INTO `TABLE` (number, places) VALUES (1, 4);
INSERT INTO `TABLE` (number, places) VALUES (2, 4);
INSERT INTO `TABLE` (number, places) VALUES (3, 4);
INSERT INTO `TABLE` (number, places) VALUES (4, 4);
INSERT INTO `TABLE` (number, places) VALUES (5, 4);


