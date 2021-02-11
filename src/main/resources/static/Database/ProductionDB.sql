--
DROP SCHEMA IF EXISTS banking_system;
CREATE SCHEMA banking_system;
USE banking_system;
-- 
DROP TABLE role;
DROP TABLE admin;
DROP TABLE account_holder;
DROP TABLE user;
--

INSERT INTO user(first_name, last_name, password, username) VALUES
("admin first name", "admin last name", "$2a$10$Y0z0.IArIUtLi1X8rhGL0ePcPDyls7b6zrjZkFTh5j4zaZ127K0RS", "admin");

INSERT INTO admin(personal_id, id)  VALUES
("123321", 2);
INSERT INTO role(role_name, user_id) VALUES
("ADMIN", 2);
INSERT INTO role(role_name, user_id) VALUES
("STANDARD_USER", 2);


INSERT INTO user(first_name, last_name, password, username) VALUES
("user first name", "user last name", "$2a$10$Y0z0.IArIUtLi1X8rhGL0ePcPDyls7b6zrjZkFTh5j4zaZ127K0RS", "standard");
INSERT INTO role(role_name, user_id) VALUES
("ADMIN", 2);
INSERT INTO role(role_name, user_id) VALUES
("STANDARD_USER", 2);

