DROP TABLE IF EXISTS member CASCADE;

CREATE TABLE member
(
    id bigint generated by default as identity,
    name varchar(255),
    primary key (id)
);