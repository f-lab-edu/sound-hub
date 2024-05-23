DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS works;

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL UNIQUE,
    login_id VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE works
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    user_id           BIGINT       NOT NULL,
    name              VARCHAR(255) NOT NULL UNIQUE,
    work_type         VARCHAR(255) NOT NULL,
    youtube_url       VARCHAR(255) NOT NULL,
    work_image_url    VARCHAR(255),
    work_created_date DATE         NOT NULL,
    PRIMARY KEY (id)
);