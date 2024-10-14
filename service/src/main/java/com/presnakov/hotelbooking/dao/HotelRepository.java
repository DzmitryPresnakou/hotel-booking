package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.Hotel;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;

public class HotelRepository extends RepositoryBase<Integer, Hotel> {

    public HotelRepository(EntityManager entityManager) {
        super(Hotel.class, entityManager);
    }

    public Optional<Hotel> findByName(String name) {
        return Optional.ofNullable(new JPAQuery<Hotel>(getEntityManager())
                .select(hotel)
                .from(hotel)
                .where(hotel.name.eq(name))
                .fetchOne());
    }
}
