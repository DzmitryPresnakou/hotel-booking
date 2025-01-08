package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.validation.UniqueEmail;
import com.presnakov.hotelbooking.validation.UserInfo;
import com.presnakov.hotelbooking.validation.ValidPhoto;
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
@UserInfo
public class UserCreateEditDto {
    @Size(min = 3, max = 64, message = "The first name should be between 3 and 64 characters")
    String firstname;

    @Size(min = 3, max = 64, message = "The last name should be between 3 and 64 characters")
    String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    @UniqueEmail(groups = CreateAction.class)
    String username;

    @NotBlank(message = "Password is required", groups = CreateAction.class)
    @Size(min = 3, max = 64, message = "Password must be between 8 and 64 characters", groups = CreateAction.class)
    String rawPassword;

    RoleEnum role;

    @NotBlank(message = "Phone number is required", groups = CreateAction.class)
    String phone;

    @NotNull(message = "Money is required", groups = CreateAction.class)
    @PositiveOrZero
    Integer money;

    @NotNull(message = "Birthdate is required", groups = CreateAction.class)
    @Past(message = "Birthdate must be in the past")
    LocalDate birthDate;

    @ValidPhoto(groups = CreateAction.class)
    @NotNull(message = "Photo is required", groups = CreateAction.class)
    MultipartFile photo;
}
