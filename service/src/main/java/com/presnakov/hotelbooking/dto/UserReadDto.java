package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.RoleEnum;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserReadDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    RoleEnum role;
    String phone;
    String photo;
    Integer money;
    LocalDate birthDate;
}
