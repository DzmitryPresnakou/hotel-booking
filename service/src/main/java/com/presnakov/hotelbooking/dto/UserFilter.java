package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {
    String firstname;
    String lastname;
    String username;
    RoleEnum role;
    Integer money;
    LocalDate birthDate;
    Boolean isActive;
}
