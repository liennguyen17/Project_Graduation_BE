package com.ltw.service.auth;

import com.ltw.dto.request.auth.LoginRequest;
import com.ltw.dto.request.auth.RegisterRequest;
import com.ltw.dto.response.LoginResponse;

public interface AuthenService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
    void registerUser(RegisterRequest signUpRequest);
}
