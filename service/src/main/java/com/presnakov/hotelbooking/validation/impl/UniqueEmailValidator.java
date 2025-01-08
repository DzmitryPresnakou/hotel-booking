package com.presnakov.hotelbooking.validation.impl;

import com.presnakov.hotelbooking.service.UserService;
import com.presnakov.hotelbooking.validation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.findByUsername(email).isEmpty();
    }
}
