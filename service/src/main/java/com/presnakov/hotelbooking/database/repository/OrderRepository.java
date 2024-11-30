package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends
        JpaRepository<Order, Integer>,
        FilterOrderRepository,
        QuerydslPredicateExecutor<Order> {

    List<Order> findOrdersByCheckInDate(LocalDate checkInDate);
}
