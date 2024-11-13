package com.presnakov.hotelbooking.integration;

import com.presnakov.hotelbooking.integration.annotation.IT;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;

@IT
public abstract class IntegrationTestBase {

    @PersistenceContext
    protected Session session;
}
