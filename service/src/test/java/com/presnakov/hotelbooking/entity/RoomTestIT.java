package com.presnakov.hotelbooking.entity;

import com.presnakov.hotelbooking.dao.QPredicate;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.integration.EntityTestBase;
import com.presnakov.hotelbooking.util.TestDataImporter;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.query.criteria.JpaJoin;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.presnakov.hotelbooking.entity.QHotel.hotel;
import static com.presnakov.hotelbooking.entity.QOrder.order;
import static com.presnakov.hotelbooking.entity.QRoom.room;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RoomTestIT extends EntityTestBase {

    @Test
    void findAllByHotelNameClassOccupancyPriceHql() {
        TestDataImporter.importData(session);
        String hotelName = "Plaza";
        Integer occupancy = 2;
        Integer pricePerDay = 29;
        RoomClassEnum roomClass = RoomClassEnum.ECONOMY;

        List<Room> results = session.createQuery("select r from Hotel h " +
                                                 "join h.rooms r " +
                                                 "where h.name = :hotelName " +
                                                 "and r.roomClass = :comfortClass " +
                                                 "and r.occupancy = :occupancy " +
                                                 "and r.pricePerDay = :pricePerDay", Room.class)
                .setParameter("hotelName", hotelName)
                .setParameter("comfortClass", roomClass)
                .setParameter("occupancy", occupancy)
                .setParameter("pricePerDay", pricePerDay)
                .list();

        assertThat(results).hasSize(1);
    }

    @Test
    void findAllByHotelNameClassOccupancyPriceFreeDateHql() {
        TestDataImporter.importData(session);
        String hotelName = "Plaza";
        Integer occupancy = 2;
        Integer pricePerDay = 29;
        RoomClassEnum roomClass = RoomClassEnum.ECONOMY;
        LocalDate checkInDate = LocalDate.of(2024, 10, 10);
        LocalDate checkOutDate = LocalDate.of(2024, 10, 15);

        List<Room> results = session.createQuery("select r from Order o " +
                                                 "join o.room r " +
                                                 "join r.hotel h " +
                                                 "where h.name = :hotelName " +
                                                 "and r.roomClass = :comfortClass " +
                                                 "and r.occupancy = :occupancy " +
                                                 "and r.pricePerDay = :pricePerDay " +
                                                 "and not LEAST(o.checkOutDate, :checkOutDate) > GREATEST(o.checkInDate, :checkInDate)", Room.class)
                .setParameter("hotelName", hotelName)
                .setParameter("comfortClass", roomClass)
                .setParameter("occupancy", occupancy)
                .setParameter("pricePerDay", pricePerDay)
                .setParameter("checkInDate", checkInDate)
                .setParameter("checkOutDate", checkOutDate)
                .list();

        assertThat(results).hasSize(1);
    }

    @Test
    void findAllByHotelNameClassOccupancyPriceCriteria() {
        TestDataImporter.importData(session);
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Room.class);
        var hotel = criteria.from(Hotel.class);
        JpaJoin<Hotel, Room> rooms = hotel.join("rooms");
        String hotelName = "Plaza";
        Integer occupancy = 2;
        Integer pricePerDay = 29;
        RoomClassEnum roomClass = RoomClassEnum.ECONOMY;

        criteria.select(rooms).where(
                cb.equal(hotel.get("name"), hotelName),
                cb.equal(rooms.get("occupancy"), occupancy),
                cb.equal(rooms.get("pricePerDay"), pricePerDay),
                cb.equal(rooms.get("roomClass"), roomClass)
        );
        List<Room> results = session.createQuery(criteria)
                .list();
        Optional<Room> room = results.stream()
                .filter(r -> r.getHotel().getName().equals(hotelName))
                .findAny();

        assertAll(
                () -> assertThat(results).hasSize(1),
                () -> assertThat(room.isPresent()).isTrue(),
                () -> assertThat(room.get().getHotel().getName().equals(hotelName)),
                () -> assertThat(room.get().getOccupancy().equals(occupancy)),
                () -> assertThat(room.get().getPricePerDay().equals(pricePerDay)),
                () -> assertThat(room.get().getRoomClass().equals(roomClass))
        );
    }

    @Test
    void findAllByHotelNameClassOccupancyPriceQueryDsl() {
        TestDataImporter.importData(session);
        String hotelName = "Plaza";
        Integer occupancy = 2;
        Integer pricePerDay = 29;
        RoomClassEnum roomClass = RoomClassEnum.ECONOMY;

        List<Room> results = new JPAQuery<Room>(session)
                .select(room)
                .from(hotel)
                .join(hotel.rooms, room)
                .where(hotel.name.eq(hotelName),
                        room.occupancy.eq(occupancy),
                        room.pricePerDay.eq(pricePerDay),
                        room.roomClass.eq(roomClass))
                .fetch();

        assertThat(results).hasSize(1);
    }

    @Test
    void findAllByHotelNameClassOccupancyPriceFreeDateQueryDsl() {
        TestDataImporter.importData(session);
        String hotelName = "Plaza";
        Integer occupancy = 2;
        Integer pricePerDay = 29;
        RoomClassEnum roomClass = RoomClassEnum.ECONOMY;
        LocalDate checkInDate = LocalDate.of(2024, 10, 10);
        LocalDate checkOutDate = LocalDate.of(2024, 10, 15);

        List<Room> results = new JPAQuery<Room>(session)
                .select(room)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(hotel.name.eq(hotelName),
                        room.occupancy.eq(occupancy),
                        room.pricePerDay.eq(pricePerDay),
                        room.roomClass.eq(roomClass),
                        order.checkInDate.after(checkOutDate)
                                .or(order.checkInDate.eq(checkOutDate))
                                .or(order.checkOutDate.before(checkInDate))
                                .or(order.checkOutDate.eq(checkInDate)))
                .fetch();

        assertThat(results).hasSize(1);
    }

    @Test
    void findAllByHotelNameClassOccupancyPriceFreeDateFilter() {
        TestDataImporter.importData(session);
        RoomFilter filter = RoomFilter.builder()
                .hotelName("Plaza")
                .occupancy(2)
                .pricePerDay(29)
                .roomClass(RoomClassEnum.ECONOMY)
                .checkInDate(LocalDate.of(2024, 10, 10))
                .checkOutDate(LocalDate.of(2024, 10, 15))
                .build();
        Predicate predicate1 = QPredicate.builder()
                .add(filter.getHotelName(), hotel.name::eq)
                .add(filter.getOccupancy(), room.occupancy::eq)
                .add(filter.getPricePerDay(), room.pricePerDay::eq)
                .add(filter.getRoomClass(), room.roomClass::eq)
                .buildAnd();
        Predicate predicate2 = QPredicate.builder()
                .add(filter.getCheckOutDate(), order.checkInDate::after)
                .add(filter.getCheckOutDate(), order.checkInDate::eq)
                .add(filter.getCheckInDate(), order.checkOutDate::before)
                .add(filter.getCheckInDate(), order.checkOutDate::eq)
                .buildOr();

        List<Room> results = new JPAQuery<Room>(session)
                .select(room)
                .from(order)
                .join(order.room, room)
                .join(room.hotel, hotel)
                .where(predicate1, predicate2)
                .fetch();

        assertThat(results).hasSize(1);
    }

    @Test
    void createRoom() {
        Room room = getRoom();
        session.persist(room);
        session.flush();
        session.clear();

        Room actualResult = session.getReference(Room.class, room.getId());

        assertThat(actualResult.getId()).isEqualTo(room.getId());
    }

    @Test
    void updateRoom() {
        Room room = getRoom();
        session.persist(room);
        room.setRoomClass(RoomClassEnum.COMFORT);
        room.setOccupancy(4);
        room.setPricePerDay(69);
        room.setPhoto("roomphoto007.jpg");
        session.merge(room);
        session.flush();
        session.clear();

        Room actualResult = session.getReference(Room.class, room.getId());

        assertAll(
                () -> assertThat(actualResult.getRoomClass().equals(RoomClassEnum.COMFORT)),
                () -> assertThat(actualResult.getOccupancy()).isEqualTo(4),
                () -> assertThat(actualResult.getPricePerDay().equals(69)),
                () -> assertThat(actualResult.getPhoto()).isEqualTo("roomphoto007.jpg")
        );
    }

    @Test
    void getRoomById() {
        Room room = getRoom();
        session.persist(room);
        session.flush();
        session.clear();

        Room actualResult = session.getReference(Room.class, room.getId());

        assertThat(actualResult.getId()).isEqualTo(room.getId());
    }

    @Test
    void deleteRoom() {
        Room room = getRoom();
        session.persist(room);
        Room actualResult = session.getReference(Room.class, room.getId());
        session.remove(actualResult);
        session.flush();
        session.clear();

        Optional<Room> deletedRoom = Optional.ofNullable(session.find(Room.class, room.getId()));

        assertThat(deletedRoom).isEmpty();
    }

    private static Room getRoom() {
        return Room.builder()
                .roomClass(RoomClassEnum.ECONOMY)
                .pricePerDay(49)
                .photo("photo0005.jpg")
                .occupancy(3)
                .build();
    }
}
