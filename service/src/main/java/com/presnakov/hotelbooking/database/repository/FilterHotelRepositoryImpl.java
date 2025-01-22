package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;

public class FilterHotelRepositoryImpl implements FilterHotelRepository{

    public void softDelete(Hotel hotel) {
        hotel.setIsActive(false);
    }
}
