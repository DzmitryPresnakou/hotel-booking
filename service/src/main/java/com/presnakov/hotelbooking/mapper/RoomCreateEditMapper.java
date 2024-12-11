package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.repository.HotelRepository;
import com.presnakov.hotelbooking.dto.RoomCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class RoomCreateEditMapper implements Mapper<RoomCreateEditDto, Room> {

    private final HotelRepository hotelRepository;

    @Override
    public Room map(RoomCreateEditDto fromObject, Room toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Room map(RoomCreateEditDto object) {
        Room room = new Room();
        copy(object, room);
        return room;
    }

    private void copy(RoomCreateEditDto object, Room room) {
        room.setOccupancy(object.getOccupancy());
        room.setRoomClass(object.getRoomClass());
        room.setPricePerDay(object.getPricePerDay());
        room.setHotel(getHotel(object.getHotelId()));

        Optional.ofNullable(object.getPhoto())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> room.setPhoto(image.getOriginalFilename()));
    }

    public Hotel getHotel(Integer hotelId) {
        return Optional.ofNullable(hotelId)
                .flatMap(hotelRepository::findById)
                .orElse(null);
    }
}
