create table users
(
    id          bigint auto_increment primary key,
    created     datetime     null,
    email       varchar(255) null,
    expired     datetime     null,
    password    varchar(255) null,
    scope       varchar(255) null,
    user_status varchar(255) null,
    username    varchar(255) null
)
    engine = InnoDB;

CREATE TABLE user_token
(
    id      bigint auto_increment primary key,
    token   varchar(255) null,
    user_id bigint       not null,
    CONSTRAINT FK_user_token FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)
    ENGINE = InnoDB;

CREATE INDEX IDX_user_token_user_id ON user_token (user_id);


CREATE TABLE exercise
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    category    VARCHAR(255),
    name        VARCHAR(255),
    description TEXT,
    video_url   VARCHAR(255),
    img_url     VARCHAR(255),
    status      VARCHAR(255),
    series      INT,
    reps        INT,
    user_id     BIGINT
)
    engine = InnoDB;

ALTER TABLE exercise
    ADD CONSTRAINT fk_user_exercise
        FOREIGN KEY (user_id)
            REFERENCES users (id)

