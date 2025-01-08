package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import com.presnakov.hotelbooking.validation.RoomInfo;
import com.presnakov.hotelbooking.validation.ValidPhoto;
import com.presnakov.hotelbooking.validation.group.CreateAction;
import com.presnakov.hotelbooking.validation.group.UpdateAction;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
@FieldNameConstants
@RoomInfo(groups = UpdateAction.class)
public class RoomCreateEditDto {
    @NotNull(message = "Rooms are required", groups = CreateAction.class)
    @Min(value = 1, message = "At least one room is required")
    @Max(value = 4, message = "At most 4 rooms")
    @Positive
    Integer occupancy;

    @NotNull
    RoomClassEnum roomClass;

    MultipartFile photo;

    @NotNull(message = "Daily cost is required", groups = CreateAction.class)
    @Min(value = 10, message = "At least $10 is required")
    @Positive
    Integer pricePerDay;

    @NotNull(message = "Photo is required", groups = CreateAction.class)
    @ValidPhoto(groups = CreateAction.class)
    Integer hotelId;
}
