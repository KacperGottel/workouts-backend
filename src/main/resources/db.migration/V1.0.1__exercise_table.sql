CREATE TABLE IF NOT EXISTS exercise (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        category VARCHAR(255),
                                        name VARCHAR(255),
                                        description TEXT,
                                        video_url VARCHAR(255),
                                        img_url VARCHAR(255)
);
