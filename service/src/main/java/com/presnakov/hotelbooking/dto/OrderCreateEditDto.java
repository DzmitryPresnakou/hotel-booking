package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.entity.Room;
import com.presnakov.hotelbooking.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@Builder
@FieldNameConstants
public class OrderCreateEditDto {
    @NotNull
    User user;

    @NotNull
    Room room;

    @NotNull
    OrderStatusEnum status;

    @NotNull
    PaymentStatusEnum paymentStatus;

    @NotNull
    LocalDate checkInDate;

    @NotNull
    LocalDate checkOutDate;
}
