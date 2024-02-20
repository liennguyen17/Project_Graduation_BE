package com.ltw.exception;

import com.ltw.constant.ErrorCodeDefs;
import com.ltw.dto.response.BaseResponse;
import com.ltw.dto.response.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(1)
    public BaseResponse handleException(Exception ex) {
        log.error("Exception: {}", ex);
        return BaseResponse.error(ErrorCodeDefs.ERR_OTHER, ex.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(-1)
    public BaseResponse handleNotReadableException(HttpMessageNotReadableException ex) {
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Order(-1)
    public BaseResponse handleExceptionMethod(MethodArgumentNotValidException ex) {
        BaseResponse response = new BaseResponse();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ErrorDetail> errorDetails = new ArrayList<>();
        for (FieldError fieldError : errors) {
            ErrorDetail error = new ErrorDetail();
            error.setId(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());
            errorDetails.add(error);
        }
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION,
                ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION),
                errorDetails);
    }

    @ResponseStatus(OK)
    @ResponseBody
    @ExceptionHandler(value = {BaseValidateException.class})
    @Order(1)
    public BaseResponse handleValidateException(BaseValidateException ex) {
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION, ex.getMessage());
    }

    @ResponseStatus(OK)
    @ResponseBody
    @ExceptionHandler(value = {DataNotFoundException.class})
    @Order(1)
    public BaseResponse handleValidateException(DataNotFoundException ex) {
        return BaseResponse.error(ErrorCodeDefs.ERR_OBJECT_NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(OK)
    @ResponseBody
    @ExceptionHandler(value = {ErrorDetailException.class})
    @Order(1)
    public BaseResponse handleErrorDetailException(ErrorDetailException ex) {
        return BaseResponse.error(ErrorCodeDefs.ERR_VALIDATION,
                ErrorCodeDefs.getMessage(ErrorCodeDefs.ERR_VALIDATION),
                ex.getErrorDetails());
    }
}
