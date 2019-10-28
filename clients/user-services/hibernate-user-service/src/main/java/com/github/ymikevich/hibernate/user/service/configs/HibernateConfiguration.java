package com.github.ymikevich.hibernate.user.service.configs;

import com.github.ymikevich.hibernate.user.service.model.Account;
import com.github.ymikevich.hibernate.user.service.model.Country;
import com.github.ymikevich.hibernate.user.service.model.Hobby;
import com.github.ymikevich.hibernate.user.service.model.Image;
import com.github.ymikevich.hibernate.user.service.model.Passport;
import com.github.ymikevich.hibernate.user.service.model.PostalCode;
import com.github.ymikevich.hibernate.user.service.model.Role;
import com.github.ymikevich.hibernate.user.service.model.User;
import com.github.ymikevich.hibernate.user.service.model.Visa;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class HibernateConfiguration {

    private static SessionFactory sessionFactory;
    private static Properties properties;

    static {
        initProperties();
    }

    private HibernateConfiguration() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                var configuration = new Configuration();

                configuration.setProperties(hibernateProperties());
                configuration.addAnnotatedClass(Account.class);
                configuration.addAnnotatedClass(Country.class);
                configuration.addAnnotatedClass(Hobby.class);
                configuration.addAnnotatedClass(Image.class);
                configuration.addAnnotatedClass(Passport.class);
                configuration.addAnnotatedClass(PostalCode.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Visa.class);
                configuration.addAnnotatedClass(Role.class);
                var serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException ex) {
                throw new IllegalStateException("Unexpected Hibernate exception in"
                        + " HibernateConfiguration.class", ex);
            }
        }

        return sessionFactory;
    }

    private static Properties hibernateProperties() {
        var settings = new Properties();
        settings.put(Environment.DRIVER, properties.getProperty("db.driver"));
        settings.put(Environment.URL, properties.getProperty("db.url"));
        settings.put(Environment.USER, properties.getProperty("db.username"));
        settings.put(Environment.PASS, properties.getProperty("db.password"));
        settings.put(Environment.DIALECT, properties.getProperty("hibernate.dialect"));
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.HBM2DDL_AUTO, properties.getProperty("hibernate.hbm2ddl"));
        // Maximum waiting time for a connection from the pool
        settings.put("hibernate.hikari.connectionTimeout", "20000");
        // Minimum number of ideal connections in the pool
        settings.put("hibernate.hikari.minimumIdle", "10");
        // Maximum number of actual connection in the pool
        settings.put("hibernate.hikari.maximumPoolSize", "20");
        // Maximum time that a connection is allowed to sit ideal in the pool
        settings.put("hibernate.hikari.idleTimeout", "300000");

        return settings;
    }

    private static void initProperties() {
        try (InputStream input = HibernateConfiguration.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            properties = new Properties();

            if (input == null) {
                throw new IllegalStateException("Couldn't find hibernate.properties file in classpath");
            }
            properties.load(input);

        } catch (IOException ex) {
            throw new IllegalStateException("Unexpected IO exception during properties initialization in"
                    + " HibernateConfiguration.class", ex);
        }
    }
}
