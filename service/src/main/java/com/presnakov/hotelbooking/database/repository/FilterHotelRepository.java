package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterHotelRepository {

    Page<Hotel> findAll(Pageable pageable);

    void softDelete(Hotel hotel);
}
