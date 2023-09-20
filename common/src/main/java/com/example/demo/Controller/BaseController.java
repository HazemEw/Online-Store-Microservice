package com.example.demo.Controller;

import com.example.demo.DTOs.BaseDto;
import com.example.demo.DTOs.BaseResponse;
import com.example.demo.Services.BaseService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@Validated
public interface BaseController<D extends BaseDto, S extends BaseService> {


    S getBaseService();

    @PostMapping
    default BaseResponse<BaseDto> addObject(@RequestBody @Valid D dto) {
        return getBaseService().addEntity(dto);
    }

    @DeleteMapping
    default BaseResponse<String> deleteObject(@RequestParam(name = "name") String EntityName) {
        return getBaseService().deleteEntity(EntityName);
    }

    @PutMapping
    default BaseResponse<BaseDto> updateObject(@RequestBody D dto) {
        return getBaseService().updateEntity(dto);
    }
}

