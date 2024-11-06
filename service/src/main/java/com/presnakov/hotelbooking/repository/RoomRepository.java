package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer>, FilterRoomRepository {
}
