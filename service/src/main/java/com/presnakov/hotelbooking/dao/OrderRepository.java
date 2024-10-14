package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.Order;
import jakarta.persistence.EntityManager;

public class OrderRepository extends RepositoryBase<Integer, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
