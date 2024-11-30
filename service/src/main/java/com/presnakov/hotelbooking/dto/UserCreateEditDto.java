package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.validation.UserInfo;
import com.presnakov.hotelbooking.validation.group.CreateAction;
import com.presnakov.hotelbooking.validation.group.UpdateAction;
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
@UserInfo(groups = UpdateAction.class)
public class UserCreateEditDto {
    @Size(min = 3, max = 128)
    String firstname;

    @Size(min = 3, max = 128)
    String lastname;

    @Email
    String username;

    @NotBlank(groups = CreateAction.class)
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
