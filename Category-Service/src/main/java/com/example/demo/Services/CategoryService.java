package com.example.demo.Services;

import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.CategoryDto;
import com.example.demo.Model.Category;

import java.util.List;


public interface CategoryService extends BaseService<Category, CategoryDto, String> {
    public BaseResponse<List<CategoryDto>> getData(String data);
}
