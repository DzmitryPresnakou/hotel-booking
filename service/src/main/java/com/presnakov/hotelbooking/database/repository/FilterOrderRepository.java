package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.dto.OrderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterOrderRepository {

    Page<Order> findAll(OrderFilter filter, Pageable pageable);
}
