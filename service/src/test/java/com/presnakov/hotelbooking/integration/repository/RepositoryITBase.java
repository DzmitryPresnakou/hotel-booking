package com.presnakov.hotelbooking.integration.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

//@Transactional
//@NoArgsConstructor()
@SpringBootTest
public abstract class RepositoryITBase {

    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.0");

//    protected static AnnotationConfigApplicationContext applicationContext;

    @DynamicPropertySource
    private static void registerDataSourceProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
//        applicationContext = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
//        applicationContext.close();
    }

}
