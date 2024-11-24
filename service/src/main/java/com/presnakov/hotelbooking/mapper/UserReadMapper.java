package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.database.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .username(object.getUsername())
                .role(object.getRole())
                .phone(object.getPhone())
                .photo(object.getPhoto())
                .money(object.getMoney())
                .birthDate(object.getBirthDate())
                .build();
    }
}
