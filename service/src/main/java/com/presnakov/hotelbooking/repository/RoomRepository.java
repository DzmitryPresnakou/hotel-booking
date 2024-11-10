package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer>, FilterRoomRepository {
}
