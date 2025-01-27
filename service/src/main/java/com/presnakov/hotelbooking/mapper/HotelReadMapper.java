package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.dto.HotelReadDto;
import com.presnakov.hotelbooking.database.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelReadMapper implements Mapper<Hotel, HotelReadDto> {
    @Override
    public HotelReadDto map(Hotel hotel) {
        return HotelReadDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .photo(hotel.getPhoto())
                .isActive(hotel.getIsActive())
                .build();
    }
}
