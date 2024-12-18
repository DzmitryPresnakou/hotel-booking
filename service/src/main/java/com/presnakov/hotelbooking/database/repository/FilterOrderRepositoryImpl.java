package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.presnakov.hotelbooking.database.entity.QOrder.order;
import static com.presnakov.hotelbooking.database.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.database.entity.QRoom.room;
import static com.presnakov.hotelbooking.database.entity.QUser.user;

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
    public List<Order> findOrdersByUsername(String username) {
        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.user, user)
                .where(user.username.eq(username))
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
