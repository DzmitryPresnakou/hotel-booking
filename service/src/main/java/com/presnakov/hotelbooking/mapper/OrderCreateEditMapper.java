package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.database.repository.RoomRepository;
import com.presnakov.hotelbooking.database.repository.UserRepository;
import com.presnakov.hotelbooking.dto.OrderCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setUser(getUser(object.getUserId()));
        order.setRoom(getRoom(object.getRoomId()));
        order.setCheckInDate(object.getCheckInDate());
        order.setCheckOutDate(object.getCheckOutDate());
        order.setStatus(object.getStatus());
        order.setPaymentStatus(object.getPaymentStatus());
    }

    public User getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }

    public Room getRoom(Integer roomId) {
        return Optional.ofNullable(roomId)
                .flatMap(roomRepository::findById)
                .orElse(null);
    }
}
