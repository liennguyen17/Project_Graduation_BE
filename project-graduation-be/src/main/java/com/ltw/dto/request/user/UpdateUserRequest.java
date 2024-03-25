package com.ltw.dto.request.user;

import com.ltw.constant.Constants;
import com.ltw.domain.validator.date.DateAnnotation;
import com.ltw.domain.validator.email.EmailAnnotation;
import com.ltw.domain.validator.name.NameAnnotation;
import com.ltw.domain.validator.password.PasswordAnnotation;
import com.ltw.domain.validator.username.UsernameAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserRequest {
    @NotNull(message = Constants.ErrorMessageUserValidation.ID_NOT_NULL)
    private Integer id;
    @NotBlank(message = Constants.ErrorMessageUserValidation.NAME_NOT_BLANK)
    @Size(min = 6, max = 100, message = Constants.ErrorMessageUserValidation.NAME_SIZE)
    @NameAnnotation
    private String name;
    @NotBlank(message = Constants.ErrorMessageUserValidation.USERNAME_NOT_BLANK)
    @Size(min = 6, max = 100, message = Constants.ErrorMessageUserValidation.USERNAME_SIZE)
    @UsernameAnnotation
    private String username;
    @NotBlank(message = Constants.ErrorMessageUserValidation.DOB_NOT_BLANK)
    @DateAnnotation
    private String dob;
    @NotBlank(message = Constants.ErrorMessageUserValidation.ADDRESS_NOT_BLANK)
    private String address;
    @NotBlank(message = Constants.ErrorMessageUserValidation.EMAIL_NOT_BLANK)
    @EmailAnnotation
    private String email;
    @NotBlank(message = Constants.ErrorMessageUserValidation.PHONE_NOT_BLANK)
    private String phone;
    @NotBlank(message = Constants.ErrorMessageUserValidation.SUBJECT_NOT_BLANK)
    private String subject;
    //    @NotNull(message = Constants.ErrorMessageUserValidation.ROLE_NOT_BLANK)
    private Integer roleId;
    private String userCode;
    private String className;
}
