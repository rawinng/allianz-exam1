--liquibase formatted sql  

--changeset rw-alliaz:1  
create table employee  (  
  id 		int primary key,  
  firstname varchar(255),
  lastname 	varchar(255)
);  
--rollback drop table employee;

--changeset rw-alliaz:2
create table users (
  username varchar(50) primary key,
  password varchar(100),
  enabled tinyint default 1
);
--rollback drop table users;

--changeset rw-alliaz:3
create table users_session (
  username varchar(50),
  expiry  datetime,
  session_token varchar(255) primary key,
  foreign key (username) references users(username)
);
--rollback drop table authorities;

