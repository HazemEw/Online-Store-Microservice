package com.example.demo.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    NOT_FOUND("error.NotFound.message", HttpStatus.NOT_FOUND),

    DUPLICATE_RECORD("error.DuplicateKey.message", HttpStatus.BAD_REQUEST),

    JSON_READ_ERROR("error.JsonRead.message", HttpStatus.BAD_REQUEST);

    private String errorMessage;

    private HttpStatus httpStatus;

    ErrorMessage(String aInString, HttpStatus aInHttpStatus) {
        errorMessage = aInString;
        httpStatus = aInHttpStatus;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
