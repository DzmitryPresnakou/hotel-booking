package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer>, FilterRoomRepository {

    @EntityGraph(attributePaths = {"hotel"})
    @Query(value = "select r from Room r")
    List<Room> findAllByHotel(Hotel hotel);
}
