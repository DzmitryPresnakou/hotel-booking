package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.database.entity.Room;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.presnakov.hotelbooking.database.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.database.entity.QOrder.order;
import static com.presnakov.hotelbooking.database.entity.QRoom.room;

@RequiredArgsConstructor
public class FilterRoomRepositoryImpl implements FilterRoomRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Room> findAllByFilter(RoomFilter filter, Pageable pageable) {
        JPAQuery<Room> query = new JPAQuery<>(entityManager)
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getByCheckInDate(filter), getByCheckOutDate(filter))
                .join(room.hotel, hotel)
                .where(getByCompleteInfo(filter), order.id.isNull());
        long total = query.fetchCount();
        List<Room> rooms = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(rooms, pageable, total);
    }

    @Override
    public Page<Room> findAllByFreeDateRange(RoomFilter filter, Pageable pageable) {
        JPAQuery<Room> query = new JPAQuery<Room>(entityManager)
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getByCheckInDate(filter), getByCheckOutDate(filter))
                .where(order.id.isNull());
        long total = query.fetchCount();
        List<Room> rooms = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(rooms, pageable, total);
    }

    @Override
    public Page<Room> findAllByHotelName(String hotelName, Pageable pageable) {
        JPAQuery<Room> query = new JPAQuery<Room>(entityManager)
                .select(room)
                .from(room)
                .join(room.hotel, hotel)
                .where(hotel.name.eq(hotelName));
        long total = query.fetchCount();
        List<Room> rooms = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(rooms, pageable, total);
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
