package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@MappedSuperclass
@Data
public  class BaseModel<T extends Serializable> {

    @Column(unique = true)
    @NotEmpty(message = "name must be not Empty or Null")
    private String name;

    private Long creationTime;

    private Long lastUpdateTime;

    @JsonIgnore
    private String version;
}
