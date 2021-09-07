package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class HibernateUtil {

    private static EntityManagerFactory entityManagerFactory;


    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory("default");

        return entityManagerFactory;
    }
}
