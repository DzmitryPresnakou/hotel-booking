package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getPassword(),
                object.getRole(),
                object.getPhone(),
                object.getPhoto(),
                object.getMoney(),
                object.getBirthDate()
        );
    }
}
