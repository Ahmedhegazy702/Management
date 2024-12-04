package com.management.management.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RunTimeEmailValidator implements ConstraintValidator<EmailValidate, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        //return email != null && email.endsWith("@megadev");
        if (email == null || !email.endsWith("@megadev")) {
            return false;
        }
        return true;
    }
}
