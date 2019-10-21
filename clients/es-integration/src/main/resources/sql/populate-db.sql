INSERT INTO hobby (name, description) VALUES ('music', 'playing guitar');
INSERT INTO hobby (name, description) VALUES ('music', 'playing piano');
INSERT INTO hobby (name, description) VALUES ('travelling', 'visiting different countries');
INSERT INTO hobby (name, description) VALUES ('streetracing', 'night races on sport cars');
INSERT INTO hobby (name, description) VALUES ('memes', 'observing all kind of memes');

INSERT INTO role (name) VALUES ('moderator');
INSERT INTO role (name) VALUES ('reader');
INSERT INTO role (name) VALUES ('loh');

INSERT INTO postal_code (code) VALUES ('228322');
INSERT INTO postal_code (code) VALUES ('230023');
INSERT INTO postal_code (code) VALUES ('278322');
INSERT INTO postal_code (code) VALUES ('228324');
INSERT INTO postal_code (code) VALUES ('217322');

INSERT INTO account (nickname, email) VALUES ('PDChina', 'pdchina@gmail.com');
INSERT INTO account (nickname, email) VALUES ('sonicdeath', 'sonicdeath@gmail.com');
INSERT INTO account (nickname, email) VALUES ('humortranslate', 'humortranslate@gmail.com');
INSERT INTO account (nickname, email) VALUES ('anger6subu', 'anger6subu@gmail.com');

INSERT INTO country (name, iso_code) VALUES ('Belarus', 'ISO 3166-2:BY');
INSERT INTO country (name, iso_code) VALUES ('Russia', 'ISO 3166-2:RU');
INSERT INTO country (name, iso_code) VALUES ('Germany', 'ISO 3166-2:DE');
INSERT INTO country (name, iso_code) VALUES ('China', 'ISO 3166-2:CN');
INSERT INTO country (name, iso_code) VALUES ('USA', 'ISO 3166-2:US');

INSERT INTO app_user (role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at, modified_at) VALUES (1, 1, 2, 8, 'Sanya', 'sanya_boss@mail.ru', 2, 'M', current_timestamp, current_timestamp);
INSERT INTO app_user (role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at, modified_at) VALUES (2, 1, 2, 6, 'Valia', 'valia2002@mail.ru', 1, 'W', current_timestamp, current_timestamp);
INSERT INTO app_user (role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at, modified_at) VALUES (1, 4, 4, 7, 'Konfucyi', 'hello_world@yandex.ru', 4, 'M', current_timestamp, current_timestamp);
INSERT INTO app_user (role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at, modified_at) VALUES (3, 5, 3, 6, 'Subu Rakapatah', 'subu@teachpoint.com', 3, 'M', current_timestamp, current_timestamp);

INSERT INTO user_account (user_id, account_id) VALUES (2, 3);
INSERT INTO user_account (user_id, account_id) VALUES (2, 2);
INSERT INTO user_account (user_id, account_id) VALUES (4, 1);
INSERT INTO user_account (user_id, account_id) VALUES (5, 4);

INSERT INTO passport (user_id, country_id, number) VALUES (2, 1, 'KH12345678');
INSERT INTO passport (user_id, country_id, number) VALUES (3, 1, 'KH87654321');
INSERT INTO passport (user_id, country_id, number) VALUES (4, 4, 'DS4F44S2DD');

INSERT INTO visa (passport_id, country_id, type) VALUES (1, 1, 'National');
INSERT INTO visa (passport_id, country_id, type) VALUES (1, 3, 'Touristic');
INSERT INTO visa (passport_id, country_id, type) VALUES (2, 1, 'Touristic');
INSERT INTO visa (passport_id, country_id, type) VALUES (3, 4, 'National');
INSERT INTO visa (passport_id, country_id, type) VALUES (3, 5, 'Touristic');
