package com.ltw.dto.request.user;

import com.ltw.constant.Constants;
import com.ltw.domain.validator.password.PasswordAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = Constants.ErrorMessageUserValidation.PASSWORD_OLD_NOT_BLANK)
    private String oldPassword;

    @NotBlank(message = Constants.ErrorMessageUserValidation.PASSWORD_NEW_NOT_BLANK)
    @Size(min = 6, message = Constants.ErrorMessageUserValidation.PASSWORD_NEW_SIZE)
    @PasswordAnnotation
    private String newPassword;

    @NotBlank(message = Constants.ErrorMessageUserValidation.PASSWORD_CONFIRM_NEW_NOT_BLANK)
    private String confirmNewPassword;
}
