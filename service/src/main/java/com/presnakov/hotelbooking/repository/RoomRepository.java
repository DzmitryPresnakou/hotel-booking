package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RoomRepository extends
        JpaRepository<Room, Integer>,
        FilterRoomRepository,
        QuerydslPredicateExecutor<Room> {
}
