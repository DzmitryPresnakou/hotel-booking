package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.RoomClassEnum;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class RoomFilter {
    String hotelName;
    Integer occupancy;
    Integer pricePerDay;
    RoomClassEnum roomClass;
    LocalDate checkInDate;
    LocalDate checkOutDate;
}
