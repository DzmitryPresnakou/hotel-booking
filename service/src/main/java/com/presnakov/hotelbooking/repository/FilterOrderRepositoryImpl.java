package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.entity.QOrder.order;
import static com.presnakov.hotelbooking.entity.QRoom.room;
import static com.presnakov.hotelbooking.entity.QUser.user;

@RequiredArgsConstructor
public class FilterOrderRepositoryImpl implements FilterOrderRepository {

    private final EntityManager entityManager;

    @Override
    public List<Order> findOrdersByDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .where(order.checkInDate.before(checkOutDate)
                        .and(order.checkOutDate.after(checkInDate)))
                .fetch();
    }

    @Override
    public List<Order> findOrdersByUserEmail(String email) {
        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.user, user)
                .where(user.email.eq(email))
                .fetch();
    }

    @Override
    public List<Order> findOrdersByHotelName(String name) {
        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(hotel.name.eq(name))
                .fetch();
    }
}
