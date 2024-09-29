package com.presnakov.hotelbooking;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.Order;
import com.presnakov.hotelbooking.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.Room;
import com.presnakov.hotelbooking.entity.RoomClassEnum;
import com.presnakov.hotelbooking.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;


public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        Configuration configuration = new Configuration();

        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Hotel hotel = Hotel.builder()
                    .name("Hilton3")
                    .photo("hotelphoto03.jpg")
                    .build();

            Room room = Room.builder()
                    .occupancy(4)
                    .roomClass(RoomClassEnum.ECONOMY)
                    .photo("photoroom002.jpg")
                    .pricePerDay(69)
                    .hotel(hotel)
                    .build();

            User user = User.builder()
                    .firstName("Vasya")
                    .lastName("Vasilyev")
                    .email("vasya25@gmail.com")
                    .phone("+375291534863")
                    .photo("userphoto001.jpg")
                    .birthDate(LocalDate.of(1995, 10, 15))
                    .money(2500)
                    .password("12345")
                    .role(RoleEnum.USER)
                    .isActive(true)
                    .build();

            Order order = Order.builder()
                    .user(user)
                    .room(room)
                    .status(OrderStatusEnum.OPEN)
                    .paymentStatus(PaymentStatusEnum.APPROVED)
                    .checkInDate(LocalDate.of(2024, 10, 15))
                    .checkOutDate(LocalDate.of(2024, 10, 25))
                    .build();

            session.saveOrUpdate(hotel);
            session.saveOrUpdate(room);
            session.saveOrUpdate(user);
            session.saveOrUpdate(order);

            session.getTransaction().commit();
        }
    }
}