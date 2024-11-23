package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.entity.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@Builder
@FieldNameConstants
public class UserCreateEditDto {
    @NotBlank
    @Size(min = 3, max = 128)
    String firstName;

    @NotBlank
    @Size(min = 3, max = 128)
    String lastName;

    @Email
    String email;

    @NotBlank()
    String rawPassword;

    RoleEnum role;

    @NotBlank
    @Size(min = 3, max = 64)
    String phone;

    @NotNull
    @PositiveOrZero
    Integer money;

    @NotNull
    @Past
    LocalDate birthDate;

    MultipartFile photo;
}
