ALTER TABLE workouts_db.exercise
    ADD COLUMN series INT,
    ADD COLUMN reps   INT;

INSERT INTO workouts_db.exercise (id, category, name, description, video_url, img_url, series, reps)
VALUES (1, 'PUSH', 'OHP BARBELL',
        'OHP barbell is a weightlifting exercise that involves pressing a barbell overhead from a standing position, which targets the shoulders, triceps, and upper back muscles.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (2, 'PULL', 'CHIN UP',
        'Chin up is a bodyweight exercise that involves pulling the body up towards a bar, targeting the back, shoulders, and biceps muscles.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (3, 'LEGS_PUSH', 'HIGH BAR SQUAT',
        'High bar squat is a strength training exercise that involves placing a barbell on the upper back and squatting down to target the quadriceps, hamstrings, and glutes.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (4, 'LEGS_PULL', 'HIP TRUST',
        'Hip thrusts is a glute strengthening exercise that involves lifting the hips up while the shoulders and feet remain on the ground, targeting the glutes and hamstrings muscles.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (5, 'ACCESSORY', 'BAND TRICEPS EXTENSION',
        'Band triceps extension is a resistance band exercise that involves extending the arms and stretching a resistance band behind the back, targeting the triceps muscles for strengthening and toning.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (6, 'ABS', 'PLANK',
        'Plank is a bodyweight exercise that involves holding a position similar to a push-up, with the body straight and supported on the forearms and toes, targeting the core muscles for strengthening and stabilization.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (7, 'MOBILITY', 'CAT-COW',
        'Cat-cow is a yoga pose that involves arching and rounding the spine, which can help improve spinal flexibility and relieve tension in the back muscles.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10),
       (8, 'PLYO', 'BOX JUMP',
        'Box jump is a plyometric exercise that involves jumping onto a box from a standing position, targeting the leg muscles and improving explosive power.',
        'https://www.youtube.com/watch?v=kqnuaHdmv20', 'https://www.bodybuilding.com/exercises/cow-cat', 3, 10);