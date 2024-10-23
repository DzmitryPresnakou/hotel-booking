package com.presnakov.hotelbooking.repository;

import com.presnakov.hotelbooking.entity.Hotel;
import com.presnakov.hotelbooking.integration.EntityITBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HotelRepositoryIT extends EntityITBase {

    protected static HotelRepository hotelRepository;

    @BeforeEach
    void createRepository() {
        hotelRepository = applicationContext.getBean(HotelRepository.class);
    }

    @Test
    void save() {
        Hotel hotel = getHotel("Bobruisk", "hotelphoto001.jpg");

        Hotel actualResult = hotelRepository.save(hotel);

        assertNotNull(actualResult.getId());
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(getHotel("First World Hotel & Plaza", "photo1.jpg"));

        hotelRepository.delete(hotel);

        assertThat(hotelRepository.findById(hotel.getId())).isEmpty();
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(getHotel("First World Hotel & Plaza", "photo1.jpg"));
        hotel.setName("Minsk");
        hotel.setPhoto("photo10.jpg");

        hotelRepository.update(hotel);

        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).get();
        assertThat(updatedHotel).isEqualTo(hotel);
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(getHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> actualResult = hotelRepository.findById(hotel.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(hotel);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(getHotel("First World Hotel & Plaza", "photo1.jpg"));
        Hotel hotel2 = hotelRepository.save(getHotel("Flamingo Las Vegas", "photo2.jpg"));
        Hotel hotel3 = hotelRepository.save(getHotel("Atlantis Paradise Island", "photo3.jpg"));
        Hotel hotel4 = hotelRepository.save(getHotel("Hilton Hawaiian Village", "photo4.jpg"));
        Hotel hotel5 = hotelRepository.save(getHotel("Disneys Port Orleans Resort", "photo5.jpg"));

        List<Hotel> actualResult = hotelRepository.findAll();

        List<Integer> hotelIds = actualResult.stream()
                .map(Hotel::getId)
                .toList();
        assertThat(actualResult).hasSize(5);
        assertThat(hotelIds).contains(hotel1.getId(), hotel2.getId(), hotel3.getId(), hotel4.getId(), hotel5.getId());
    }

    @Test
    void findByName() {
        Hotel hotel = hotelRepository.save(getHotel("First World Hotel & Plaza", "photo1.jpg"));

        Optional<Hotel> actualResult = hotelRepository.findByName(hotel.getName());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(hotel);
    }

    private static Hotel getHotel(String name, String photo) {
        return Hotel.builder()
                .name(name)
                .photo(photo)
                .build();
    }
}
