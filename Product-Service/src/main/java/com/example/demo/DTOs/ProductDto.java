package com.example.demo.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class ProductDto extends BaseDto<String> {

    private BigDecimal price;

    private String category;
}
