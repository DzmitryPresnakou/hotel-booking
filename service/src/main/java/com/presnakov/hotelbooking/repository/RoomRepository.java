package com.presnakov.hotelbooking.repository;

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

    public List<Room> findAllRoomsByFilter(RoomFilter filter) {
        List<Room> rooms = new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(room)
                .where(getByCompleteInfo(filter))
                .fetch();

        List<Room> bookedRooms = new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(getByBookedDateRange(filter))
                .fetch();
        for (Room room : bookedRooms) {
            rooms.remove(room);
        }
        return rooms;
    }

    public List<Room> findAllRoomsByFreeDateRange(RoomFilter filter) {
        List<Room> rooms = new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(room)
                .fetch();

        List<Room> bookedRooms = new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(getByBookedDateRange(filter))
                .fetch();
        for (Room room : bookedRooms) {
            rooms.remove(room);
        }
        return rooms;
    }

    private static Predicate getByCompleteInfo(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getHotelName(), hotel.name::eq)
                .add(filter.getOccupancy(), room.occupancy::eq)
                .add(filter.getPricePerDay(), room.pricePerDay::eq)
                .add(filter.getRoomClass(), room.roomClass::eq)
                .buildAnd();
    }

    private static Predicate getByBookedDateRange(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getCheckOutDate(), order.checkInDate::before)
                .add(filter.getCheckInDate(), order.checkOutDate::after)
                .buildAnd();
    }
}
