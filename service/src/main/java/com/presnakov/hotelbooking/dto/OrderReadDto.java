package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.entity.PaymentStatusEnum;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class OrderReadDto {
    Integer id;
    UserReadDto user;
    RoomReadDto room;
    OrderStatusEnum status;
    PaymentStatusEnum paymentStatus;
    LocalDate checkInDate;
    LocalDate checkOutDate;
}
