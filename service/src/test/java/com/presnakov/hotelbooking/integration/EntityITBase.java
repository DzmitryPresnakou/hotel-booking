package com.presnakov.hotelbooking.integration;

import com.presnakov.hotelbooking.config.ApplicationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;

public abstract class EntityITBase {

    protected static SessionFactory sessionFactory;
    protected static Session session;
    protected static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    @BeforeAll
    static void createSessionFactory() {
        sessionFactory = context.getBean(SessionFactory.class);
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));;
    }

    @AfterAll
    static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void openSession() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            throw new IllegalStateException("SessionFactory doesn't exist");
        }
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        if (session != null && session.isOpen()) {
            session.getTransaction().rollback();
            session.close();
        }
    }
}
