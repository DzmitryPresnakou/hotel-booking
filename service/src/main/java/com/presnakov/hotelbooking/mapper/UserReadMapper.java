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
        return UserReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole())
                .phone(object.getPhone())
                .photo(object.getPhoto())
                .money(object.getMoney())
                .birthDate(object.getBirthDate())
                .build();
    }
}
