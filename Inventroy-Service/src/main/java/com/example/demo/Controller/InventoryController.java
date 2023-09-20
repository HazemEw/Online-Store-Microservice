package com.example.demo.Controller;

import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.InventoryDto;
import com.example.demo.Services.impl.InventoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "api/v1.0/inventory")
@Validated
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

//    @PostMapping
//    @CircuitBreaker(name = "getProduct",fallbackMethod = "backMethod")
//    public CompletableFuture<BaseResponse<InventoryDto>> addInventory(@RequestBody InventoryDto inventoryDto) {
//        return CompletableFuture.supplyAsync(() -> {
//            return inventoryService.addEntity(inventoryDto);
//        }).exceptionally(e -> {
//            return backMethod(e).resultNow();
//        });
//    }
//
//    public CompletableFuture<BaseResponse<InventoryDto>> backMethod(Throwable e) {
//        CompletableFuture<BaseResponse<InventoryDto>> future = new CompletableFuture<>();
//        BaseResponse<InventoryDto> baseResponse = new BaseResponse<InventoryDto>();
//        baseResponse.setMessage(e.getMessage());
//        future.complete(baseResponse);
//        return future;
//    }

    @GetMapping
    public String sendToProduct(@RequestParam(name = "data") String data){
        return inventoryService.sendToProduct(data);
    }


    @PostMapping
    @CircuitBreaker(name = "getProduct",fallbackMethod = "backMethod")
    public BaseResponse<InventoryDto> addInventory(@RequestBody InventoryDto inventoryDto) {
        return inventoryService.addEntity(inventoryDto);
    }

    public CompletableFuture<BaseResponse<InventoryDto>> backMethod(Exception e) {
        CompletableFuture<BaseResponse<InventoryDto>> future = new CompletableFuture<>();
        BaseResponse<InventoryDto> baseResponse = new BaseResponse<InventoryDto>();
        baseResponse.setMessage(e.getMessage());
        future.complete(baseResponse);
        return future;
    }
}




