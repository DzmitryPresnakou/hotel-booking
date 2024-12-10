package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.OrderFilter;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public Page<Order> findAllByFilter(OrderFilter filter, Pageable pageable) {
        JPAQuery<Order> query = new JPAQuery<>(entityManager)
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(getByCompleteInfo(filter));
        long total = query.fetchCount();
        List<Order> orders = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(orders, pageable, total);
    }

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
    public List<Order> findOrdersByHotelName(String hotelName) {
        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(hotel.name.eq(hotelName))
                .fetch();
    }

    private static Predicate getByCompleteInfo(OrderFilter filter) {
        return QPredicate.builder()
                .add(filter.getHotelName(), order.room.hotel.name::eq)
                .add(filter.getUsername(), order.user.username::eq)
                .add(filter.getCheckOutDate(), order.checkInDate::after)
                .add(filter.getCheckInDate(), order.checkOutDate::before)
                .buildAnd();
    }
}
