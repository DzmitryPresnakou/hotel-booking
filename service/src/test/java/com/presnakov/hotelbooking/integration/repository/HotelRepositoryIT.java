package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.integration.annotation.IT;
import com.presnakov.hotelbooking.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
class HotelRepositoryIT {

    private final HotelRepository hotelRepository;

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(createHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> maybeHotel = hotelRepository.findById(hotel.getId());

        assertTrue(maybeHotel.isPresent());
        maybeHotel.ifPresent(hotelRepository::delete);
        assertTrue(hotelRepository.findById(hotel.getId()).isEmpty());
    }

    @Test
    void save() {
        Hotel hotel = createHotel("Bobruisk", "hotelphoto001.jpg");

        Hotel actualResult = hotelRepository.save(hotel);

        assertNotNull(actualResult.getId());
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(createHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> actualResult = hotelRepository.findById(hotel.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(hotel);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(createHotel("First World Hotel & Plaza", "photo1.jpg"));
        Hotel hotel2 = hotelRepository.save(createHotel("Flamingo Las Vegas", "photo2.jpg"));
        Hotel hotel3 = hotelRepository.save(createHotel("Atlantis Paradise Island", "photo3.jpg"));
        Hotel hotel4 = hotelRepository.save(createHotel("Hilton Hawaiian Village", "photo4.jpg"));
        Hotel hotel5 = hotelRepository.save(createHotel("Disneys Port Orleans Resort", "photo5.jpg"));

        List<Hotel> actualResult = (List<Hotel>) hotelRepository.findAll();

        List<Integer> hotelIds = actualResult.stream()
                .map(Hotel::getId)
                .toList();
        assertThat(actualResult).hasSize(5);
        assertThat(hotelIds).contains(hotel1.getId(), hotel2.getId(), hotel3.getId(), hotel4.getId(), hotel5.getId());
    }

    @Test
    void findByName() {
        Hotel hotel = hotelRepository.save(createHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> actualResult = hotelRepository.findByName(hotel.getName());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(hotel);
    }

    private static Hotel createHotel(String name, String photo) {
        return Hotel.builder()
                .name(name)
                .photo(photo)
                .build();
    }
}
