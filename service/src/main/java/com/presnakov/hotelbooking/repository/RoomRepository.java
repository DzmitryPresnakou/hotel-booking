package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends Repository<Room, Integer>, FilterRoomRepository {

    Room save(Room entity);

    void delete(Room entity);

    Optional<Room> findById(Integer id);

    List<Room> findAll();

    @EntityGraph(attributePaths = {"hotel"})
    @Query(value = "select r from Room r")
    List<Room> findAllByHotel(Hotel hotel);
}
