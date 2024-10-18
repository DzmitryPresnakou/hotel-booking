package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.Order;
import com.presnakov.hotelbooking.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.Room;
import com.presnakov.hotelbooking.entity.RoomClassEnum;
import com.presnakov.hotelbooking.entity.User;
import com.presnakov.hotelbooking.integration.EntityITBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderRepositoryIT extends EntityITBase {

    protected static OrderRepository orderRepository;
    protected static RoomRepository roomRepository;
    protected static HotelRepository hotelRepository;
    protected static UserRepository userRepository;

    @BeforeEach
    void createRepository() {
        orderRepository = new OrderRepository(session);
        roomRepository = new RoomRepository(session);
        hotelRepository = new HotelRepository(session);
        userRepository = new UserRepository(session);
    }

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25));

        Order actualResult = orderRepository.save(order);

        assertNotNull(actualResult.getId());
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        orderRepository.delete(order);

        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        order.setCheckInDate(LocalDate.of(2024, 11, 11));
        order.setCheckOutDate(LocalDate.of(2024, 11, 22));
        order.setStatus(OrderStatusEnum.APPROVED);
        orderRepository.update(order);

        Order updatedOrder = orderRepository.findById(order.getId()).get();
        assertThat(updatedOrder).isEqualTo(order);
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        Optional<Order> actualResult = orderRepository.findById(order.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(order);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(getHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(getRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(getRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));
        User user1 = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(getUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(getUser("Petya", "Petrov", "petya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));
        Order order1 = orderRepository.save(getOrder(user1, room1, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 20), LocalDate.of(2024, 10, 30)));
        Order order2 = orderRepository.save(getOrder(user2, room2, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 25), LocalDate.of(2024, 11, 10)));
        Order order3 = orderRepository.save(getOrder(user3, room3, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
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
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 25)));

        List<Order> actualResult = orderRepository.findOrdersByUserEmail(user.getEmail());
        List<String> emails = actualResult.stream()
                .map(Order::getUser)
                .toList()
                .stream()
                .map(User::getEmail)
                .toList();
        assertThat(actualResult).hasSize(1);
        assertThat(emails).contains(user.getEmail());
    }

    @Test
    void findOrdersByHotelName() {
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
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
        Hotel hotel = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));
        User user = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmai.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        Order order = orderRepository.save(getOrder(user, room, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
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
        Hotel hotel1 = hotelRepository.save(getHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(getHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(getRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(getRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(getRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));
        User user1 = userRepository.save(getUser("Vasya", "Vasilyev", "vasya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1995, 2, 5),
                2500, "12345", RoleEnum.USER));
        User user2 = userRepository.save(getUser("Vanya", "Ivanov", "vanya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(1997, 6, 11),
                3000, "56987", RoleEnum.USER));
        User user3 = userRepository.save(getUser("Petya", "Petrov", "petya@gmail.com",
                "+375291478523", "userphoto001.jpg", LocalDate.of(2000, 11, 9),
                5000, "4563258", RoleEnum.USER));
        Order order1 = orderRepository.save(getOrder(user1, room1, OrderStatusEnum.OPEN, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 10, 20), LocalDate.of(2024, 11, 6)));
        Order order2 = orderRepository.save(getOrder(user2, room2, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
                LocalDate.of(2024, 11, 10), LocalDate.of(2024, 11, 20)));
        Order order3 = orderRepository.save(getOrder(user3, room3, OrderStatusEnum.APPROVED, PaymentStatusEnum.APPROVED,
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
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4)
        );
    }
}