package com.ltw.controller;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.request.auth.LoginRequest;
import com.ltw.dto.request.auth.RegisterRequest;
import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.LoginResponse;
import com.ltw.exception.DataNotFoundException;
import com.ltw.service.auth.AuthenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenService authenService;
    @PostMapping("/login")
    public BaseItemResponse<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
//        LoginResponse response = authenService.authenticateUser(loginRequest);
        return BaseResponse.successData(authenService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public BaseResponse registerUser(@Valid @RequestBody RegisterRequest signUpRequest){
        try{
            authenService.registerUser(signUpRequest);
            return BaseResponse.successData("Đăng ký người dùng thành công");
        }catch (DataNotFoundException e){
            return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, "Có lỗi xảy ra trong quá trình đăng ký");
        }
    }
}
