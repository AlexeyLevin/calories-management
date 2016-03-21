DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  (100000, '2015-11-25 11:00:00', 'Юзер поел бобра', 1500),
  (100001, '2015-11-25 11:00:00', 'Админ поел бобра', 1500),
  (100000, '2015-11-25 15:00:00', 'Юзер поел осла', 500),
  (100001, '2015-11-25 15:00:00', 'Админ поел осла', 500),
  (100000, '2015-11-25 20:00:00', 'Юзер поел овцу', 500),
  (100001, '2015-11-25 20:00:00', 'Админ поел овцу', 500),
  (100000, '2015-11-26 11:00:00', 'Юзер поел мацу', 2001),
  (100001, '2015-11-26 11:00:00', 'Админ поел мацу', 2001);


