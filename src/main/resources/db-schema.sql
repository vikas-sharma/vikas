SET MODE MySQL

CREATE TABLE IF NOT EXISTS person (  PERSON_ID bigint(20) NOT NULL AUTO_INCREMENT,  NAME varchar(20) NOT NULL,  ENCODED_PASSWORD varchar(60) NOT NULL,  EMAIL_ADDRESS varchar(255) NOT NULL,  FIRST_NAME varchar(20) NOT NULL,  LAST_NAME varchar(20) NOT NULL,  GENDER char(1) NOT NULL,  COUNTRY varchar(22) NOT NULL,  IP_ADDRESS varchar(20) NOT NULL,  CREATED timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  ACTIVATED timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',  AUTH_KEY int(11) NOT NULL,  STATUS varchar(10) NOT NULL,  PRIMARY KEY (PERSON_ID))

CREATE TABLE IF NOT EXISTS person_role (  PERSON_ID bigint(20) NOT NULL,  ROLE int(11) NOT NULL,  PRIMARY KEY (PERSON_ID))

CREATE TABLE IF NOT EXISTS game (  game_id bigint(20) NOT NULL AUTO_INCREMENT,  title varchar(20) NOT NULL,  status varchar(12) NOT NULL,  gm_id int(11) NOT NULL,  time_left int(11) NOT NULL,  fen varchar(100) NOT NULL,  PRIMARY KEY (game_id))

CREATE TABLE IF NOT EXISTS person_game (  PERSON_ID bigint(20) NOT NULL,  game_id bigint(20) NOT NULL,  active char(1) NOT NULL,  creation_time datetime NOT NULL,  PRIMARY KEY (PERSON_ID,game_id),  KEY game_id (game_id),  KEY person_id (PERSON_ID))

CREATE TABLE IF NOT EXISTS move (  move_id bigint(20) NOT NULL AUTO_INCREMENT,  person_id bigint(20) NOT NULL,  game_id bigint(20) NOT NULL,  move_no int(11) NOT NULL,  mv varchar(8) NOT NULL,  PRIMARY KEY (move_id),  KEY FK (person_id,game_id))
