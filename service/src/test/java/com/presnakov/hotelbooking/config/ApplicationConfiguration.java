package com.presnakov.hotelbooking.config;

import com.presnakov.hotelbooking.repository.HotelRepository;
import com.presnakov.hotelbooking.repository.OrderRepository;
import com.presnakov.hotelbooking.repository.RoomRepository;
import com.presnakov.hotelbooking.repository.UserRepository;
import com.presnakov.hotelbooking.util.HibernateTestUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateTestUtil.buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
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
