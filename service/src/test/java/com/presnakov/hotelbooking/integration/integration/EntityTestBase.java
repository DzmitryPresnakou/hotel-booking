package com.presnakov.hotelbooking.integration.integration;

import com.presnakov.hotelbooking.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public abstract class EntityTestBase {

    protected static SessionFactory sessionFactory;
    protected Session session;

    @BeforeAll
    static void createSessionFactory() {
        sessionFactory = HibernateUtil.buildSessionFactory();
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
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.clear();
    }

    @AfterEach
    void closeSession() {
        if (session != null && session.isOpen()) {
            session.getTransaction().rollback();
            session.close();
        }
    }
}
