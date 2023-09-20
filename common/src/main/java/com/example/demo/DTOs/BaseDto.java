package com.example.demo.DTOs;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDto<T extends Serializable> {

    private T id;
    private String name;
    private String description;
    private Long creationTime;
    private Long lastUpdateTime;
    private String version;
}
