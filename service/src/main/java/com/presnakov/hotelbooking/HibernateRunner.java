package com.presnakov.hotelbooking;

import com.presnakov.hotelbooking.dao.HotelRepository;
import com.presnakov.hotelbooking.dao.RoomRepository;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.RoomClassEnum;
import com.presnakov.hotelbooking.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();

            var roomRepository = new RoomRepository(session);
            RoomFilter filter = RoomFilter.builder()
                    .hotelName("First World Hotel & Plaza")
                    .occupancy(2)
                    .pricePerDay(29)
                    .roomClass(RoomClassEnum.ECONOMY)
                    .checkInDate(LocalDate.of(2024, 10, 10))
                    .checkOutDate(LocalDate.of(2024, 10, 15))
                    .build();
            System.out.println(roomRepository.findAllByHotelNameClassOccupancyPriceFreeDate(filter));

            session.getTransaction().commit();
        }
    }
}
