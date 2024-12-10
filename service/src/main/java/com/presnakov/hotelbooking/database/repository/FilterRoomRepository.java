package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.dto.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterRoomRepository {

    Page<Room> findAllByFilter(RoomFilter filter, Pageable pageable);

    Page<Room> findAllByFreeDateRange(RoomFilter filter, Pageable pageable);

    Page<Room> findAllByHotelName(String hotelName, Pageable pageable);
}
