package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.entity.RoomClassEnum;
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
    Hotel hotel;
}
