package com.example.demo.Services.impl;

import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.CategoryDto;
import com.example.demo.Mapper.BaseMapper;
import com.example.demo.Mapper.CategoryMapper;
import com.example.demo.Model.Category;
import com.example.demo.Reppsitory.BaseRepository;
import com.example.demo.Reppsitory.CategoryRepository;
import com.example.demo.Services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends MySqlBaseServiceImpl<Category, CategoryDto, String> implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    private final ObjectMapper objectMapper;

    @Qualifier("messageSource")
    @Autowired
    MessageSource errorSource;

    public CategoryServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BaseRepository<Category, String> getRepository() {
        return categoryRepository;
    }

    @Override
    public BaseMapper getMapper() {
        return categoryMapper;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public MessageSource getErrorSource() {
        return errorSource;
    }

    @Override
    public BaseResponse<CategoryDto> addEntity(CategoryDto dto) {
        Category lcategroy = categoryMapper.mapToModel(dto);
        lcategroy.setId(generateRandomId());
        lcategroy.setCreationTime(System.currentTimeMillis());
        lcategroy.setLastUpdateTime(System.currentTimeMillis());
        lcategroy.setVersion("V1");
        if (entityExists(lcategroy)) //check name duplicate
            throw new DuplicateKeyException(lcategroy.getName());
        categoryRepository.save(lcategroy);
        BaseResponse<CategoryDto> baseResponse = new BaseResponse<>();
        baseResponse.setData((CategoryDto) categoryMapper.mapToDto(categoryRepository.findByName(dto.getName())));
        baseResponse.setStatus(HttpStatus.OK.toString());
        baseResponse.setMessage("Add successfully");
        return baseResponse;
    }

    @Override
    public BaseResponse<CategoryDto> updateEntity(CategoryDto dto) {
        Category lcategory1 = categoryMapper.mapToModel(dto);
        if (!entityExists(lcategory1))
            throw new ResourceNotFoundException(lcategory1.getName());
        Category lcategory = categoryRepository.findByName(lcategory1.getName());
        lcategory.setDescription(lcategory1.getDescription());
        lcategory.setLastUpdateTime(System.currentTimeMillis());
        categoryRepository.save(lcategory);
        BaseResponse<CategoryDto> baseResponse = new BaseResponse<>();
        baseResponse.setMessage("Category update successfully");
        baseResponse.setStatus(HttpStatus.OK.toString());
        baseResponse.setData(categoryMapper.mapToDto(categoryRepository.findByName(lcategory.getName())));
        return baseResponse;
    }

    public BaseResponse<List<CategoryDto>> getData(String categoryName) {
        BaseResponse<List<CategoryDto>> baseResponse = new BaseResponse<>();
        List<CategoryDto> list = new ArrayList<>();
        if (categoryName == null) {
            baseResponse.setMessage("Read Category's successfully");
            baseResponse.setStatus(HttpStatus.OK.toString());
            baseResponse.setData(getAll());
            return baseResponse;
        } else {
            Category category = categoryRepository.findByName(categoryName);
            if (category == null)
                throw new ResourceNotFoundException(categoryName);
            else {
                list.add(categoryMapper.mapToDto(category));
                baseResponse.setMessage("Read Category successfully");
                baseResponse.setStatus(HttpStatus.OK.toString());
                baseResponse.setData(list);
                return baseResponse;
            }
        }
    }

}

