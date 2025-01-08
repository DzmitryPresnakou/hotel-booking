package com.presnakov.hotelbooking.integration.repository;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import com.presnakov.hotelbooking.database.repository.HotelRepository;
import com.presnakov.hotelbooking.database.repository.RoomRepository;
import com.presnakov.hotelbooking.integration.IntegrationTestBase;
import com.presnakov.hotelbooking.util.CreateDataUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class RoomRepositoryIT extends IntegrationTestBase {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Test
    void save() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel);

        Room actualResult = roomRepository.save(room);

        assertNotNull(actualResult.getId());
    }

    @Test
    void update() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        room.setRoomClass(RoomClassEnum.COMFORT);
        room.setPricePerDay(40);
        room.setOccupancy(3);
        room.setPhoto("roomphoto111.jpg");
        roomRepository.save(room);

        Room updatedRoom = roomRepository.findById(room.getId()).get();
        assertThat(updatedRoom).isEqualTo(room);
    }

    @Test
    void delete() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        roomRepository.delete(room);

        assertThat(roomRepository.findById(room.getId())).isEmpty();
    }

    @Test
    void findById() {
        Hotel hotel = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Room room = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel));

        Optional<Room> actualResult = roomRepository.findById(room.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(room);
    }

    @Test
    void findAll() {
        Hotel hotel1 = hotelRepository.save(CreateDataUtil.createHotel("Plaza", "hotelphoto001.jpg"));
        Hotel hotel2 = hotelRepository.save(CreateDataUtil.createHotel("Minsk", "hotelphoto002.jpg"));
        Room room1 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.ECONOMY, 29, "roomphoto001.jpg", 2, hotel1));
        Room room2 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.COMFORT, 59, "roomphoto002.jpg", 3, hotel2));
        Room room3 = roomRepository.save(CreateDataUtil.createRoom(RoomClassEnum.BUSINESS, 79, "roomphoto003.jpg", 4, hotel2));

        List<Room> actualResult = (List<Room>) roomRepository.findAll();

        List<Integer> roomIds = actualResult.stream()
                .map(Room::getId)
                .toList();
        assertThat(actualResult).hasSize(3);
        assertThat(roomIds).contains(room1.getId(), room2.getId(), room3.getId());
    }
}
