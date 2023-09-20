package com.example.demo.Controller;
import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.ProductDto;
import com.example.demo.Services.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/product")
@Validated

public class ProductController implements BaseController<ProductDto, ProductService> {

    @Autowired
    private ProductService productService;


    @Override
    public ProductService getBaseService() {
        return productService;
    }

    @PostMapping("/import")
    public BaseResponse<List<ProductDto>> importData(@RequestParam @NotNull MultipartFile dataFile) {
        return productService.importData(dataFile, ProductDto.class);
    }

    @GetMapping
    public BaseResponse<List<ProductDto>> getData(@RequestParam(name = "data", required = false) String productData) {
        return productService.getData(productData);
    }

    @GetMapping("/ById")
    public BaseResponse<ProductDto> readByName(@RequestParam String name) {
        return productService.findByName(name);
    }
}
