package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.RoleEnum;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {
    String firstName;
    String lastName;
    String email;
    RoleEnum role;
    Integer money;
    LocalDate birthDate;
}
