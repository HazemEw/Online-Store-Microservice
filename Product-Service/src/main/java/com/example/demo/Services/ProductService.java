package com.example.demo.Services;

import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.ProductDto;
import com.example.demo.Model.Product;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;
public interface ProductService extends BaseService<Product, ProductDto,String> {

    @KafkaListener(topics = "mytopic",groupId = "my-group")
    void readFromKafka(String data);

    public BaseResponse<List<ProductDto>> getData(String data);
    public BaseResponse<ProductDto> findByName(String inProductName);

}
