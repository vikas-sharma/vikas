CREATE TABLE IF NOT EXISTS person (
  PERSON_ID int NOT NULL,
  NAME varchar(20) NOT NULL,
  ENCODED_PASSWORD varchar(60) NOT NULL,
  EMAIL_ADDRESS varchar(255) NOT NULL,
  FIRST_NAME varchar(20) NOT NULL,
  LAST_NAME varchar(20) NOT NULL,
  GENDER char(1) NOT NULL,
  COUNTRY varchar(22) NOT NULL,
  IP_ADDRESS varchar(20) NOT NULL,
  CREATED timestamp NOT NULL,
  ACTIVATED timestamp NOT NULL,
  AUTH_KEY int NOT NULL,
  STATUS varchar(10) NOT NULL,
  PRIMARY KEY (PERSON_ID)
)

CREATE TABLE IF NOT EXISTS person_role (
  PERSON_ID int NOT NULL,
  ROLE int NOT NULL,
  PRIMARY KEY (PERSON_ID)
)

CREATE TABLE IF NOT EXISTS game (
  game_id int NOT NULL,
  title varchar(20) NOT NULL,
  status varchar(12) NOT NULL,
  gm_id int NOT NULL,
  time_left int NOT NULL,
  fen varchar(100) NOT NULL,
  PRIMARY KEY (game_id)
)

CREATE TABLE IF NOT EXISTS person_game (
  PERSON_ID int NOT NULL,
  game_id int NOT NULL,
  active char(1) NOT NULL,
  creation_time datetime NOT NULL,
  PRIMARY KEY (PERSON_ID,game_id)
)

ALTER TABLE person_game
  ADD FOREIGN KEY (game_id) REFERENCES game (game_id)
ALTER TABLE person_game
  ADD FOREIGN KEY (PERSON_ID) REFERENCES person (PERSON_ID);

CREATE TABLE IF NOT EXISTS move (
  move_id int NOT NULL,
  person_id int NOT NULL,
  game_id int NOT NULL,
  move_no int NOT NULL,
  mv varchar(8) NOT NULL,
  PRIMARY KEY (move_id)
)

ALTER TABLE move
  ADD CONSTRAINT FK FOREIGN KEY (person_id, game_id) REFERENCES person_game (PERSON_ID, game_id);
