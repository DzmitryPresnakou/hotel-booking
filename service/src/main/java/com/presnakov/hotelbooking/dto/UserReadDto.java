package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserReadDto {
    Integer id;
    String firstname;
    String lastname;
    String username;
    RoleEnum role;
    String phone;
    String photo;
    Integer money;
    LocalDate birthDate;
}
