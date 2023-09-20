package com.example.demo.Mapper;

import com.example.demo.DTOs.CategoryDto;
import com.example.demo.Model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryDto mapToDto(Category category);

    Category mapToModel(CategoryDto categoryDto);

    List<CategoryDto> mapListTODto(List<Category> categories);
}
