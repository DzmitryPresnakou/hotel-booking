package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.dto.OrderFilter;
import com.presnakov.hotelbooking.dto.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FilterOrderRepository {

    Page<Order> findAllByFilter(OrderFilter filter, Pageable pageable);

    List<Order> findOrdersByDateRange(LocalDate startRange, LocalDate endRange);

    List<Order> findOrdersByUsername(String username);

    List<Order> findOrdersByHotelName(String name);
}
