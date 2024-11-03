package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends Repository<Hotel, Integer> {

    Hotel save(Hotel entity);

    void delete(Hotel entity);

    Optional<Hotel> findById(Integer id);

    Optional<Hotel> findByName(String name);

    List<Hotel> findAll();
}
