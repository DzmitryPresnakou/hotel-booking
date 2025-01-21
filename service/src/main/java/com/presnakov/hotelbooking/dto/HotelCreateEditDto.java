package com.presnakov.hotelbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
@FieldNameConstants
public class HotelCreateEditDto {
    @NotBlank
    @Size(min = 3, max = 128, message = "name must be between 3 and 128 symbols")
    String name;

    MultipartFile photo;
}
