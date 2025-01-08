package com.presnakov.hotelbooking.validation;

import com.presnakov.hotelbooking.validation.impl.RoomInfoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoomInfoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomInfo {

    String message() default "Occupancy or Price Per Day should be filled in";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
