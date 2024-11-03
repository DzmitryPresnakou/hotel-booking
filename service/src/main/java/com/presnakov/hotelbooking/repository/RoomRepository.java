package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Room;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends Repository<Room, Integer> {

    Room save(Room entity);

    void delete(Room entity);

    Optional<Room> findById(Integer id);

    List<Room> findAll();
}
