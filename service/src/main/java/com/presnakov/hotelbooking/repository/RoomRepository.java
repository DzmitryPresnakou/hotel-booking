package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.Room;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.entity.QOrder.order;
import static com.presnakov.hotelbooking.entity.QRoom.room;

@Repository
public class RoomRepository extends RepositoryBase<Integer, Room> {

    public RoomRepository(EntityManager entityManager) {
        super(Room.class, entityManager);
    }

    @Transactional
    public List<Room> findAllRoomsByFilter(RoomFilter filter) {
        return new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getByCheckInDate(filter), getByCheckOutDate(filter))
                .join(room.hotel, hotel)
                .where(getByCompleteInfo(filter), order.id.isNull())
                .fetch();
    }

    @Transactional
    public List<Room> findAllRoomsByFreeDateRange(RoomFilter filter) {
        return new JPAQuery<Room>(getEntityManager())
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getByCheckInDate(filter), getByCheckOutDate(filter))
                .where(order.id.isNull())
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
