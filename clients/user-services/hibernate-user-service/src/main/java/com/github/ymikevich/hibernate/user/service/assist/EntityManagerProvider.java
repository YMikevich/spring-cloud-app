package com.github.ymikevich.hibernate.user.service.assist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EntityManagerProvider {

    private static final String PERSISTENCE_UNIT_NAME;
    private static EntityManagerFactory factory;
    private static Properties properties;

    static {
        initProperties();
        PERSISTENCE_UNIT_NAME = properties.getProperty("persistence.unit.name");
    }

    private EntityManagerProvider() {

    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    private static void initProperties() {
        try (InputStream input = EntityManagerProvider.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();

            if (input == null) {
                throw new IllegalStateException("Couldn't find application.properties file in classpath");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Unexpected IO exception while loading property file(probably it's "
                    + "not present)", ex);
        }
    }
}
