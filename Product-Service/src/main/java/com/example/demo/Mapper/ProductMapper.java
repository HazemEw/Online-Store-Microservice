package com.example.demo.Mapper;

import com.example.demo.DTOs.ProductDto;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Reppsitory.CategoryRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper{

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto mapToDto(Product product);

    Product mapToModel(ProductDto productDto , @Context CategoryRepository categoryRepository);

    List<ProductDto> mapListTODto(List<Product> products);

    default String mapCategoryToString(Category aICategory) {
        return aICategory.getName();
    }

    default Category map(String aInString,@Context CategoryRepository categoryRepository) {
       return categoryRepository.findByName(aInString);

    }

}
