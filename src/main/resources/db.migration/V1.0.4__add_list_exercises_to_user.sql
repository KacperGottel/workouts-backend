ALTER TABLE exercise
    ADD COLUMN user_id BIGINT;

ALTER TABLE exercise
    ADD CONSTRAINT fk_exercise_user
        FOREIGN KEY (user_id)
            REFERENCES user (id);


