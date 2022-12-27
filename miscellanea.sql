create database miscellanea;

use miscellanea;


create table miscellanea.tb_properties
(
    property_id int auto_increment
        primary key,
    name        varchar(30) not null,
    value       text        not null,
    constraint name
        unique (name)
)
    charset = utf8mb4;
	
	
create table miscellanea.tb_user
(
    user_id          int auto_increment
        primary key,
    user_first_name  varchar(255)  not null,
    user_last_name   varchar(255)  not null,
    user_login       varchar(255)  not null,
    user_password    varchar(255)  not null,
    user_phone       varchar(255)  null,
    user_email       varchar(255)  null,
    user_status      int default 1 null,
    user_create_date datetime      null,
    user_create_id   int default 0 null,
    user_modify_date datetime      null,
    user_modify_id   int default 0 null,
    user_last_login  datetime      null
);