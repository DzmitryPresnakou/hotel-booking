package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, FilterOrderRepository {

    List<Order> findOrdersByCheckInDate(LocalDate checkInDate);
}
