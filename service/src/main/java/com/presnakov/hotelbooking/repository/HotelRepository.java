package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface HotelRepository extends
        JpaRepository<Hotel, Integer>,
        QuerydslPredicateExecutor<Hotel> {

    Optional<Hotel> findByName(String name);
}
