create table if not exists files(
     id bigint auto_increment primary key,
     name varchar(50) not null,
     location varchar(1000) not null,
     `status` varchar(20) not null
);

create table if not exists events(
    id bigint auto_increment primary key,
    file_id int not null,
    `datetime` datetime not null,
    user_id int not null,
    `status` varchar(20) not null
);

create table if not exists users(
    id bigint auto_increment primary key,
    username varchar(100) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    password varchar(1000) not null,
    `status` varchar(20) not null
);

create table if not exists users_roles(
    user_id int not null,
    role_id int not null
);

create table if not exists roles(
     id bigint auto_increment primary key,
     name varchar(50) not null
);