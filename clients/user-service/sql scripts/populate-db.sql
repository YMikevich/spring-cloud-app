INSERT INTO hobby (id, name, description)
VALUES (1, 'music', 'playing guitar');
INSERT INTO hobby (id, name, description)
VALUES (2, 'music', 'playing piano');
INSERT INTO hobby (id, name, description)
VALUES (3, 'travelling', 'visiting different countries');
INSERT INTO hobby (id, name, description)
VALUES (4, 'streetracing', 'night races on sport cars');
INSERT INTO hobby (id, name, description)
VALUES (5, 'memes', 'observing all kind of memes');

INSERT INTO role (id, name)
VALUES (1, 'moderator');
INSERT INTO role (id, name)
VALUES (2, 'reader');
INSERT INTO role (id, name)
VALUES (3, 'loh');

INSERT INTO postal_code (id, code)
VALUES (1, '228322');
INSERT INTO postal_code (id, code)
VALUES (2, '230023');
INSERT INTO postal_code (id, code)
VALUES (3, '278322');
INSERT INTO postal_code (id, code)
VALUES (4, '228324');
INSERT INTO postal_code (id, code)
VALUES (5, '217322');

INSERT INTO account (id, nickname, email)
VALUES (1, 'PDChina', 'pdchina@gmail.com');
INSERT INTO account (id, nickname, email)
VALUES (2, 'sonicdeath', 'sonicdeath@gmail.com');
INSERT INTO account (id, nickname, email)
VALUES (3, 'humortranslate', 'humortranslate@gmail.com');
INSERT INTO account (id, nickname, email)
VALUES (4, 'anger6subu', 'anger6subu@gmail.com');

INSERT INTO country (id, name, iso_code)
VALUES (1, 'Belarus', 'ISO 3166-2:BY');
INSERT INTO country (id, name, iso_code)
VALUES (2, 'Russia', 'ISO 3166-2:RU');
INSERT INTO country (id, name, iso_code)
VALUES (3, 'Germany', 'ISO 3166-2:DE');
INSERT INTO country (id, name, iso_code)
VALUES (4, 'China', 'ISO 3166-2:CN');
INSERT INTO country (id, name, iso_code)
VALUES (5, 'USA', 'ISO 3166-2:US');

INSERT INTO app_user (id, role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at,
                      modified_at)
VALUES (1, 1, 1, 2, 4, 'Sanya', 'sanya_boss@mail.ru', 2, 'M', current_timestamp, current_timestamp);
INSERT INTO app_user (id, role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at,
                      modified_at)
VALUES (2, 2, 1, 2, 2, 'Valia', 'valia2002@mail.ru', 1, 'W', current_timestamp, current_timestamp);
INSERT INTO app_user (id, role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at,
                      modified_at)
VALUES (3, 1, 4, 4, 3, 'Konfucyi', 'hello_world@yandex.ru', 4, 'M', current_timestamp, current_timestamp);
INSERT INTO app_user (id, role_id, country_id, postal_code_id, hobby_id, name, email, partner, gender, created_at,
                      modified_at)
VALUES (4, 2, 5, 3, 2, 'Rakapatah', 'rakapatah@gmail.com', 3, 'M', current_timestamp, current_timestamp);

INSERT INTO user_account (user_id, account_id)
VALUES (1, 3);
INSERT INTO user_account (user_id, account_id)
VALUES (1, 2);
INSERT INTO user_account (user_id, account_id)
VALUES (3, 1);
INSERT INTO user_account (user_id, account_id)
VALUES (4, 4);

INSERT INTO passport (id, user_id, country_id, number)
VALUES (1, 1, 1, 'KH12345678');
INSERT INTO passport (id, user_id, country_id, number)
VALUES (2, 2, 1, 'KH87654321');
INSERT INTO passport (id, user_id, country_id, number)
VALUES (3, 3, 4, 'DS4F44S2DD');

INSERT INTO visa (id, passport_id, country_id, type)
VALUES (1, 1, 1, 'National');
INSERT INTO visa (id, passport_id, country_id, type)
VALUES (2, 1, 3, 'Touristic');
INSERT INTO visa (id, passport_id, country_id, type)
VALUES (3, 2, 1, 'Touristic');
INSERT INTO visa (id, passport_id, country_id, type)
VALUES (4, 3, 4, 'National');
INSERT INTO visa (id, passport_id, country_id, type)
VALUES (5, 3, 5, 'Touristic');
