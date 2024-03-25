package com.ltw.dto.entity.users;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
@Data
public class UserUpdateValueDto {
    private Integer id;
    private String name;
    private String username;
    private String dob;
    private String address;
    private String email;
    private String phone;
    private String subject;
    private String role;
    private String userCode;
    private String className;
}
