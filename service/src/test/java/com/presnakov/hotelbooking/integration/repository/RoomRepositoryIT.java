package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.Order;
import com.presnakov.hotelbooking.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.Room;
import com.presnakov.hotelbooking.entity.RoomClassEnum;
import com.presnakov.hotelbooking.entity.User;
import com.presnakov.hotelbooking.integration.annotation.IT;
import com.presnakov.hotelbooking.repository.HotelRepository;
import com.presnakov.hotelbooking.repository.OrderRepository;
import com.presnakov.hotelbooking.repository.RoomRepository;
import com.presnakov.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

@IT
@RequiredArgsConstructor
class RoomRepositoryIT {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel);

        Room actualResult = roomRepository.save(room);

        assertNotNull(actualResult.getId());
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        room.setRoomClass(RoomClassEnum.COMFORT);
        room.setPricePerDay(40);
        room.setOccupancy(3);
        room.setPhoto("roomphoto111.jpg");
        roomRepository.save(room);

        Room updatedRoom = roomRepository.findById(room.getId()).get();
        assertThat(updatedRoom).isEqualTo(room);
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        roomRepository.delete(room);

        assertThat(roomRepository.findById(room.getId())).isEmpty();
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        Optional<Room> actualResult = roomRepository.findById(room.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(room);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(createHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(createRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(createRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));

        List<Room> actualResult = (List<Room>) roomRepository.findAll();

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(roomIds).contains(room1.getId(), room2.getId(), room3.getId());
    }

    @Test
    void findAllByHotel() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room1 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        Room room2 = roomRepository.save(createRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel));
        Room room3 = roomRepository.save(createRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel));

        List<Room> actualResult = roomRepository.findAllByHotelName(hotel.getName());

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(roomIds).contains(room1.getId(), room2.getId(), room3.getId());
    }

    @ParameterizedTest
    @MethodSource("getDateRanges")
    void findAllByFilter(LocalDate checkInDate, LocalDate checkOutDate) {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room1 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        Room room2 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        Room room3 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        Room room4 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(createOrder(user, room1, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 22)));
        RoomFilter filter = RoomFilter.builder()
                .hotelName(hotel.getName())
                .occupancy(room1.getOccupancy())
                .pricePerDay(room1.getPricePerDay())
                .roomClass(room1.getRoomClass())
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();

        List<Room> actualResult = roomRepository.findAllByFilter(filter);

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(4);
        assertThat(roomIds).contains(room1.getId(), room2.getId(), room3.getId(), room4.getId());
    }

    @ParameterizedTest
    @MethodSource("getDateRanges")
    void findAllRoomsByFreeDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 22)));
        RoomFilter filter = RoomFilter.builder()
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();

        List<Room> actualResult = roomRepository.findAllByFreeDateRange(filter);

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(roomIds).contains(room.getId());
    }

    private static Hotel createHotel(String name, String photo) {
        return Hotel.builder()
                .photo(photo)
                .name(name)
                .build();
    }

    private static Room createRoom(RoomClassEnum roomClass,
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

    private static User createUser(String firstName,
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

    private static Order createOrder(User user,
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
