CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    uuid       VARCHAR(36)           NOT NULL,
    first_name VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    phone_id   BIGINT                NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

CREATE INDEX idx_user_email ON users (email);

CREATE INDEX idx_user_uuid ON users (uuid);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PHONE FOREIGN KEY (phone_id) REFERENCES phones (id);