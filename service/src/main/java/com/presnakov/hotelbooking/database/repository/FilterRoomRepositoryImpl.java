package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.presnakov.hotelbooking.database.entity.OrderStatusEnum.APPROVED;
import static com.presnakov.hotelbooking.database.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.database.entity.QOrder.order;
import static com.presnakov.hotelbooking.database.entity.QRoom.room;

@RequiredArgsConstructor
public class FilterRoomRepositoryImpl implements FilterRoomRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Room> findAll(RoomFilter filter, Pageable pageable) {
        JPAQuery<Room> query = new JPAQuery<>(entityManager)
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .on(getPredicateByCheckInDate(filter), getPredicateByCheckOutDate(filter))
                .where(getPredicate(filter), order.id.isNull().or(order.status.ne(APPROVED)))
                .join(room.hotel, hotel);
        long total = query.fetch().size();
        List<Room> rooms = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(rooms, pageable, total);
    }

    private static Predicate getPredicateByCheckInDate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getCheckInDate(), order.checkInDate::after)
                .add(filter.getCheckOutDate(), order.checkInDate::before)
                .buildAnd();
    }

    private static Predicate getPredicateByCheckOutDate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getCheckInDate(), order.checkOutDate::after)
                .add(filter.getCheckOutDate(), order.checkOutDate::before)
                .buildOr();
    }

    private static Predicate getPredicate(RoomFilter filter) {
        return QPredicate.builder()
                .add(filter.getHotelName(), hotel.name::eq)
                .add(filter.getOccupancy(), room.occupancy::eq)
                .add(filter.getPricePerDay(), room.pricePerDay::eq)
                .add(filter.getRoomClass(), room.roomClass::eq)
                .buildAnd();
    }
}
