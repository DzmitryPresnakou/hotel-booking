package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.Hotel;
import jakarta.persistence.EntityManager;

public class HotelRepository extends RepositoryBase<Integer, Hotel> {

    public HotelRepository(EntityManager entityManager) {
        super(Hotel.class, entityManager);
    }
}
