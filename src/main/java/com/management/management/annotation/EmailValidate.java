package com.management.management.annotation;


import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RunTimeEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidate {
    String message() default "Email must end with @megadev";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};

}

