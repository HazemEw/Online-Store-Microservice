package com.example.demo.Mapper;

import com.example.demo.DTOs.BaseDto;
import com.example.demo.Model.BaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component("BaseMapper")
public interface BaseMapper {
    BaseMapper MAPPER = Mappers.getMapper(BaseMapper.class);
    BaseDto mapToDto(BaseModel product);

    BaseModel mapToModel(BaseDto productDto);

}



