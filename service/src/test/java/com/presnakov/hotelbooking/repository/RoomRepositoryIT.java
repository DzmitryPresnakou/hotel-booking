package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.*;
import com.presnakov.hotelbooking.integration.EntityITBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoomRepositoryIT extends EntityITBase {

    protected static OrderRepository orderRepository;
    protected static RoomRepository roomRepository;
    protected static HotelRepository hotelRepository;
    protected static UserRepository userRepository;

    @BeforeEach
    void createRepository() {
        roomRepository = new RoomRepository(session);
        hotelRepository = new HotelRepository(session);
    }

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel);

        Room actualResult = roomRepository.save(room);

        assertNotNull(actualResult.getId());
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        roomRepository.delete(room);

        assertThat(roomRepository.findById(room.getId())).isEmpty();
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        room.setRoomClass(RoomClassEnum.COMFORT);
        room.setPricePerDay(40);
        room.setOccupancy(3);
        room.setPhoto("roomphoto111.jpg");
        roomRepository.update(room);

        Room updatedRoom = roomRepository.findById(room.getId()).get();
        assertThat(updatedRoom).isEqualTo(room);
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        Optional<Room> actualResult = roomRepository.findById(room.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(room);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(getHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(getRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(getRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));

        List<Room> actualResult = roomRepository.findAll();

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(roomIds).contains(room1.getId(), room2.getId(), room3.getId());
    }

    @Test
    void findAllRoomsByFilter() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        RoomFilter filter = RoomFilter.builder()
                .hotelName(hotel.getName())
                .occupancy(room.getOccupancy())
                .pricePerDay(room.getPricePerDay())
                .roomClass(room.getRoomClass())
                .build();

        List<Room> actualResult = roomRepository.findAllRoomsByFilter(filter);

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(roomIds).contains(room.getId());
    }

    @ParameterizedTest
    @MethodSource("getDateRanges")
    void findAllRoomsByFreeDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        orderRepository = new OrderRepository(session);
        userRepository = new UserRepository(session);
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 22)));
        RoomFilter filter = RoomFilter.builder()
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();

        List<Room> actualResult = roomRepository.findAllRoomsByFreeDateRange(filter);

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(roomIds).contains(room.getId());
    }

    private static Hotel getHotel(String name, String photo) {
        return Hotel.builder()
                .photo(photo)
                .name(name)
                .build();
    }

    private static Room getRoom(RoomClassEnum roomClass,
                                Integer pricePerDay,
                                String photo,
                                Integer occupancy,
                                Hotel hotel) {
        return Room.builder()
                .roomClass(roomClass)
                .pricePerDay(pricePerDay)
                .photo(photo)
                .occupancy(occupancy)
                .hotel(hotel)
                .build();
    }

    private static User getUser(String firstName,
                                String lastName,
                                String email,
                                String phone,
                                String photo,
                                LocalDate birthDate,
                                Integer money,
                                String password,
                                RoleEnum roleEnum) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .photo(photo)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(roleEnum)
                .build();
    }

    private static Order getOrder(User user,
                                  Room room,
                                  OrderStatusEnum orderStatus,
                                  PaymentStatusEnum paymentStatus,
                                  LocalDate checkInDate,
                                  LocalDate checkOutDate) {
        return Order.builder()
                .user(user)
                .room(room)
                .status(orderStatus)
                .paymentStatus(paymentStatus)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();
    }

    static Stream<Arguments> getDateRanges() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 10, 25), LocalDate.of(2024, 11, 5)),
                Arguments.of(LocalDate.of(2024, 10, 28), LocalDate.of(2024, 11, 10)),
                Arguments.of(LocalDate.of(2024, 11, 22), LocalDate.of(2024, 11, 28)),
                Arguments.of(LocalDate.of(2024, 11, 25), LocalDate.of(2024, 11, 30))
        );
    }
}
