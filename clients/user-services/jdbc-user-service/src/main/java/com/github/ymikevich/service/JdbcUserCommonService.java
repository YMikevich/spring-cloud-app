package com.github.ymikevich.service;

import com.github.ymikevich.service.assist.DatabaseRequestConsumer;
import com.github.ymikevich.service.assist.DatabaseRequestExecutor;
import com.github.ymikevich.service.assist.DatabaseRequestFunction;
import com.github.ymikevich.user.service.common.model.Account;
import com.github.ymikevich.user.service.common.model.Country;
import com.github.ymikevich.user.service.common.model.Gender;
import com.github.ymikevich.user.service.common.model.Hobby;
import com.github.ymikevich.user.service.common.model.Image;
import com.github.ymikevich.user.service.common.model.Passport;
import com.github.ymikevich.user.service.common.model.PostalCode;
import com.github.ymikevich.user.service.common.model.Role;
import com.github.ymikevich.user.service.common.model.User;
import com.github.ymikevich.user.service.common.model.Visa;
import com.github.ymikevich.user.service.common.service.UserCommonService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserCommonService implements UserCommonService {

    //language=SQL
    private static final String SQL_SELECT_ACCOUNTS_BY_USER_ID = """
            SELECT account.id AS account_id, account.nickname AS account_name, account.email AS account_email,
            user_account.user_id AS user_id
            FROM account
            JOIN user_account ON account.id = user_account.account_id
            JOIN app_user ON app_user.id = user_account.user_id
            WHERE app_user.id=?
            """;

    //language=SQL
    private static final String SQL_SELECT_ILLEGALS = """
            SELECT * FROM illegal_users WHERE country=?
            """;

    //language=SQL
    private static final String SQL_SELECT_VISAS_IN_PASSPORT = """
            SELECT visa.id AS visa_id, visa.type AS visa_type,
            country.id AS country_id, country.name AS country_name, country.iso_code AS country_iso_code
            FROM passport
            JOIN visa ON visa.passport_id=passport.id
            LEFT JOIN country ON visa.country_id = country.id
            WHERE passport.id=?
            """;

    //language=SQL
    private static final String SQL_SELECT_IMAGE_BY_ID = """
            SELECT * FROM image WHERE id=?;
            """;


    //language=SQL
    private static final String SQL_SELECT_COUNTRY_BY_ID = """
            SELECT * FROM country WHERE id=?
            """;

    //language=SQL
    private static final String SQL_SELECT_USER_BY_ID = """
            SELECT
            app_user.id AS user_id, user_partner.id AS partner_id, app_user.name AS username, app_user.email,
            country.id AS country_id, country.name AS country, country.iso_code, role.name AS role,
            postal_code.id AS postal_code_id, postal_code.code AS postal_code, hobby.id AS hobby_id,
            hobby.name AS hobby_name, hobby.description AS hobby_description, app_user.created_at, app_user.modified_at,
            app_user.gender, passport.id AS passport_id, passport.country_id AS passport_country_id,
            passport.image_id AS passport_image_id, passport.number AS passport_number, app_user.image_id AS image_id
            FROM app_user
            LEFT JOIN passport ON passport.user_id = app_user.id
            LEFT JOIN visa ON visa.id = passport.id
            LEFT JOIN role ON role.id = app_user.role_id
            LEFT JOIN country ON app_user.country_id = country.id
            LEFT JOIN app_user AS user_partner ON app_user.id = user_partner.partner_id
            LEFT JOIN postal_code ON app_user.postal_code_id = postal_code.id
            LEFT JOIN hobby ON app_user.hobby_id = hobby.id
            LEFT JOIN image ON app_user.image_id = image.id
            WHERE app_user.id=?
            """;

    //language=SQL
    private static final String SQL_INSERT_ACCOUNT = """
            INSERT INTO account (id, nickname, email)
            VALUES (?, ?, ?)
            """;

    //language=SQL
    private static final String SQL_UPDATE_ACCOUNT = """
            UPDATE account SET id=?, nickname=?, email=?
            WHERE id=?
            """;

    //language=SQL
    private static final String SQL_DELETE_ACCOUNT = """
            DELETE FROM account WHERE id=?
            """;

    //language=SQL
    private static final String SQL_LINK_USER_AND_ACCOUNT = """
            INSERT INTO user_account (user_id, account_id)
            VALUES (?, ?)
            """;

    //language=SQL
    private static final String SQL_UNLINK_USER_AND_ACCOUNT = """
            DELETE FROM user_account WHERE user_id=? AND account_id=?
            """;

    //language=SQL
    private static final String SQL_SELECT_USERS_BY_ROLE_AND_COUNTRY = """
            SELECT app_user.id AS user_id
            FROM app_user
            LEFT JOIN country ON app_user.country_id=country.id
            JOIN role ON app_user.role_id=role.id
            WHERE role.name=? AND country_id=?
            """;

    //language=SQL
    private static final String SQL_DELETE_VISA_IN_PASSPORT = """
            DELETE FROM visa
            WHERE visa.id IN
            (SELECT visa.id FROM visa
            JOIN passport ON visa.passport_id=passport.id
            JOIN app_user ON passport.user_id=app_user.id
            WHERE app_user.country_id!=visa.country_id AND visa.id=? AND app_user.id=?
            )
            """;

    //language=SQL
    private static final String SQL_SELECT_ACCOUNT = """
            SELECT id, nickname, email FROM account
            WHERE id=?
            """;

    //language=SQL
    private static final String SQL_SELECT_USER_ACCOUNT = """
            SELECT * FROM user_account
            WHERE account_id=?
            """;

    @Override
    public List<Account> findAllAccountsByUserId(Long id) {
        DatabaseRequestFunction<Connection, List<Account>> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_ACCOUNTS_BY_USER_ID);

            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            var userAccounts = new ArrayList<Account>();

            while (resultSet.next()) {
                var accountId = resultSet.getLong("account_id");
                var nickname = resultSet.getString("account_name");
                var email = resultSet.getString("account_email");
                var userId = resultSet.getLong("user_id");
                Account account = new Account();
                account.setId(accountId);
                account.setNickname(nickname);
                account.setEmail(email);
                account.setUserId(userId);
                userAccounts.add(account);
            }
            return userAccounts;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    @Override
    public List<User> findAllIllegalsFromCountry(String countryName) {
        DatabaseRequestFunction<Connection, List<User>> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_ILLEGALS);

            preparedStatement.setString(1, countryName);
            var resultSet = preparedStatement.executeQuery();
            var illegals = new ArrayList<User>();

            while (resultSet.next()) {

                var user = findUserById(resultSet.getLong("user_id"));
                illegals.add(user);
            }
            return illegals;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    public User findUserById(Long userId) {
        DatabaseRequestFunction<Connection, User> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var user = new User();
                var partnerId = resultSet.getLong("user_id");
                var name = resultSet.getString("username");
                var email = resultSet.getString("email");

                Country userCountry = null;
                if (resultSet.getLong("country_id") != 0) {
                    userCountry = new Country();
                    userCountry.setId(resultSet.getLong("country_id"));
                    userCountry.setName(resultSet.getString("country"));
                    userCountry.setIsoCode(resultSet.getString("iso_code"));
                }

                var role = Role.valueOf(resultSet.getString("role"));

                PostalCode postalCode = null;
                if (resultSet.getLong("postal_code_id") != 0) {
                    postalCode = new PostalCode();
                    postalCode.setId(resultSet.getLong("postal_code_id"));
                    postalCode.setCode(resultSet.getString("postal_code"));
                }

                var accounts = findAllAccountsByUserId(userId);

                Hobby hobby = null;
                if (resultSet.getLong("hobby_id") != 0) {
                    hobby = new Hobby();
                    hobby.setId(resultSet.getLong("hobby_id"));
                    hobby.setName(resultSet.getString("hobby_name"));
                    hobby.setDescription(resultSet.getString("hobby_description"));

                }

                var imageId = resultSet.getLong("image_id");
                Image image = null;
                if (imageId != 0) {
                    image = findImageById(imageId);
                }

                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var modifiedAt = resultSet.getTimestamp("modified_at").toLocalDateTime();

                var gender = Gender.valueOf(resultSet.getString("gender"));

                Passport passport = null;
                if (resultSet.getLong("passport_id") != 0) {
                    passport = new Passport();
                    passport.setId(resultSet.getLong("passport_id"));
                    passport.setCountry(findCountryById(resultSet.getLong("passport_country_id")));

                    var passportImageId = resultSet.getLong("passport_image_id");
                    Image passportImage = null;
                    if (passportImageId != 0) {
                        passportImage = findImageById(passportImageId);
                    }
                    passport.setImage(passportImage);
                    passport.setNumber(resultSet.getString("passport_number"));
                    passport.setVisaList(findVisasInPassport(resultSet.getLong("passport_id")));
                }

                user.setId(userId);
                user.setAccounts(accounts);
                user.setCountry(userCountry);
                user.setCreatedAt(createdAt);
                user.setEmail(email);
                user.setGender(gender);
                user.setHobby(hobby);
                user.setImage(image);
                user.setModifiedAt(modifiedAt);
                user.setPassport(passport);
                user.setPostalCode(postalCode);
                user.setRole(role);
                user.setName(name);
                user.setPartnerId(partnerId);
                return user;
            }
            return null;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    public Image findImageById(Long imageId) {
        DatabaseRequestFunction<Connection, Image> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_IMAGE_BY_ID);

            preparedStatement.setLong(1, imageId);
            var resultSet = preparedStatement.executeQuery();

            var image = new Image();
            if (resultSet.next()) {
                image.setId(resultSet.getLong("id"));
                image.setFileType(resultSet.getString("file_type"));
                image.setImage(resultSet.getBytes("image"));
                return image;
            }
            return null;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    public Country findCountryById(Long countryId) {
        DatabaseRequestFunction<Connection, Country> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_COUNTRY_BY_ID);

            preparedStatement.setInt(1, countryId.intValue());
            var resultSet = preparedStatement.executeQuery();
            var country = new Country();

            while (resultSet.next()) {
                country.setId(resultSet.getLong("id"));
                country.setName(resultSet.getString("name"));
                country.setIsoCode(resultSet.getString("iso_code"));
            }
            return country;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    public List<Visa> findVisasInPassport(Long passportId) {
        DatabaseRequestFunction<Connection, List<Visa>> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_VISAS_IN_PASSPORT);

            preparedStatement.setInt(1, passportId.intValue());
            var resultSet = preparedStatement.executeQuery();
            var visas = new ArrayList<Visa>();

            while (resultSet.next()) {
                var visaId = resultSet.getLong("visa_id");
                var visaType = resultSet.getString("visa_type");
                var countryId = resultSet.getLong("country_id");
                var countryName = resultSet.getString("country_name");
                var isoCode = resultSet.getString("country_iso_code");

                var country = new Country();
                country.setId(countryId);
                country.setName(countryName);
                country.setIsoCode(isoCode);

                var visa = new Visa();
                visa.setId(visaId);
                visa.setType(visaType);
                visa.setCountry(country);

                visas.add(visa);
            }
            return visas;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    public Account findAccountById(Long accountId) {
        DatabaseRequestFunction<Connection, Account> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_ACCOUNT);

            preparedStatement.setLong(1, accountId);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                var id = resultSet.getLong("id");
                var nickname = resultSet.getString("nickname");
                var email = resultSet.getString("email");
                var userId = 0L;

                preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ACCOUNT);
                preparedStatement.setLong(1, accountId);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    userId = resultSet.getLong("user_id");
                }

                var account = new Account();
                account.setId(id);
                account.setNickname(nickname);
                account.setEmail(email);
                account.setUserId(userId);
                return account;
            }
            return null;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    @Override
    public List<User> findUsersByRoleAndCountryInPassport(Role role, Long countryId) {
        DatabaseRequestFunction<Connection, List<User>> accountDatabaseRequestFunction = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE_AND_COUNTRY);

            preparedStatement.setString(1, role.name());
            preparedStatement.setLong(2, countryId);
            var resultSet = preparedStatement.executeQuery();

            var users = new ArrayList<User>();

            while (resultSet.next()) {
                var user = findUserById(resultSet.getLong("user_id"));
                users.add(user);
            }
            return users;
        };

        return DatabaseRequestExecutor.execute(accountDatabaseRequestFunction);
    }

    @Override
    public void linkUserWithTwitterAccount(Long userId, Long accountId) {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_LINK_USER_AND_ACCOUNT);

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.execute();
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Override
    public void unlinkUserFromTwitterAccount(Long userId, Long accountId) {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_UNLINK_USER_AND_ACCOUNT);

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.execute();
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Override
    public void saveAccount(Account account) {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_INSERT_ACCOUNT);

            preparedStatement.setLong(1, account.getId());
            preparedStatement.setString(2, account.getNickname());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.execute();
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Override
    public void updateAccountById(Long id, Account account) {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT);

            preparedStatement.setLong(1, account.getId());
            preparedStatement.setString(2, account.getNickname());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setLong(4, id);
            preparedStatement.execute();
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Override
    public void deleteAccountById(Long id) {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {

            var accountToDelete = findAccountById(id);
            if (accountToDelete == null) {
                return;
            }

            var preparedStatement = connection.prepareStatement(SQL_UNLINK_USER_AND_ACCOUNT);
            preparedStatement.setLong(1, accountToDelete.getUserId());
            preparedStatement.setLong(2, accountToDelete.getId());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT);
            preparedStatement.setLong(1, accountToDelete.getId());
            preparedStatement.execute();
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Override
    public void deleteVisaFromUserPassportWhichLivesInAnotherCountry(Long userId, Long visaId) {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_DELETE_VISA_IN_PASSPORT);

            preparedStatement.setLong(1, visaId);
            preparedStatement.setLong(2, userId);
            preparedStatement.execute();
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }
}
