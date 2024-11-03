package com.presnakov.hotelbooking.integration.repository;

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
import org.junit.jupiter.params.provider.Arguments;

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

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel);

        Room actualResult = roomRepository.save(room);

        assertNotNull(actualResult.getId());
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

        List<Room> actualResult = roomRepository.findAll();

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

        List<Room> actualResult = roomRepository.findAllByHotel(hotel);

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(roomIds).contains(room1.getId(), room2.getId(), room3.getId());
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
