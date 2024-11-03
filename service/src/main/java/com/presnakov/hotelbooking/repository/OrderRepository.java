package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Order;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Repository<Order, Integer> {

    Order save(Order entity);

    void delete(Order entity);

    Optional<Order> findById(Integer id);

    List<Order> findAll();

    List<Order> findOrdersByCheckInDate(LocalDate checkInDate);
}
