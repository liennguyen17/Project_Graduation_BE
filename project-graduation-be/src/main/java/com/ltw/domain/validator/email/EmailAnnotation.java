package com.ltw.domain.validator.email;

import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
//@Target({ElementType.FIELD, ElementType.METHOD,ElementType.PARAMETER})
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAnnotation {
    String message() default "Email không đúng định dang!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
