package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.dto.HotelReadDto;
import com.presnakov.hotelbooking.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelReadMapper implements Mapper<Hotel, HotelReadDto> {
    @Override
    public HotelReadDto map(Hotel object) {
        return HotelReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .photo(object.getPhoto())
                .build();
    }
}
