package com.github.ymikevich.service;

import com.github.ymikevich.service.utils.DatabaseRequestConsumer;
import com.github.ymikevich.service.utils.DatabaseRequestExecutor;
import com.github.ymikevich.user.service.common.model.Gender;
import com.github.ymikevich.user.service.common.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
        };

        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @After
    public void rollback() {
        DatabaseRequestConsumer<Connection> accountDatabaseRequestConsumer = connection -> {
            connection.prepareStatement(SQL_DELETE_USER).execute();
            connection.prepareStatement(SQL_DELETE_ROLE).execute();
            connection.prepareStatement(SQL_DELETE_COUNTRY).execute();
        };
        DatabaseRequestExecutor.execute(accountDatabaseRequestConsumer);
    }

    @Test
    public void findAllAccountsByUserId() {
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
    public void findAllVisasInPassport() {
    }
}