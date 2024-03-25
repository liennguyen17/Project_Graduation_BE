package com.ltw.dto.request.user;

import com.ltw.domain.validator.email.EmailAnnotation;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @EmailAnnotation
    private String email;
}
