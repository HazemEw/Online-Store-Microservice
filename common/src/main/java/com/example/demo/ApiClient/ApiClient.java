package com.example.demo.ApiClient;


import com.example.demo.DTOs.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiClient {

    @Autowired
    private RestTemplate restTemplate;

    public BaseResponse sendRequest(String serviceName, String entityName ,String id) {

        String url = "http://" + serviceName + "/api/v1.0/" + entityName + "/ById?name=" + id;
        return restTemplate.getForObject(url, BaseResponse.class);

    }


}
