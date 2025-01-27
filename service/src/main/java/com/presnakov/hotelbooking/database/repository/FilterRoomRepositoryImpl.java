package com.presnakov.hotelbooking.database.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.querydsl.QPredicate;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.presnakov.hotelbooking.database.entity.OrderStatusEnum.APPROVED;
import static com.presnakov.hotelbooking.database.entity.OrderStatusEnum.CLOSED;
import static com.presnakov.hotelbooking.database.entity.OrderStatusEnum.REJECTED;
import static com.presnakov.hotelbooking.database.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.database.entity.QOrder.order;
import static com.presnakov.hotelbooking.database.entity.QRoom.room;

@RequiredArgsConstructor
public class FilterRoomRepositoryImpl implements FilterRoomRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Room> findAll(RoomFilter filter, Pageable pageable) {
        BooleanExpression checkInCondition = getByCheckInDate(filter);
        BooleanExpression checkOutCondition = getByCheckOutDate(filter);
        BooleanExpression dateCondition = checkInCondition.or(checkOutCondition);

        JPAQuery<Room> query = new JPAQuery<>(entityManager)
                .select(room)
                .from(order)
                .rightJoin(order.room, room)
                .where(getPredicate(filter), room.isActive.isTrue(),
                        order.isNull().or((filter.getCheckInDate() != null) ?
                                dateCondition : order.status.in(CLOSED, APPROVED, REJECTED).and(order.isActive.isTrue())));
        long total = query.fetch().size();
        List<Room> rooms = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(rooms, pageable, total);
    }

    private static BooleanExpression getByCheckInDate(RoomFilter filter) {
        BooleanExpression checkInAfter = (filter.getCheckInDate() != null) ?
                order.checkInDate.after(filter.getCheckInDate()) : Expressions.asBoolean(false).isTrue();
        BooleanExpression checkInAfterEnd = (filter.getCheckOutDate() != null) ?
                order.checkInDate.after(filter.getCheckOutDate()) : Expressions.asBoolean(false).isTrue();
        return checkInAfter.and(checkInAfterEnd);
    }

    private static BooleanExpression getByCheckOutDate(RoomFilter filter) {
        BooleanExpression checkOutBefore = (filter.getCheckInDate() != null) ?
                order.checkOutDate.before(filter.getCheckInDate()) : Expressions.asBoolean(false).isTrue();
        BooleanExpression checkOutBeforeEnd = (filter.getCheckOutDate() != null) ?
                order.checkOutDate.before(filter.getCheckOutDate()) : Expressions.asBoolean(false).isTrue();
        return checkOutBefore.and(checkOutBeforeEnd);
    }

    private static Predicate getPredicate(RoomFilter filter) {
        return QPredicate.builder()
                .add((filter.getHotelName() != null && filter.getHotelName().isEmpty()) ?
                        null : filter.getHotelName(), hotel.name::eq)
                .add(filter.getOccupancy(), room.occupancy::goe)
                .add(filter.getPricePerDay(), room.pricePerDay::loe)
                .add((filter.getRoomClass() != null && filter.getRoomClass().name().isEmpty()) ?
                        null : filter.getRoomClass(), room.roomClass::eq)
                .buildAnd();
    }

    public void softDelete(Room room) {
        room.setIsActive(false);
    }

    @Override
    public Optional<Room> findByHotelId(Integer hotelId) {
        JPAQuery<Room> query = new JPAQuery<Room>(entityManager)
                .select(room)
                .from(room)
                .where(room.id.like(String.valueOf(hotelId)));
        return Optional.empty();
    }


    public Page<Hotel> findAll(Pageable pageable) {
        JPAQuery<Hotel> query = new JPAQuery<Hotel>(entityManager)
                .select(hotel)
                .from(hotel)
                .where(hotel.isActive.isTrue());
        long total = query.fetch().size();
        List<Hotel> hotels = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(hotels, pageable, total);
    }

}
