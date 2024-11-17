package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.RoleEnum;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@Builder
@FieldNameConstants
public class UserCreateEditDto {
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
