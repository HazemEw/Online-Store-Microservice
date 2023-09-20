package com.example.demo.Mapper;


import com.example.demo.DTOs.InventoryDto;
import com.example.demo.Model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends BaseMapper {
    InventoryMapper MAPPER = Mappers.getMapper(InventoryMapper.class);

    InventoryDto mapToDto(Inventory inventory);

    Inventory mapToModel(InventoryDto inventoryDto);
    
}
