package com.ltw.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
//        BaseResponse.error(ErrorCodeDefs.ERR_PERMISSION_INVALID, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_TOKEN_INVALID));
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, BaseResponse.error(ErrorCodeDefs.ERR_PERMISSION_INVALID, ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_TOKEN_INVALID)));
        outputStream.flush();
    }
}
