--liquibase formatted sql

--changeset zhavokhir:1.0
CREATE TABLE if not exists users(
    id SERIAL PRIMARY KEY ,
    first_name VARCHAR(64) NOT NULL ,
    last_name VARCHAR(64) NOT NULL ,
    username VARCHAR(128) NOT NULL UNIQUE ,
    role VARCHAR(32) NOT NULL ,
    password VARCHAR(256) NOT NULL
);--rollback drop table users;

--changeset zhavokhir:1.1
CREATE TABLE if not exists tasks(
    id SERIAL PRIMARY KEY ,
    title VARCHAR(128) NOT NULL ,
    description VARCHAR(256) NOT NULL ,
    status VARCHAR(32) NOT NULL ,
    created_at TIMESTAMP default current_timestamp NOT NULL ,
    updated_at TIMESTAMP NOT NULL
);--rollback drop table tasks;

--changeset zhavokhir:1.2
CREATE TABLE if not exists tokens(
    id SERIAL PRIMARY KEY ,
    refresh_token VARCHAR(256) NOT NULL ,
    created_at TIMESTAMP default current_timestamp NOT NULL ,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);--rollback drop table tokens;