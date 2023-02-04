create table workouts_db.user
(
    id          bigint auto_increment
        primary key,
    created     datetime     null,
    email       varchar(255) null,
    expired     datetime     null,
    password    varchar(255) null,
    scope       varchar(255) null,
    user_status varchar(255) null,
    username    varchar(255) null
)
    engine = MyISAM;

create table workouts_db.user_token
(
    id      bigint auto_increment
        primary key,
    token   varchar(255) null,
    user_id bigint       null
)
    engine = MyISAM;

create index FK_user_token
    on workouts_db.user_token (user_id);

