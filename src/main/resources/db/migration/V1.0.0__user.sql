CREATE TABLE "User" (
    id bigint not null primary key,
    email varchar(255) not null unique,
    name varchar(255),
    password varchar(255) not null,
    birth_date date not null,
    status varchar not null
);
create sequence User_SEQ start with 1 increment by 50;
