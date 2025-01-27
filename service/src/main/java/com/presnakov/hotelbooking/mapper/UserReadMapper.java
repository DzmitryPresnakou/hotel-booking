package com.presnakov.hotelbooking.mapper;

import com.presnakov.hotelbooking.database.entity.User;
import com.presnakov.hotelbooking.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User user) {
        return UserReadDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .rawPassword(user.getPassword())
                .role(user.getRole())
                .phone(user.getPhone())
                .photo(user.getPhoto())
                .money(user.getMoney())
                .isActive(user.getIsActive())
                .birthDate(user.getBirthDate())
                .build();
    }
}
