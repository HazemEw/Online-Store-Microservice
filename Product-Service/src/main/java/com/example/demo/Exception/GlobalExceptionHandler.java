package com.example.demo.Exception;

import com.example.demo.DTOs.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public BaseResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.NOT_FOUND;
        return new BaseResponse<>(errorMessage.getHttpStatus().toString(), ex.getMessage() + " " + getErrorMessage(errorMessage.getErrorMessage()));
    }



    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResponse handleDuplicateKeyException(DuplicateKeyException ex) {
        ErrorMessage errorMessage = ErrorMessage.DUPLICATE_RECORD;
        return new BaseResponse<>(errorMessage.getHttpStatus().toString(), ex.getMessage() + " " + getErrorMessage(errorMessage.getErrorMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorMessage errorMessage = ErrorMessage.JSON_READ_ERROR;
        return new BaseResponse<>(errorMessage.getHttpStatus().toString(), ex.getMessage() + " " + getErrorMessage(errorMessage.getErrorMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new BaseResponse<>(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());

    }

    public String getErrorMessage(String errorCode) {
        return messageSource.getMessage(errorCode, null, null);
    }
}
