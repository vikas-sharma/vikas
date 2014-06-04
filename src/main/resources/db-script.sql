SET MODE MySQL

INSERT INTO person (PERSON_ID, NAME, ENCODED_PASSWORD, EMAIL_ADDRESS, FIRST_NAME, LAST_NAME, GENDER, COUNTRY, IP_ADDRESS, CREATED, ACTIVATED, AUTH_KEY, STATUS) VALUES (1, 'vikas', '$2a$10$ZbQFsywpJdAJiVqlQZE/ju1FZ2IBdVRngh9HV5FO30bqznRHZ99Cy', 'vikas.sharma.in@gmail.com', 'vikas', 'sharma', 'M', 'India', '180.151.148.16', '2013-10-13 07:05:13', '2013-10-13 07:05:13', 10882604, 'Active')

INSERT INTO person_role (PERSON_ID, ROLE) VALUES (1, 1)

INSERT INTO game (game_id,  title,  status,  gm_id,  time_left,  fen) values (1, 'Anand vs World', 'STARTED', 2, 0, null)