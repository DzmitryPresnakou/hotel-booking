package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.entity.PaymentStatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@Builder
@FieldNameConstants
public class OrderCreateEditDto {
    @NotNull
    Integer userId;

    @NotNull
    Integer roomId;

    @NotNull
    OrderStatusEnum status;

    @NotNull
    PaymentStatusEnum paymentStatus;

    @NotNull(message = "Check-in is required")
    @FutureOrPresent(message = "Check-in must be in the present or future")
    LocalDate checkInDate;

    @NotNull(message = "Check-Out is required")
    @Future(message = "Check-out must be in the future")
    LocalDate checkOutDate;
}
