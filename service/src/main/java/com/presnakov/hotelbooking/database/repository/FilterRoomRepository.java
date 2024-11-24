package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.database.entity.Room;

import java.util.List;

public interface FilterRoomRepository {

    List<Room> findAllByFilter(RoomFilter filter);

    List<Room> findAllByFreeDateRange(RoomFilter filter);

    List<Room> findAllByHotelName(String hotelName);
}
