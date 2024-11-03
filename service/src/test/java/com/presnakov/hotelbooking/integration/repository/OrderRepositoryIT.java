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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@RequiredArgsConstructor
class OrderRepositoryIT {

    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25));

        Order actualResult = orderRepository.save(order);

        assertNotNull(actualResult.getId());
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        orderRepository.delete(order);

        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        Optional<Order> actualResult = orderRepository.findById(order.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(order);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(createHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(createRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(createRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));
        User user1 = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(createUser("Petya", "Petrov", "petya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));
        Order order1 = orderRepository.save(createOrder(user1, room1, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 20), LocalDate.of(2024, 10, 30)));
        Order order2 = orderRepository.save(createOrder(user2, room2, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 25), LocalDate.of(2024, 11, 10)));
        Order order3 = orderRepository.save(createOrder(user3, room3, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 5), LocalDate.of(2024, 11, 16)));

        List<Order> actualResult = orderRepository.findAll();

        List<Integer> orderIds = actualResult.stream()
                .map(Order::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(orderIds).contains(order1.getId(), order2.getId(), order3.getId());
    }

    @Test
    void findOrdersByCheckInDate() {
        Hotel hotel = hotelRepository.save(createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        List<Order> actualResult = orderRepository.findOrdersByCheckInDate(order.getCheckInDate());
        List<LocalDate> orderCheckInDates = actualResult.stream()
                .map(Order::getCheckInDate)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(orderCheckInDates).containsExactly(order.getCheckInDate());
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
}