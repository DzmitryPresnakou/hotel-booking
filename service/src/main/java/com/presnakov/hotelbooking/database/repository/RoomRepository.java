package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RoomRepository extends
        JpaRepository<Room, Integer>,
        FilterRoomRepository,
        QuerydslPredicateExecutor<Room> {
}
