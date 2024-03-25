package com.ltw.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String jwt;
    private Integer id;
    private String name;
    private String username;
    private String phone;
    private String email;
//    private String role;
    private List<String> roles;
}

