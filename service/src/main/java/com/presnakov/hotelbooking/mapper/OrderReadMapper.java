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
    public OrderReadDto map(Order order) {
        UserReadDto user = Optional.ofNullable(order.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        RoomReadDto room = Optional.ofNullable(order.getRoom())
                .map(roomReadMapper::map)
                .orElse(null);
        return OrderReadDto.builder()
                .id(order.getId())
                .user(user)
                .room(room)
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .checkInDate(order.getCheckInDate())
                .checkOutDate(order.getCheckOutDate())
                .isActive(user != null && room != null ?
                        user.getIsActive() :
                        false)
                .build();
    }
}
