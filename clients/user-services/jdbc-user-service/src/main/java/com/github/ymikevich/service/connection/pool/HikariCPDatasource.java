package com.github.ymikevich.service.connection.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDatasource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    private static Properties properties;

    static {
        initProperties();

        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void initProperties() {
        try (InputStream input = HikariCPDatasource.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties = new Properties();

            if (input == null) {
                throw new IllegalStateException("Couldn't find db.properties file in classpath");
            }
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private HikariCPDatasource() {
    }
}
