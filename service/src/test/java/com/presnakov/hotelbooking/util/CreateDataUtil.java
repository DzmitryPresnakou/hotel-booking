package com.presnakov.hotelbooking.util;

import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.Order;
import com.presnakov.hotelbooking.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.entity.RoleEnum;
import com.presnakov.hotelbooking.entity.Room;
import com.presnakov.hotelbooking.entity.RoomClassEnum;
import com.presnakov.hotelbooking.entity.User;

import java.time.LocalDate;

public class CreateDataUtil {

    public static User createUser(String firstName,
                                  String lastName,
                                  String email,
                                  String phone,
                                  String photo,
                                  LocalDate birthDate,
                                  Integer money,
                                  String password,
                                  RoleEnum role) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .photo(photo)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(role)
                .build();
    }

    public static UserReadDto getUserReadDto(Integer id,
                                             String firstName,
                                             String lastName,
                                             String email,
                                             String phone,
                                             LocalDate birthDate,
                                             Integer money,
                                             String password,
                                             RoleEnum role) {
        return UserReadDto.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(role)
                .build();
    }

    public static UserCreateEditDto getUserCreateEditDto(String firstName,
                                                         String lastName,
                                                         String email,
                                                         String phone,
                                                         LocalDate birthDate,
                                                         Integer money,
                                                         String password,
                                                         RoleEnum role) {
        return UserCreateEditDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .birthDate(birthDate)
                .money(money)
                .password(password)
                .role(role)
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
