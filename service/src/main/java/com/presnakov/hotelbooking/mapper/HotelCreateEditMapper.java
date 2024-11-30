package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.dto.HotelCreateEditDto;
import com.presnakov.hotelbooking.database.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class HotelCreateEditMapper implements Mapper<HotelCreateEditDto, Hotel> {

    @Override
    public Hotel map(HotelCreateEditDto fromObject, Hotel toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Hotel map(HotelCreateEditDto object) {
        Hotel hotel = new Hotel();
        copy(object, hotel);
        return hotel;
    }

    private void copy(HotelCreateEditDto object, Hotel hotel) {
        hotel.setName(object.getName());

        Optional.ofNullable(object.getPhoto())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> hotel.setPhoto(image.getOriginalFilename()));
    }
}
