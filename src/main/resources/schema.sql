DROP TABLE IF EXISTS users;

CREATE TABLE Users (
        id BIGINT NOT NULL AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        login_id VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,

        PRIMARY KEY (id)
);