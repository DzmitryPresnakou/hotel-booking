package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;

public interface FilterHotelRepository {

    void softDelete(Hotel hotel);
}
