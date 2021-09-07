package util;

import repsostory.impl.TwitRepositoryImpl;
import repsostory.impl.UserRepositoryImpl;
import service.impl.TwitServiceImpl;
import service.impl.UserServiceImpl;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class ApplicationContext {

    private static final ApplicationContext applicationContext = new ApplicationContext();

    private final UserServiceImpl userService;
    private final TwitServiceImpl twitService;
    private final EntityManager entityManager;

    private final Scanner scannerForString;
    private final Scanner scannerForInteger;



    private ApplicationContext() {
        entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        userService = new UserServiceImpl(new UserRepositoryImpl(entityManager));
        twitService = new TwitServiceImpl(new TwitRepositoryImpl(entityManager));
        scannerForInteger = new Scanner(System.in);
        scannerForString = new Scanner(System.in);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public TwitServiceImpl getTwitService() {
        return twitService;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Scanner getScannerForString() {
        return scannerForString;
    }

    public Scanner getScannerForInteger() {
        return scannerForInteger;
    }
}
