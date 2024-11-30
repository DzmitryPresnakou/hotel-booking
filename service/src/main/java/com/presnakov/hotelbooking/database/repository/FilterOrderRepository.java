package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface FilterOrderRepository {

    List<Order> findOrdersByDateRange(LocalDate startRange, LocalDate endRange);

    List<Order> findOrdersByUsername(String username);

    List<Order> findOrdersByHotelName(String name);
}
