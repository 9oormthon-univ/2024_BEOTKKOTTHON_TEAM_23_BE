package com.beotkkotthon.areyousleeping.constants;

import com.beotkkotthon.areyousleeping.annotation.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class LocalDateValidator implements ConstraintValidator<Date, String> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(Date constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            LocalDate.parse(value, DATE_FORMATTER);
        } catch(DateTimeParseException e) {
            log.error("Invalid Date Format. (yyyy-MM-dd)");
            return false;
        }

        return true;
    }
}
