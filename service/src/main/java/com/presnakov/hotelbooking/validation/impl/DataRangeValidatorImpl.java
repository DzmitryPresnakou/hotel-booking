package com.presnakov.hotelbooking.validation.impl;

import com.presnakov.hotelbooking.validation.DataRange;
import com.presnakov.hotelbooking.validation.DataRangeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class DataRangeValidatorImpl implements ConstraintValidator<DataRange, DataRangeValidator> {

    @Override
    public boolean isValid(DataRangeValidator order, ConstraintValidatorContext context) {
        if (order.getCheckIn() != null && order.getCheckOut() != null && !order.getCheckOut().isAfter(order.getCheckIn())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Check-out must be after check-in")
                    .addPropertyNode("checkOut")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
