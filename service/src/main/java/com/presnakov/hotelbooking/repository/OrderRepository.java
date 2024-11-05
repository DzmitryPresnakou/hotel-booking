package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer>, FilterOrderRepository, RepositoryBase {

    List<Order> findOrdersByCheckInDate(LocalDate checkInDate);
}
