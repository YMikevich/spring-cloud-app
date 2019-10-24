CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE country
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50) UNIQUE NOT NULL,
    iso_code VARCHAR(13) UNIQUE NOT NULL
);

CREATE TABLE postal_code
(
    id   SERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE hobby
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(500)
);

CREATE TABLE image
(
    id        SERIAL PRIMARY KEY,
    file_type VARCHAR(10) NOT NULL,
    image     BYTEA       NOT NULL
);

CREATE TABLE app_user
(
    id             SERIAL PRIMARY KEY,
    role_id        INTEGER             NOT NULL,
    country_id     INTEGER,
    postal_code_id INTEGER,
    hobby_id       INTEGER,
    image_id       INTEGER,
    name           VARCHAR(50) UNIQUE  NOT NULL,
    email          VARCHAR(100) UNIQUE NOT NULL,
    created_at     TIMESTAMP           NOT NULL,
    modified_at    TIMESTAMP           NOT NULL,
    partner_id     INTEGER,
    gender         VARCHAR(5)          NOT NULL,
    CONSTRAINT role_role_id_fkey FOREIGN KEY (role_id)
        REFERENCES role (id) MATCH SIMPLE,
    CONSTRAINT country_country_id_fkey FOREIGN KEY (country_id)
        REFERENCES country (id) MATCH SIMPLE,
    CONSTRAINT postal_code_postal_code_id_fkey FOREIGN KEY (postal_code_id)
        REFERENCES postal_code (id) MATCH SIMPLE,
    CONSTRAINT hobby_hobby_id_fkey FOREIGN KEY (hobby_id)
        REFERENCES hobby (id) MATCH SIMPLE,
    CONSTRAINT image_image_id_fkey FOREIGN KEY (image_id)
        REFERENCES image (id) MATCH SIMPLE
);

CREATE TABLE account
(
    id       SERIAL PRIMARY KEY,
    nickname VARCHAR(50) UNIQUE NOT NULL,
    email    VARCHAR(50) UNIQUE
);

CREATE TABLE user_account
(
    user_id    INTEGER NOT NULL,
    account_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, account_id),
    CONSTRAINT user_account_account_id_fkey FOREIGN KEY (account_id)
        REFERENCES account (id) MATCH SIMPLE,
    CONSTRAINT user_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES app_user (id) MATCH SIMPLE
);

CREATE TABLE passport
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER     NOT NULL,
    image_id   INTEGER,
    country_id INTEGER     NOT NULL,
    number     VARCHAR(20) NOT NULL,
    CONSTRAINT image_image_id_fkey FOREIGN KEY (image_id)
        REFERENCES image (id) MATCH SIMPLE,
    CONSTRAINT country_country_id_fkey FOREIGN KEY (country_id)
        REFERENCES country (id) MATCH SIMPLE,
    CONSTRAINT user_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES app_user (id) MATCH SIMPLE
);

CREATE TABLE visa
(
    id          SERIAL PRIMARY KEY,
    passport_id INTEGER NOT NULL,
    country_id  INTEGER NOT NULL,
    type        VARCHAR(10),
    CONSTRAINT passport_passport_id_fkey FOREIGN KEY (passport_id)
        REFERENCES passport (id) MATCH SIMPLE,
    CONSTRAINT country_country_id_fkey FOREIGN KEY (country_id)
        REFERENCES country (id) MATCH SIMPLE
);

CREATE OR REPLACE FUNCTION count_users_in_country(IN country_name VARCHAR, OUT total INTEGER) RETURNS INTEGER AS
$$
BEGIN
    SELECT COUNT(*)
    INTO total
    FROM app_user
             JOIN country ON app_user.country_id = country.id
    WHERE country.name = country_name;
END;
$$
    LANGUAGE PLPGSQL;

CREATE VIEW illegal_users AS
SELECT app_user.id AS user_id, user_partner.id AS partner_id, app_user.name AS username, app_user.email,
       country.id AS country_id, country.name AS country, country.iso_code, role.name AS role, app_user.gender
FROM app_user
         LEFT JOIN passport ON passport.user_id = app_user.id
         LEFT JOIN visa ON visa.id = passport.id
         LEFT JOIN role ON role.id = app_user.role_id
         LEFT JOIN country ON app_user.country_id = country.id
         LEFT JOIN app_user AS user_partner ON app_user.id = user_partner.partner_id
WHERE (passport.user_id IS NULL
    OR (passport.user_id IS NOT NULL AND visa.id IS NULL AND visa.country_id != app_user.country_id))
  AND app_user.gender = user_partner.gender;

CREATE FUNCTION name_changed() RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.name != OLD.name THEN
        NEW.modified_at := current_timestamp;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trigger_name_changed
    BEFORE UPDATE
    ON app_user
    FOR EACH ROW
EXECUTE PROCEDURE name_changed();

CREATE FUNCTION email_changed() RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.email != OLD.email THEN
        NEW.modified_at := current_timestamp;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trigger_email_changed
    BEFORE UPDATE
    ON app_user
    FOR EACH ROW
EXECUTE PROCEDURE email_changed();