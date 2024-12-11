package com.presnakov.hotelbooking.validation.impl;

import com.presnakov.hotelbooking.dto.RoomCreateEditDto;
import com.presnakov.hotelbooking.validation.RoomInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class RoomInfoValidator implements ConstraintValidator<RoomInfo, RoomCreateEditDto> {

    @Override
    public boolean isValid(RoomCreateEditDto value, ConstraintValidatorContext context) {
        return hasText(String.valueOf(value.getOccupancy())) || hasText(String.valueOf(value.getPricePerDay()));
    }
}
