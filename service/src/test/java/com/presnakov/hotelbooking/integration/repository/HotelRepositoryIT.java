package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.database.repository.HotelRepository;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class HotelRepositoryIT extends IntegrationTestBase {

    private final HotelRepository hotelRepository;

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> maybeHotel = hotelRepository.findById(hotel.getId());

        assertTrue(maybeHotel.isPresent());
        maybeHotel.ifPresent(hotelRepository::delete);
        assertTrue(hotelRepository.findById(hotel.getId()).isEmpty());
    }

    @Test
    void save() {
        Hotel hotel = CreateDataUtil.createHotel("Bobruisk", "hotelphoto001.jpg");

        Hotel actualResult = hotelRepository.save(hotel);

        assertNotNull(actualResult.getId());
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("First World Hotel & Plaza", "photo1.jpg"));
        hotel.setName("Minsk");
        hotel.setPhoto("photo10.jpg");

        hotelRepository.save(hotel);

        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).get();
        assertThat(updatedHotel).isEqualTo(hotel);
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> actualResult = hotelRepository.findById(hotel.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(hotel);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(CreateDataUtil.createHotel("First World Hotel & Plaza", "photo1.jpg"));
        Hotel hotel2 = hotelRepository.save(CreateDataUtil.createHotel("Flamingo Las Vegas", "photo2.jpg"));
        Hotel hotel3 = hotelRepository.save(CreateDataUtil.createHotel("Atlantis Paradise Island", "photo3.jpg"));
        Hotel hotel4 = hotelRepository.save(CreateDataUtil.createHotel("Hilton Hawaiian Village", "photo4.jpg"));
        Hotel hotel5 = hotelRepository.save(CreateDataUtil.createHotel("Disneys Port Orleans Resort", "photo5.jpg"));

        List<Hotel> actualResult = (List<Hotel>) hotelRepository.findAll();

        List<Integer> hotelIds = actualResult.stream()
                .map(Hotel::getId)
                .toList();
        assertThat(actualResult).hasSize(5);
        assertThat(hotelIds).contains(hotel1.getId(), hotel2.getId(), hotel3.getId(), hotel4.getId(), hotel5.getId());
    }

    @Test
    void findByName() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> actualResult = hotelRepository.findByName(hotel.getName());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(hotel);
    }
}
