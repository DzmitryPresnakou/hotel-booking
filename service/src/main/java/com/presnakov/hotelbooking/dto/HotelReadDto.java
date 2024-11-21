package com.presnakov.hotelbooking.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HotelReadDto {
    Integer id;
    String name;
    String photo;
}
