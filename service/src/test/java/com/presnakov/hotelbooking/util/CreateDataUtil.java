package com.presnakov.hotelbooking.util;

import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import com.presnakov.hotelbooking.database.entity.User;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

public class CreateDataUtil {

    public static User createUser(String firstname,
                                  String lastname,
                                  String username,
                                  String phone,
                                  String photo,
                                  LocalDate birthDate,
                                  Integer money,
                                  String password,
                                  RoleEnum role) {
        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .phone(phone)
                .photo(photo)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(role)
                .build();
    }

    public static UserReadDto getUserReadDto(Integer id,
                                             String firstname,
                                             String lastname,
                                             String username,
                                             String phone,
                                             LocalDate birthDate,
                                             Integer money,
                                             RoleEnum role) {
        return UserReadDto.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .phone(phone)
                .birthDate(birthDate)
                .money(money)
                .role(role)
                .build();
    }

    public static UserCreateEditDto getUserCreateEditDto(String firstname,
                                                         String lastname,
                                                         String username,
                                                         String phone,
                                                         LocalDate birthDate,
                                                         Integer money,
                                                         String password,
                                                         RoleEnum role,
                                                         MockMultipartFile photo) {
        return UserCreateEditDto.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .phone(phone)
                .birthDate(birthDate)
                .money(money)
                .rawPassword(password)
                .role(role)
                .photo(photo)
                .build();
    }

    public static Hotel createHotel(String name, String photo) {
        return Hotel.builder()
                .photo(photo)
                .name(name)
                .build();
    }

    public static Room createRoom(RoomClassEnum roomClass,
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

    public static Order createOrder(User user,
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
