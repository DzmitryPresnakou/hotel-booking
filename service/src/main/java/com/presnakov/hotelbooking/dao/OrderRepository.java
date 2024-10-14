package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.entity.QOrder.order;
import static com.presnakov.hotelbooking.entity.QRoom.room;
import static com.presnakov.hotelbooking.entity.QUser.user;

public class OrderRepository extends RepositoryBase<Integer, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findOrderByUserEmail(String email) {
        return new JPAQuery<Order>(getEntityManager())
                .select(order)
                .from(order)
                .join(order.user, user)
                .where(user.email.eq(email))
                .fetch();
    }

    public List<Order> findAllOrdersByHotelName(String name) {
        return new JPAQuery<Order>(getEntityManager())
                .select(order)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(hotel.name.eq(name))
                .fetch();
    }

    public List<Order> findOrdersByCheckInDate(LocalDate checkInDate) {
        return new JPAQuery<Order>(getEntityManager())
                .select(order)
                .from(order)
                .where(order.checkInDate.eq(checkInDate))
                .fetch();
    }
}
