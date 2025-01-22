package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer>, FilterHotelRepository, QuerydslPredicateExecutor<Hotel> {

    Optional<Hotel> findByName(String name);
}
