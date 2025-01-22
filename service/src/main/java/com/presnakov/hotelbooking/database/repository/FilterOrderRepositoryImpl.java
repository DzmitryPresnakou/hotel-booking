package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.OrderFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.presnakov.hotelbooking.database.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.database.entity.QOrder.order;
import static com.presnakov.hotelbooking.database.entity.QRoom.room;
import static com.presnakov.hotelbooking.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterOrderRepositoryImpl implements FilterOrderRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Order> findAll(OrderFilter filter, Pageable pageable) {
        JPAQuery<Order> query = new JPAQuery<>(entityManager)
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(getPredicate(filter));
        long total = query.fetch().size();
        List<Order> orders = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        orders.stream()
                .filter(order -> order.getCheckOutDate().isBefore(LocalDate.now()))
                .forEach(order -> order.setStatus(OrderStatusEnum.CLOSED));
        return new PageImpl<>(orders, pageable, total);
    }

    private static Predicate getPredicate(OrderFilter filter) {
        return QPredicate.builder()
                .add((filter.getHotelName() != null && filter.getHotelName().isEmpty()) ?
                        null : filter.getHotelName(), order.room.hotel.name::eq)
                .add((filter.getUsername() != null && filter.getUsername().isEmpty()) ?
                        null :
                        filter.getUsername(), order.user.username::eq)
                .add(filter.getCheckOutDate(), order.checkInDate::after)
                .add(filter.getCheckInDate(), order.checkOutDate::before)
                .buildAnd();
    }

    public void softDelete(Order order) {
        order.setIsActive(false);
    }
}
