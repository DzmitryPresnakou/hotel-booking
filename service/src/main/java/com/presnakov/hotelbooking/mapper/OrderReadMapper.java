package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.dto.OrderReadDto;
import com.presnakov.hotelbooking.dto.RoomReadDto;
import com.presnakov.hotelbooking.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper userReadMapper;
    private final RoomReadMapper roomReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        RoomReadDto room = Optional.ofNullable(object.getRoom())
                .map(roomReadMapper::map)
                .orElse(null);
        return OrderReadDto.builder()
                .id(object.getId())
                .user(user)
                .room(room)
                .status(object.getStatus())
                .paymentStatus(object.getPaymentStatus())
                .checkInDate(object.getCheckInDate())
                .checkOutDate(object.getCheckOutDate())
                .build();
    }
}
