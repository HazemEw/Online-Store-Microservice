package com.example.demo.Services;

import com.example.demo.DTOs.BaseDto;
import com.example.demo.DTOs.BaseResponse;
import com.example.demo.Model.BaseModel;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;


public interface BaseService<E extends BaseModel<ID>, D extends BaseDto<ID>,ID extends Serializable> {
    public abstract BaseResponse<D> addEntity(D dto);
    public abstract BaseResponse<D> updateEntity(D dto);
    List<D> getAll();
    @Transactional
    BaseResponse<List<D>> importData(MultipartFile file, Class<D> dtoClass);
    BaseResponse<String> deleteEntity(String inEntityName);



}
