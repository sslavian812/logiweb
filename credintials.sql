
USE logiweb;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
     username VARCHAR(12) NOT NULL,
     password VARCHAR(32) NOT NULL,
     enabled BOOLEAN NOT NULL,
     authority VARCHAR(32) NOT NULL,
     PRIMARY KEY (username)
);

-- insert into users(username,password,enabled,authority) values ('manager','abacaba', TRUE, 'ROLE_MANAGER');
insert into users(username,password,enabled,authority) values ('manager','3a3817a00668151b775a26a89068070814b19dc28508c294eb68631ee2ce86bb', TRUE, 'ROLE_MANAGER');
