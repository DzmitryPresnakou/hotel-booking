package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.Room;

import java.util.List;

public interface FilterRoomRepository {

    List<Room> findAllByFilter(RoomFilter filter);

    List<Room> findAllByFreeDateRange(RoomFilter filter);

    void update(Room entity);
}
