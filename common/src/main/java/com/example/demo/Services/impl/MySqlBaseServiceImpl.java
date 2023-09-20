package com.example.demo.Services.impl;

import com.example.demo.DTOs.BaseDto;
import com.example.demo.DTOs.BaseResponse;
import com.example.demo.Mapper.BaseMapper;
import com.example.demo.Model.BaseModel;
import com.example.demo.Reppsitory.BaseRepository;
import com.example.demo.Services.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;


public abstract class MySqlBaseServiceImpl<E extends BaseModel<ID>, D extends BaseDto<ID>, ID extends Serializable> implements BaseService<E, D, ID> {


    public abstract BaseRepository<E, ID> getRepository();

    public abstract BaseMapper getMapper();
    public abstract ObjectMapper getObjectMapper();
    public abstract MessageSource getErrorSource();
    public abstract BaseResponse<List<D>> getData(ID id);
    String generateRandomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    boolean entityExists(E entity) {
        return StreamSupport.stream(getRepository().findAll().spliterator(), false).
                anyMatch(lentity -> lentity.getName().equals(entity.getName()));
    }
    @Override
    public BaseResponse<List<D>> importData(MultipartFile file, Class<D> dtoClass) {
        List<D> addedEntities = new ArrayList<>();
        try {
            TypeFactory typeFactory = getObjectMapper().getTypeFactory();
            List<D> Entities = getObjectMapper().readValue(file.getBytes(), typeFactory.constructCollectionType(List.class, dtoClass));
            for (D entity : Entities) {
                D savedEntity = addEntity(entity).getData();
                addedEntities.add(savedEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseResponse<List<D>> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.toString());
        baseResponse.setMessage("import data successfully");
        baseResponse.setData(addedEntities);
        return baseResponse;
    }
    @Override
    public BaseResponse<String> deleteEntity(String inEntityName) {
        E lentity = getRepository().findByName(inEntityName);
        if (lentity == null)
            throw new ResourceNotFoundException(inEntityName);
        else {
            String name = lentity.getName();
            getRepository().delete(lentity);
            BaseResponse<String> baseResponse = new BaseResponse<>();
            baseResponse.setStatus(HttpStatus.OK.toString());
            baseResponse.setMessage("delete " + lentity.getName() + "successfully");
            baseResponse.setData(lentity.getName());
            return baseResponse;
        }
    }
    @Override
    public List<D> getAll() {
        List<D> list = new ArrayList<>();
        getRepository().findAll().forEach(e ->
                list.add((D) getMapper().mapToDto(e))
        );
        return list;
    }

    public abstract BaseResponse<D> addEntity(D dto);


}

