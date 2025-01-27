package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterHotelRepository {

    Page<Hotel> findAll(Pageable pageable);

    List<Hotel> findAll();

    void softDelete(Hotel hotel);
}
