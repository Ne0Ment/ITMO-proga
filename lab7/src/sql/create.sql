DROP TYPE IF EXISTS ORG_TYPE CASCADE;
DROP TYPE IF EXISTS POS CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;
DROP TABLE IF EXISTS COORDINATES CASCADE;
DROP TABLE IF EXISTS ORGANIZATIONS CASCADE;
DROP TABLE IF EXISTS WORKERS CASCADE;
-- DROP TABLE IF EXISTS COLLECTION_INFO CASCADE;

CREATE TABLE IF NOT EXISTS USERS
(
    id     BIGSERIAL PRIMARY KEY,
    name   varchar(127),
    passwd varchar(127)
);

CREATE TABLE IF NOT EXISTS COORDINATES
(
    id BIGSERIAL PRIMARY KEY,
    x BIGINT,
    y BIGINT
);

CREATE TABLE IF NOT EXISTS ORGANIZATIONS
(
    id BIGSERIAL PRIMARY KEY,
    name varchar(127),
    type varchar(127)
);

CREATE TABLE IF NOT EXISTS WORKERS
(
    id            BIGSERIAL PRIMARY KEY,
    creator_id    BIGINT REFERENCES USERS(id) ON DELETE CASCADE,
    name          varchar(127),
    coordinate_id BIGINT REFERENCES COORDINATES(id) ON DELETE CASCADE,
    creation_date timestamp,
    start_date    timestamp,
    end_date      timestamp,
    salary        float,
    position_     text,
    org_id        BIGINT REFERENCES ORGANIZATIONS(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS COLLECTION_INFO
(
    id SERIAL PRIMARY KEY ,
    creation_date timestamp
);
