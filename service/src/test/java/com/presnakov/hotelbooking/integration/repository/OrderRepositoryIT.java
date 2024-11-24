package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.database.repository.HotelRepository;
import com.presnakov.hotelbooking.database.repository.OrderRepository;
import com.presnakov.hotelbooking.database.repository.RoomRepository;
import com.presnakov.hotelbooking.database.repository.UserRepository;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class OrderRepositoryIT extends IntegrationTestBase {

    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25));

        Order actualResult = orderRepository.save(order);

        assertNotNull(actualResult.getId());
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        order.setCheckInDate(LocalDate.of(2024, 11, 11));
        order.setCheckOutDate(LocalDate.of(2024, 11, 22));
        order.setStatus(OrderStatusEnum.APPROVED);
        orderRepository.save(order);

        Order updatedOrder = orderRepository.findById(order.getId()).get();
        assertThat(updatedOrder).isEqualTo(order);
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        orderRepository.delete(order);

        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        Optional<Order> actualResult = orderRepository.findById(order.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(order);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(CreateDataUtil.createHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));
        User user1 = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(CreateDataUtil.createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(CreateDataUtil.createUser("Petya", "Petrov", "petya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));
        Order order1 = orderRepository.save(CreateDataUtil.createOrder(user1, room1, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 20), LocalDate.of(2024, 10, 30)));
        Order order2 = orderRepository.save(CreateDataUtil.createOrder(user2, room2, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 25), LocalDate.of(2024, 11, 10)));
        Order order3 = orderRepository.save(CreateDataUtil.createOrder(user3, room3, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 5), LocalDate.of(2024, 11, 16)));

        List<Order> actualResult = orderRepository.findAll();

        List<Integer> orderIds = actualResult.stream()
                .map(Order::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(orderIds).contains(order1.getId(), order2.getId(), order3.getId());
    }

    @Test
    void findOrdersByUserEmail() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        List<Order> actualResult = orderRepository.findOrdersByUsername(user.getUsername());
        List<String> emails = actualResult.stream()
                .map(Order::getUser)
                .toList()
                .stream()
                .map(User::getUsername)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(emails).contains(user.getUsername());
    }

    @Test
    void findOrdersByHotelName() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        List<Order> actualResult = orderRepository.findOrdersByHotelName(hotel.getName());
        List<String> hotels = actualResult.stream()
                .map(Order::getRoom)
                .toList()
                .stream()
                .map(Room::getHotel)
                .toList()
                .stream()
                .map(Hotel::getName)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(hotels).contains(hotel.getName());
    }

    @Test
    void findOrdersByCheckInDate() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(CreateDataUtil.createOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        List<Order> actualResult = orderRepository.findOrdersByCheckInDate(order.getCheckInDate());
        List<LocalDate> orderCheckInDates = actualResult.stream()
                .map(Order::getCheckInDate)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(orderCheckInDates).containsExactly(order.getCheckInDate());
    }

    @Test
    void findOrdersByDateRange() {
        Hotel hotel1 = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(CreateDataUtil.createHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));
        User user1 = userRepository.save(CreateDataUtil.createUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(CreateDataUtil.createUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(CreateDataUtil.createUser("Petya", "Petrov", "petya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));
        Order order1 = orderRepository.save(CreateDataUtil.createOrder(user1, room1, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 20), LocalDate.of(2024, 11, 6)));
        Order order2 = orderRepository.save(CreateDataUtil.createOrder(user2, room2, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 20)));
        Order order3 = orderRepository.save(CreateDataUtil.createOrder(user3, room3, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 16)));
        LocalDate startRange = LocalDate.of(2024, 11, 5);
        LocalDate endRange = LocalDate.of(2024, 12, 5);

        List<Order> actualResult = orderRepository.findOrdersByDateRange(startRange, endRange);

        List<Integer> orderIds = actualResult.stream()
                .map(Order::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(orderIds).contains(order1.getId(), order2.getId(), order3.getId());
    }
}
