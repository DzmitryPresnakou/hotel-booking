package com.presnakov.hotelbooking.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class OrderFilter {
    String hotelName;
    String username;
    LocalDate checkInDate;
    LocalDate checkOutDate;
}
