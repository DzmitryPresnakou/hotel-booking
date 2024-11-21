package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.entity.Room;
import com.presnakov.hotelbooking.entity.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class OrderReadDto {
    Integer id;
    User user;
    Room room;
    OrderStatusEnum status;
    PaymentStatusEnum paymentStatus;
    LocalDate checkInDate;
    LocalDate checkOutDate;
}
