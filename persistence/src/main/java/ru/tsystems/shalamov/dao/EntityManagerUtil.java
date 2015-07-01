package ru.tsystems.shalamov.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by viacheslav on 30.06.2015.
 */
public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory = null;


    private EntityManagerUtil()
    {}

    /**
     * Constructs a new Singleton EntityManagerFactory
     *
     * @return
     */
    public static EntityManagerFactory buildSessionFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("logiweb");
        return entityManagerFactory;
    }

    /**
     * Builds a EntityManagerFactory, if it hasn't been already.
     */
    public static EntityManagerFactory buildIfNeeded() {
        if (entityManagerFactory != null) {
            return entityManagerFactory;
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("logiweb");
        return entityManagerFactory;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager createEntityManager() {
        buildIfNeeded();
        return entityManagerFactory.createEntityManager();
    }

    public static void closeFactory() {
        if (entityManagerFactory != null) {
                entityManagerFactory.close();
        }
    }

    public static void close(EntityManager entityManager) {
        if (entityManager != null) {
                entityManager.close();
        }
    }
}
