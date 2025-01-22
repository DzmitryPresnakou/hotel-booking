package com.presnakov.hotelbooking.validation;

import java.time.LocalDate;

public interface DataRangeValidator {

    LocalDate getCheckIn();

    LocalDate getCheckOut();
}
