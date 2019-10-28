package com.github.ymikevich.hibernate.user.service.assist;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EntityManagerFactoryProvider {

    private static final String PERSISTENCE_UNIT_NAME = "user-service-persistence-unit";
    private static EntityManagerFactory factory;

    private EntityManagerFactoryProvider() {

    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
