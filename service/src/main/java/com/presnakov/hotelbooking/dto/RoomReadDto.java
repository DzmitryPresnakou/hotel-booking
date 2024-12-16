package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomReadDto {
    Integer id;
    Integer occupancy;
    RoomClassEnum roomClass;
    String photo;
    Integer pricePerDay;
    HotelReadDto hotel;
}
