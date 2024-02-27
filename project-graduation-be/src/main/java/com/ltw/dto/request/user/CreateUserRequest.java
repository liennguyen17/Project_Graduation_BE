package com.ltw.dto.request.user;

import com.ltw.domain.validator.date.DateAnnotation;
import com.ltw.domain.validator.email.EmailAnnotation;
import com.ltw.domain.validator.name.NameAnnotation;
import com.ltw.domain.validator.password.PasswordAnnotation;
import com.ltw.domain.validator.username.UsernameAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateUserRequest {
    @NotBlank(message = "Tên nguời dùng không được để trống!")
    @Size(min = 6, max = 100, message = "Tên người dùng phải có ít nhất 6, nhiều nhất 100 kí tự!")
    @NameAnnotation
    private String name;
    @NotBlank(message = "User name nguời dùng không được để trống!")
    @Size(min = 6, max = 100, message = "User name người dùng phải có ít nhất 6, nhiều nhất 100 kí tự!")
    @UsernameAnnotation
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự!")
    @PasswordAnnotation
    private String password;
    @NotBlank(message = "Ngày sinh không được để trống!")
    @DateAnnotation
    private String dob;
    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;
    @NotBlank(message = "Email không được để trống!")
    @EmailAnnotation
    private String email;
    @NotBlank(message = "Số điện thoại không được để trống!")
    private String phone;
    @NotBlank(message = "Bộ môn không được để trống!")
    private String subject;
//    @NotNull(message = "Vai trò không được để trống!")
    private Integer roleId;


}
