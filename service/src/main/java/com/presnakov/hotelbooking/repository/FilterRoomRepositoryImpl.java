package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.Room;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.entity.QOrder.order;
import static com.presnakov.hotelbooking.entity.QRoom.room;

@RequiredArgsConstructor
public class FilterRoomRepositoryImpl implements FilterRoomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Room> findAllByFilter(RoomFilter filter) {
        return new JPAQuery<Room>(entityManager)
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getByCheckInDate(filter), getByCheckOutDate(filter))
                .join(room.hotel, hotel)
                .where(getByCompleteInfo(filter), order.id.isNull())
                .fetch();
    }

    @Override
    public List<Room> findAllByFreeDateRange(RoomFilter filter) {
        return new JPAQuery<Room>(entityManager)
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getByCheckInDate(filter), getByCheckOutDate(filter))
                .where(order.id.isNull())
                .fetch();
    }

    @Override
    public List<Room> findAllByHotelName(String hotelName) {
        return new JPAQuery<Room>(entityManager)
                .select(room)
                .from(room)
                .join(room.hotel, hotel)
                .where(hotel.name.eq(hotelName))
                .fetch();
    }

    private static Predicate getByCheckInDate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getCheckInDate(), order.checkInDate::after)
                .add(filter.getCheckOutDate(), order.checkInDate::before)
                .buildAnd();
    }

    private static Predicate getByCheckOutDate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getCheckInDate(), order.checkOutDate::after)
                .add(filter.getCheckOutDate(), order.checkOutDate::before)
                .buildOr();
    }

    private static Predicate getByCompleteInfo(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getHotelName(), hotel.name::eq)
                .add(filter.getOccupancy(), room.occupancy::eq)
                .add(filter.getPricePerDay(), room.pricePerDay::eq)
                .add(filter.getRoomClass(), room.roomClass::eq)
                .buildAnd();
    }
}
