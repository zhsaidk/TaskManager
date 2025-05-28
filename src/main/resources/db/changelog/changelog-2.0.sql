--liquibase formatted sql

--changeset zhavokhir:2.0
INSERT INTO users (first_name, last_name, username, role, password)
VALUES ('Test', 'Test', 'test@gmail.com', 'ADMIN', '{noop}123');
--rollback delete from users where username = 'test@gmail.com';

--changeset zhavokhir:2.1
INSERT INTO tokens (refresh_token, created_at, user_id)
VALUES (gen_random_uuid(), current_timestamp, 1);
--rollback delete from users where user_id = '1';
