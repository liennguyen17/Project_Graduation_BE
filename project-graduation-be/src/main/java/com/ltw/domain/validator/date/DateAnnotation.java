package com.ltw.domain.validator.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = DateValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface DateAnnotation {
    String message() default "Ngày không đúng định dạng (yyyy-MM-dd)!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
