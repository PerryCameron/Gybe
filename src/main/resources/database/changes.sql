CREATE TABLE users
(
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    p_id     INT          NOT NULL
);

CREATE TABLE authorities
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO authorities (username, authority) VALUES ('pcameron', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('pcameron', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('pcameron', 'ROLE_MANAGER');

