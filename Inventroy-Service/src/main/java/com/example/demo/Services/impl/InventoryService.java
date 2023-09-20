package com.example.demo.Services.impl;

import com.example.demo.ApiClient.ApiClient;
import com.example.demo.DTOs.BaseResponse;
;
import com.example.demo.DTOs.InventoryDto;
import com.example.demo.Mapper.InventoryMapper;
import com.example.demo.Model.Inventory;
import com.example.demo.Reppsitory.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerWebClientBuilderBeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private  KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private  ApiClient apiClient ;

    @Qualifier("messageSource")
    @Autowired
    MessageSource errorSource;


    public InventoryService(ObjectMapper objectMapper) {
    }


    public BaseResponse<InventoryDto> addEntity(InventoryDto dto) {
        String skuCode = dto.getSkeuCode();
         BaseResponse isFound = apiClient.sendRequest("PRODUCT", "product", skuCode);
        if (isFound.getStatus().equals("200 OK")) {
            BaseResponse<InventoryDto> baseResponse = new BaseResponse<>();
            if (isFoundInventory(skuCode)) {
                updateQuantity(skuCode, dto.getQuantity());
                baseResponse.setMessage("update Quantity Done");
                baseResponse.setStatus("200 Ok");
                baseResponse.setData(inventoryMapper.mapToDto(inventoryRepository.findByskeuCode(dto.getSkeuCode())));
            } else {
                Inventory inventory = inventoryMapper.mapToModel(dto);
                inventory.setId(generateRandomId());
                inventoryRepository.save(inventory);
                baseResponse.setData(inventoryMapper.mapToDto(inventory));
                baseResponse.setMessage("Create Inventory Done");
                baseResponse.setStatus("201 ok");
            }
            return baseResponse;

        } else throw new ResourceNotFoundException(dto.getSkeuCode());

    }

    String generateRandomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    boolean isFoundInventory(String skuCode) {
        Inventory inventory = inventoryRepository.findByskeuCode(skuCode);
        return inventory != null;
    }

    void updateQuantity(String skuCode, Integer quantity) {
        Inventory inventory = inventoryRepository.findByskeuCode(skuCode);
        Integer oldQuantity = inventory.getQuantity();
        inventory.setQuantity(oldQuantity + quantity);
        inventoryRepository.save(inventory);
    }

    public String sendToProduct(String data) {
        kafkaTemplate.send("mytopic",data);
        return "send to Kafka....";
    }
}

