CREATE TABLE orders
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    uuid    VARCHAR(36) NOT NULL,
    total   DECIMAL     NOT NULL,
    user_id BIGINT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE INDEX idx_order_uuid ON orders (uuid);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);