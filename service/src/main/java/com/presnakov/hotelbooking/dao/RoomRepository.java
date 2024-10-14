package com.presnakov.hotelbooking.dao;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.Room;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.entity.QOrder.order;
import static com.presnakov.hotelbooking.entity.QRoom.room;

public class RoomRepository extends RepositoryBase<Integer, Room> {

    public RoomRepository(EntityManager entityManager) {
        super(Room.class, entityManager);
    }

    public List<Room> findAllByHotelNameClassOccupancyPriceFreeDate(RoomFilter filter) {
        return new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(getFirstPredicate(filter), getSecondPredicate(filter))
                .fetch();
    }

    private static Predicate getSecondPredicate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getCheckOutDate(), order.checkInDate::after)
                .add(filter.getCheckOutDate(), order.checkInDate::eq)
                .add(filter.getCheckInDate(), order.checkOutDate::before)
                .add(filter.getCheckInDate(), order.checkOutDate::eq)
                .buildOr();
    }

    private static Predicate getFirstPredicate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getHotelName(), hotel.name::eq)
                .add(filter.getOccupancy(), room.occupancy::eq)
                .add(filter.getPricePerDay(), room.pricePerDay::eq)
                .add(filter.getRoomClass(), room.roomClass::eq)
                .buildAnd();
    }
}
