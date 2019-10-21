CREATE TABLE app_user(
	id SERIAL PRIMARY KEY,
	role_id INTEGER NOT NULL,
	country_id INTEGER,
	postal_code_id INTEGER,
	hobby_id INTEGER,
	image_id INTEGER,
	name VARCHAR (50) UNIQUE NOT NULL,
	email VARCHAR (100) UNIQUE NOT NULL,
	created_at TIMESTAMP NOT NULL,
	modified_at TIMESTAMP NOT NULL,
	partner INTEGER,
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

CREATE TABLE role(
	id SERIAL PRIMARY KEY,
	name VARCHAR (50) UNIQUE NOT NULL
);

CREATE TABLE account(
	id SERIAL PRIMARY KEY,
	nickname VARCHAR (50) UNIQUE NOT NULL,
	email VARCHAR (50) UNIQUE
);

CREATE TABLE app_user_account(
	app_user_id INTEGER NOT NULL,
	account_id INTEGER NOT NULL,
	PRIMARY KEY (app_user_id, account_id),
	CONSTRAINT app_user_account_account_id_fkey FOREIGN KEY (account_id)
	REFERENCES account (id) MATCH SIMPLE,
	CONSTRAINT app_user_app_user_id_fkey FOREIGN KEY (app_user_id)
	REFERENCES app_user (id) MATCH SIMPLE
);

CREATE TABLE country(
	id SERIAL PRIMARY KEY,
	name VARCHAR (50) UNIQUE NOT NULL,
	iso_code VARCHAR (13) UNIQUE NOT NULL
);

CREATE TABLE postal_code(
	id SERIAL PRIMARY KEY,
	code VARCHAR (10) UNIQUE NOT NULL
);

CREATE TABLE image(
	id SERIAL PRIMARY KEY,
	file_type VARCHAR(10) NOT NULL,
	image BYTEA NOT NULL
);

CREATE TABLE passport(
	id SERIAL PRIMARY KEY,
	app_user_id INTEGER NOT NULL,
	image_id INTEGER NOT NULL,
	country_id INTEGER NOT NULL,
	CONSTRAINT image_image_id_fkey FOREIGN KEY (image_id)
	REFERENCES image (id) MATCH SIMPLE,
	CONSTRAINT country_country_id_fkey FOREIGN KEY (country_id)
	REFERENCES country (id) MATCH SIMPLE,
	CONSTRAINT app_user_app_user_id_fkey FOREIGN KEY (app_user_id)
	REFERENCES app_user (id) MATCH SIMPLE
);

CREATE TABLE visa(
    id SERIAL PRIMARY KEY,
    passport_id INTEGER NOT NULL,
    country_id INTEGER NOT NULL,
    type VARCHAR(10),
	CONSTRAINT passport_passport_id_fkey FOREIGN KEY (passport_id)
	REFERENCES passport (id) MATCH SIMPLE,
	CONSTRAINT country_country_id_fkey FOREIGN KEY (country_id)
	REFERENCES country (id) MATCH SIMPLE
);

CREATE TABLE hobby(
	id SERIAL PRIMARY KEY,
	name VARCHAR(10) NOT NULL,
	description VARCHAR(500)
);

CREATE FUNCTION countUsersInCountry(IN country VARCHAR, OUT total INTEGER) RETURNS INTEGER AS $$
BEGIN
SELECT COUNT(*) INTO total FROM app_user JOIN country ON app_user.country_id=country.id WHERE country.name=country;
END; $$
LANGUAGE PLPGSQL;