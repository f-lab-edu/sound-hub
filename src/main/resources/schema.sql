DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS works;
DROP TABLE IF EXISTS profiles;

CREATE TABLE users
(
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL UNIQUE,
    login_id        VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    profile_img_url VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE works
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    user_id           BIGINT       NOT NULL,
    name              VARCHAR(255) NOT NULL UNIQUE,
    work_type         VARCHAR(255) NOT NULL,
    youtube_url       VARCHAR(255) NOT NULL,
    work_img_url      VARCHAR(255),
    work_created_date DATE         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE profiles
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id                BIGINT NOT NULL UNIQUE,
    genre                  VARCHAR(255),
    position               VARCHAR(255),
    introduce              TEXT,
    background_img_url     VARCHAR(255),
    favorite_artist_first  VARCHAR(255),
    favorite_artist_second VARCHAR(255),
    favorite_artist_third  VARCHAR(255),
    favorite_artist_fourth VARCHAR(255),
    favorite_artist_fifth  VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
