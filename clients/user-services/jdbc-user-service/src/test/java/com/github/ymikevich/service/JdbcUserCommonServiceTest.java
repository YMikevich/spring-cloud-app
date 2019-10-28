package com.github.ymikevich.service;

import com.github.ymikevich.service.assist.DatabaseRequestConsumer;
import com.github.ymikevich.service.assist.DatabaseRequestExecutor;
import com.github.ymikevich.user.service.common.model.Account;
import com.github.ymikevich.user.service.common.model.Gender;
import com.github.ymikevich.user.service.common.model.Role;
import com.github.ymikevich.user.service.common.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class JdbcUserCommonServiceTest {

    //language=SQL
    private static final String SQL_INSERT_ROLE = """
            INSERT INTO role (id, name)
            VALUES (?, ?)
            """;

    //language=SQL
    private static final String SQL_INSERT_USER = """
            INSERT INTO app_user (id, role_id, country_id, created_at,
            modified_at, name, partner_id, gender)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    //language=SQL
    private static final String SQL_INSERT_COUNTRY = """
            INSERT INTO country (id, name, iso_code)
            VALUES (?, ?, ?)
            """;

    //language=SQL
    private static final String SQL_DELETE_USER = """
            DELETE FROM app_user
            """;

    //language=SQL
    private static final String SQL_DELETE_ROLE = """
            DELETE FROM role
            """;

    //language=SQL
    private static final String SQL_DELETE_COUNTRY = """
            DELETE FROM country
            """;

    //language=SQL
    private static final String SQL_INSERT_ACCOUNT = """
            INSERT INTO account (id, nickname, email)
            VALUES (?, ?, ?)
            """;

    //language=SQL
    private static final String SQL_LINK_USER_AND_ACCOUNT = """
            INSERT INTO user_account (user_id, account_id)
            VALUES (?, ?)
            """;

    //language=SQL
    private static final String SQL_DELETE_ACCOUNT = """
            DELETE FROM account
            """;

    //language=SQL
    private static final String SQL_DELETE_USER_ACCOUNT = """
            DELETE FROM user_account
            """;

    //language=SQL
    private static final String SQL_INSERT_IMAGE = """
            INSERT INTO image (id, file_type)
            VALUES (?, ?)
            """;

    //language=SQL
    private static final String SQL_DELETE_IMAGE = """
            DELETE FROM image
            """;

    //language=SQL
    private static final String SQL_INSERT_PASSPORT = """
            INSERT INTO passport (id, number, user_id)
            VALUES (?, ?, ?)
            """;

    //language=SQL
    private static final String SQL_DELETE_PASSPORT = """
            DELETE FROM passport
            """;

    //language=SQL
    private static final String SQL_INSERT_VISA = """
            INSERT INTO visa (id, passport_id, country_id)
            VALUES (?, ?, ?)
            """;

    //language=SQL
    private static final String SQL_DELETE_VISA = """
            DELETE FROM visa
            """;

    private JdbcUserCommonService service = new JdbcUserCommonService();

    @Before
    public void populateDatabase() {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            var preparedStatement = connection.prepareStatement(SQL_INSERT_ROLE);
            preparedStatement.setLong(1, 2L);
            preparedStatement.setString(2, "READER");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_COUNTRY);
            preparedStatement.setLong(1, 1L);
            preparedStatement.setString(2, "USA");
            preparedStatement.setString(3, "ISOCODE");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_COUNTRY);
            preparedStatement.setLong(1, 2L);
            preparedStatement.setString(2, "Belarus");
            preparedStatement.setString(3, "ISOCODE2");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setLong(1, 100L);
            preparedStatement.setLong(2, 2L);
            preparedStatement.setLong(3, 1L);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, "Sanya");
            preparedStatement.setLong(7, 101L);
            preparedStatement.setString(8, Gender.MAN.name());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setLong(1, 101L);
            preparedStatement.setLong(2, 2L);
            preparedStatement.setLong(3, 1L);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, "Dima");
            preparedStatement.setLong(7, 100L);
            preparedStatement.setString(8, Gender.MAN.name());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_ACCOUNT);
            preparedStatement.setLong(1, 1L);
            preparedStatement.setString(2, "user1");
            preparedStatement.setString(3, "email@mail");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_ACCOUNT);
            preparedStatement.setLong(1, 3L);
            preparedStatement.setString(2, "user3");
            preparedStatement.setString(3, "e@mail");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_ACCOUNT);
            preparedStatement.setLong(1, 2L);
            preparedStatement.setString(2, "user2");
            preparedStatement.setString(3, "email@mail.ru");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_LINK_USER_AND_ACCOUNT);
            preparedStatement.setLong(1, 100L);
            preparedStatement.setLong(2, 1L);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_LINK_USER_AND_ACCOUNT);
            preparedStatement.setLong(1, 100L);
            preparedStatement.setLong(2, 2L);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_IMAGE);
            preparedStatement.setLong(1, 1L);
            preparedStatement.setString(2, "pdf");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_PASSPORT);
            preparedStatement.setLong(1, 1L);
            preparedStatement.setString(2, "KH123456");
            preparedStatement.setLong(3, 100L);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_VISA);
            preparedStatement.setLong(1, 1L);
            preparedStatement.setLong(2, 1L);
            preparedStatement.setLong(3, 1L);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL_INSERT_VISA);
            preparedStatement.setLong(1, 2L);
            preparedStatement.setLong(2, 1L);
            preparedStatement.setLong(3, 2L);
            preparedStatement.execute();

        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @After
    public void rollback() {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            connection.prepareStatement(SQL_DELETE_VISA).execute();
            connection.prepareStatement(SQL_DELETE_PASSPORT).execute();
            connection.prepareStatement(SQL_DELETE_IMAGE).execute();
            connection.prepareStatement(SQL_DELETE_USER_ACCOUNT).execute();
            connection.prepareStatement(SQL_DELETE_ACCOUNT).execute();
            connection.prepareStatement(SQL_DELETE_USER).execute();
            connection.prepareStatement(SQL_DELETE_ROLE).execute();
            connection.prepareStatement(SQL_DELETE_COUNTRY).execute();
        };
        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Test
    public void findAllAccountsByUserId() {
        //given
        var userId = 100L;

        //when
        var accounts = service.findAllAccountsByUserId(userId);

        //then
        assertEquals(accounts.size(), 2);
    }

    @Test
    public void findImageById() {
        //given
        var imageId = 1L;

        //when
        var image = service.findImageById(imageId);

        //then
        assertEquals(image.getId(), Long.valueOf(imageId));
        assertEquals(image.getFileType(), "pdf");
    }

    @Test
    public void findAllIllegalsFromCountry() {
        //given
        var countryName = "USA";

        //when
        var illegals = service.findAllIllegalsFromCountry(countryName);

        //then
        for (User user : illegals) {
            assertNull(user.getPassport());
            assertNotEquals(user.getRole().name(), "LOH");
        }
    }

    @Test
    public void findUserById() {
        //when
        var user = service.findUserById(100L);

        //then
        assertEquals(user.getId(), Long.valueOf(100));
        assertEquals(user.getName(), "Sanya");
    }

    @Test
    public void findCountryById() {
        //when
        var country = service.findCountryById(1L);

        //then
        assertEquals(country.getId(), Long.valueOf(1));
        assertEquals(country.getName(), "USA");
    }

    @Test
    public void findVisasInPassport() {
        //given
        var passportId = 1L;

        //when
        var visas = service.findVisasInPassport(passportId);

        //then
        assertEquals(visas.size(), 2);
    }

    @Test
    public void findUsersByRoleAndCountryInPassport() {
        //given
        var role = Role.READER;
        var countryId = 1L;

        //when
        var users = service.findUsersByRoleAndCountryInPassport(role, countryId);

        //then
        assertEquals(users.size(), 2);
    }

    @Test
    public void linkUserWithTwitterAccount() {
        //given
        var userId = 100L;
        var accountId = 3L;

        //when
        service.linkUserWithTwitterAccount(userId, accountId);
        var accounts = service.findAllAccountsByUserId(userId);

        //then
        assertEquals(accounts.size(), 3);
    }

    @Test
    public void unlinkUserFromTwitterAccount() {
        //given
        var userId = 100L;
        var accountId = 2L;

        //when
        service.unlinkUserFromTwitterAccount(userId, accountId);
        var accounts = service.findAllAccountsByUserId(userId);

        //then
        assertEquals(accounts.size(), 1);
    }

    @Test
    public void saveAccount() {
        //given
        var account = new Account();
        account.setId(10L);
        account.setNickname("Ship");

        //when
        service.saveAccount(account);
        var accountFromService = service.findAccountById(10L);

        //then
        assertNotNull(accountFromService);
        assertEquals(accountFromService.getId(), account.getId());
        assertEquals(accountFromService.getNickname(), account.getNickname());
    }

    @Test
    public void updateAccountById() {
        //given
        var account = new Account();
        account.setId(1L);
        account.setNickname("Bred");
        account.setEmail("hello@gmail.com");
        account.setUserId(100L);
        var accountId = 1L;

        //when
        service.updateAccountById(accountId, account);
        var accountFromService = service.findAccountById(1L);

        //then
        assertEquals(accountFromService.getId(), account.getId());
        assertEquals(accountFromService.getNickname(), account.getNickname());
        assertEquals(accountFromService.getEmail(), account.getEmail());
    }

    @Test
    public void deleteAccountById() {
        //given
        var accountId = 1L;

        //when
        service.deleteAccountById(accountId);
        var deletedAccount = service.findAccountById(accountId);

        //then
        assertNull(deletedAccount);
    }

    @Test
    public void deleteVisaFromUserPassportWhichLivesInAnotherCountry() {
        //given
        var userId = 100L;
        var visaId = 2L;
        var passportId = 1L;

        //when
        service.deleteVisaFromUserPassportWhichLivesInAnotherCountry(userId, visaId);
        var userVisas = service.findVisasInPassport(passportId);

        //then
        assertEquals( 1, userVisas.size());
        assertNotEquals(userVisas.get(0).getId(), Long.valueOf(visaId));
    }
}