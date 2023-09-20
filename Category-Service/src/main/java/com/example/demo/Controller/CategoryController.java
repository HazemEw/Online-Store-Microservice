package com.example.demo.Controller;

import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.CategoryDto;
import com.example.demo.Services.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/category")
@Validated
public class CategoryController implements BaseController<CategoryDto, CategoryService> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public CategoryService getBaseService() {
        return categoryService;
    }

    @PostMapping("/import")
    public BaseResponse<List<CategoryDto>> importData(@RequestParam @NotNull MultipartFile dataFile) {
        return categoryService.importData(dataFile, CategoryDto.class);
    }

    @GetMapping
    public BaseResponse<List<CategoryDto>> getData(@RequestParam(name = "name", required = false) String name) {
        return categoryService.getData(name);
    }
}


