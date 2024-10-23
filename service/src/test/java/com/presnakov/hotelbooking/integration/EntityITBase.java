package com.presnakov.hotelbooking.integration;

import com.presnakov.hotelbooking.config.ApplicationTestConfiguration;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class EntityITBase {

    protected static AnnotationConfigApplicationContext applicationContext;
    protected Session session;

    @BeforeAll
    static void createApplicationContext() {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);
    }

    @AfterAll
    static void closeApplicationContext() {
        applicationContext.close();
    }

    @BeforeEach
    void openSession() {
        session = applicationContext.getBean(Session.class);
        session.getTransaction().begin();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
    }
}
