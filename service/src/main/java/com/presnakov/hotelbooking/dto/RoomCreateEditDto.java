package com.presnakov.hotelbooking.dto;

import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import com.presnakov.hotelbooking.validation.RoomInfo;
import com.presnakov.hotelbooking.validation.group.UpdateAction;
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
    @NotNull
    @Positive
    Integer occupancy;

    @NotNull
    RoomClassEnum roomClass;

    MultipartFile photo;

    @NotNull
    @Positive
    Integer pricePerDay;

    @NotNull
    Integer hotelId;
}
