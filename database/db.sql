drop database if exists demo;
create database demo charset=utf8 collate=utf8_swedish_ci;
use demo;

create table user(
	userId bigint not null auto_increment primary key,
    username varchar(100) not null,
    password varchar(255) not null,
    sex tinyint(1),
    enabled tinyint(1) default 1
)engine=innodb default charset=utf8 collate=utf8_swedish_ci;

create table message(
	messageId bigint not null auto_increment primary key,
    sourseId bigint not null,
    targetId bigint not null,
    content text
)engine=innodb default charset=utf8 collate=utf8_swedish_ci;

alter table message add constraint fk_message_sourse foreign key(sourseId) references user(userId);
alter table message add constraint fk_message_target foreign key(targetId) references user(userId);

create table friend(
	friendId bigint not null auto_increment primary key,
    sourseId bigint not null,
    targetId bigint not null
)engine=innodb default charset=utf8 collate=utf8_swedish_ci;

alter table friend add constraint fk_friend_sourse foreign key(sourseId) references user(userId);
alter table friend add constraint fk_friend_target foreign key(targetId) references user(userId);
