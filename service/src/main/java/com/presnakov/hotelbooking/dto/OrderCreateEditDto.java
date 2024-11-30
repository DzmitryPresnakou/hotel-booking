package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.entity.PaymentStatusEnum;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.entity.User;
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
