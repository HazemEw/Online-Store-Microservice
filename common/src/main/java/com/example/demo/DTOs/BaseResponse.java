package com.example.demo.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T>
{
    private String status;
    private String message;
    private T data;

    public BaseResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {

    }
}
