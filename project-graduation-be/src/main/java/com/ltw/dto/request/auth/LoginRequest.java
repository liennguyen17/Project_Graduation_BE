package com.ltw.dto.request.auth;

import com.ltw.constant.Constants;
import com.ltw.domain.validator.username.UsernameAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = Constants.AuthValidation.USER_NAME_NOT_BLANK)
    @Size(min = 6, max = 100, message = Constants.AuthValidation.USER_NAME_SIZE)
    @UsernameAnnotation
    private String username;
    @NotBlank(message = Constants.AuthValidation.PASSWORD_NOT_BLANK)
    @Size(min = 6, max = 20, message = Constants.AuthValidation.PASSWORD_SIZE)
    private String password;
}
