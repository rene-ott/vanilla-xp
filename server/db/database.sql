/* First create database with superuser */
CREATE DATABASE vanilla_xp;



/* Connect to the database and create schema with objects */
DROP SCHEMA IF EXISTS dbo CASCADE;
CREATE SCHEMA dbo;

CREATE SEQUENCE dbo.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE dbo.player (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    closed_at timestamp without time zone,
    name character varying(255) NOT NULL
);

CREATE TABLE dbo.player_overall_state (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    closed_at timestamp without time zone,
    level integer NOT NULL,
    rank integer NOT NULL,
    xp integer NOT NULL,
    player_id bigint
);


CREATE TABLE dbo.syncro_result (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    status integer,
    try_count integer
);

ALTER TABLE ONLY dbo.player_overall_state ADD CONSTRAINT pk__player_overall_state__id PRIMARY KEY (id);
ALTER TABLE ONLY dbo.player ADD CONSTRAINT pk__player__id PRIMARY KEY (id);
ALTER TABLE ONLY dbo.syncro_result ADD CONSTRAINT pk__syncro_result__id PRIMARY KEY (id);
ALTER TABLE ONLY dbo.player ADD CONSTRAINT uk__player__name UNIQUE (name);
ALTER TABLE ONLY dbo.player_overall_state ADD CONSTRAINT fk__player_overall_state__player_id FOREIGN KEY (player_id) REFERENCES dbo.player(id);



/* Create user with grants */
CREATE USER vanilla_xp_app WITH PASSWORD 'vanilla_xp_app_password'
GRANT USAGE ON SCHEMA dbo TO vanilla_xp_app;
GRANT ALL PRIVILEGES ON DATABASE vanilla_xp to vanilla_xp_app;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA dbo TO vanilla_xp_app;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA dbo TO vanilla_xp_app;