package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface FilterOrderRepository {

    List<Order> findOrdersByDateRange(LocalDate startRange, LocalDate endRange);

    List<Order> findOrdersByUserEmail(String email);

    List<Order> findOrdersByHotelName(String name);
}
