CREATE TABLE phones
(
    id     BIGINT AUTO_INCREMENT NOT NULL,
    number VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_phones PRIMARY KEY (id)
);