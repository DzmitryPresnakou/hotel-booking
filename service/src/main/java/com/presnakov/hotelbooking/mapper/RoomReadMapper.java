package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.dto.HotelReadDto;
import com.presnakov.hotelbooking.dto.RoomReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomReadMapper implements Mapper<Room, RoomReadDto> {

    private final HotelReadMapper hotelReadMapper;

    @Override
    public RoomReadDto map(Room object) {
        HotelReadDto hotel = Optional.ofNullable(object.getHotel())
                .map(hotelReadMapper::map)
                .orElse(null);
        return  RoomReadDto.builder()
                .id(object.getId())
                .occupancy(object.getOccupancy())
                .roomClass(object.getRoomClass())
                .photo(object.getPhoto())
                .pricePerDay(object.getPricePerDay())
                .hotel(hotel)
                .build();
    }
}
