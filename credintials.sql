

CREATE TABLE IF NOT EXISTS users (
     username VARCHAR(12) NOT NULL,
     password VARCHAR(32) NOT NULL,
     enabled BOOLEAN NOT NULL,
     authority VARCHAR(32) NOT NULL,
     PRIMARY KEY (username)
);

insert into users(username,password,enabled,authority) values ('manager','abacaba', TRUE, 'ROLE_MANAGER');
