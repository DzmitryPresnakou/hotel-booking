--liquibase formatted sql

--changeset presnakov:1
CREATE TABLE IF NOT EXISTS hotel
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(128) NOT NULL unique,
    photo VARCHAR(128) NOT NULL
);
--rollback DROP TABLE hotel;

--changeset presnakov:2
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128)         NOT NULL,
    last_name  VARCHAR(128)         NOT NULL,
    email      VARCHAR(128)         NOT NULL unique,
    phone      VARCHAR(64)          NOT NULL,
    photo      VARCHAR(128)         NOT NULL,
    birth_date DATE                 NOT NULL,
    money      INT                  NOT NULL,
    password   VARCHAR(128)         NOT NULL,
    role       VARCHAR(128)         NOT NULL,
    is_active  BOOLEAN DEFAULT true NOT NULL
);
--rollback DROP TABLE users;

--changeset presnakov:3
CREATE TABLE IF NOT EXISTS room
(
    id            SERIAL PRIMARY KEY,
    occupancy     INT                       NOT NULL,
    class         VARCHAR(64)               NOT NULL,
    photo         VARCHAR(128)              NOT NULL,
    price_per_day INT                       NOT NULL,
    hotel_id      INT REFERENCES hotel (id) NOT NULL
);
--rollback DROP TABLE room;

--changeset presnakov:4
CREATE TABLE IF NOT EXISTS room_order
(
    id             SERIAL PRIMARY KEY,
    user_id        INT REFERENCES users (id) NOT NULL,
    room_id        INT REFERENCES room (id)  NOT NULL,
    status         VARCHAR(64)               NOT NULL,
    payment_status VARCHAR(64)               NOT NULL,
    check_in_date  DATE                      NOT NULL,
    check_out_date DATE                      NOT NULL
);
--rollback DROP TABLE room_order;
