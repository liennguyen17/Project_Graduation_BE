package com.ltw.domain.validator.password;

import com.ltw.constant.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordAnnotation {
    String message() default Constants.ErrorMessageUserValidation.PASSWORD_ERROR;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
