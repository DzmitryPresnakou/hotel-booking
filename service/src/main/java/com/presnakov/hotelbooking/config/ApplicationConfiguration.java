package com.presnakov.hotelbooking.config;

import com.presnakov.hotelbooking.repository.HotelRepository;
import com.presnakov.hotelbooking.repository.OrderRepository;
import com.presnakov.hotelbooking.repository.RoomRepository;
import com.presnakov.hotelbooking.repository.UserRepository;
import com.presnakov.hotelbooking.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.presnakov.hotelbooking")
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

    @Bean
    public HotelRepository hotelRepository(EntityManager entityManager) {
        return new HotelRepository(entityManager);
    }

    @Bean
    public OrderRepository orderRepository(EntityManager entityManager) {
        return new OrderRepository(entityManager);
    }

    @Bean
    public RoomRepository roomRepository(EntityManager entityManager) {
        return new RoomRepository(entityManager);
    }

    @Bean
    public UserRepository userRepository(EntityManager entityManager) {
        return new UserRepository(entityManager);
    }
}
