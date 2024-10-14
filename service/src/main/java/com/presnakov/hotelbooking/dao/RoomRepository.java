package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.entity.Room;
import jakarta.persistence.EntityManager;

public class RoomRepository extends RepositoryBase<Integer, Room> {

    public RoomRepository(EntityManager entityManager) {
        super(Room.class, entityManager);
    }
}
