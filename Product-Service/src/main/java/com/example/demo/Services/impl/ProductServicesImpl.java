package com.example.demo.Services.impl;

import com.example.demo.DTOs.BaseResponse;
import com.example.demo.DTOs.ProductDto;
import com.example.demo.Mapper.BaseMapper;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Reppsitory.BaseRepository;
import com.example.demo.Reppsitory.CategoryRepository;
import com.example.demo.Reppsitory.ProductRepository;
import com.example.demo.Services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ProductServicesImpl extends MySqlBaseServiceImpl<Product, ProductDto, String> implements ProductService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public ProductServicesImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public BaseRepository<Product, String> getRepository() {
        return productRepository;
    }

    @Override
    public BaseMapper getMapper() {
        return productMapper;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public MessageSource getErrorSource() {
        return messageSource;
    }

    public BaseResponse<ProductDto> addEntity(ProductDto inProduct) {

        String categoryName = inProduct.getCategory();
        ResponseEntity<BaseResponse> category = restTemplate.getForEntity("http://CATEGORY/api/v1.0/category?name={categoryName}", BaseResponse.class,categoryName);
        if (! category.getBody().getStatus().equals("200 OK"))
            throw new ResourceNotFoundException(inProduct.getCategory());
        else {
            Category lcategory = categoryRepository.findByName(categoryName);
            Product lproduct = productMapper.mapToModel(inProduct, categoryRepository);
            lproduct.setId(generateRandomId());
            lproduct.setCreationTime(System.currentTimeMillis());
            lproduct.setLastUpdateTime(System.currentTimeMillis());
            lproduct.setVersion("V1");
            lproduct.setCategory(lcategory);
            if (entityExists(lproduct))
                throw new DuplicateKeyException(lproduct.getName());
            productRepository.save(lproduct);
            BaseResponse<ProductDto> baseResponse = new BaseResponse<>();
            baseResponse.setData(productMapper.mapToDto(productRepository.findByName(inProduct.getName())));
            baseResponse.setStatus(HttpStatus.OK.toString());
            baseResponse.setMessage("Add successfully");
            return baseResponse;

        }
    }

    public ProductDto findById(String inProductName, String inProductCategory) {
        Optional<Product> foundProduct = StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> product.getName().equals(inProductName))
                .filter(product -> product.getCategory().getName().equalsIgnoreCase(inProductCategory))
                .findFirst();

        if (foundProduct.isPresent()) {
            return (ProductDto) productMapper.mapToDto(foundProduct.get());
        } else {
            throw new ResourceNotFoundException(inProductCategory);
        }
    }

    public BaseResponse<ProductDto> findByName(String inProductName) {
        Product product = productRepository.findByName(inProductName);
        if (product != null) {
            BaseResponse<ProductDto> baseResponse = new BaseResponse<>();
            baseResponse.setData((ProductDto) productMapper.mapToDto(product));
            baseResponse.setStatus(HttpStatus.OK.toString());
            baseResponse.setMessage("Find Product successfully");
            return baseResponse;
        } else {
            throw new ResourceNotFoundException(inProductName);
        }
    }


    @Override
    public  void readFromKafka(String data) {
        Product product = productRepository.findByName(data);
        if (product!=null)
            productRepository.delete(product);
    }


    public BaseResponse<ProductDto> updateEntity(ProductDto inProduct) {
        BaseResponse<ProductDto> baseResponse = new BaseResponse<>();
        Category category = categoryRepository.findByName(inProduct.getCategory());
        Product newProduct = productMapper.mapToModel(inProduct, categoryRepository);
        if (!entityExists(newProduct)) {
            throw new ResourceNotFoundException(newProduct.getName());
        }
        Product oldProduct = productRepository.findByName(newProduct.getName());
        Category lcategory = categoryRepository.findByName(inProduct.getCategory());
        if (lcategory == null)
            throw new ResourceNotFoundException(inProduct.getCategory());
        else {
            oldProduct.setCategory(newProduct.getCategory());
            oldProduct.setPrice(newProduct.getPrice());
            oldProduct.setDescription(newProduct.getDescription());
            oldProduct.setLastUpdateTime(System.currentTimeMillis());
            productRepository.save(oldProduct);
            baseResponse.setMessage("Product update successfully");
            baseResponse.setStatus(HttpStatus.OK.toString());
            baseResponse.setData(productMapper.mapToDto(productRepository.findByName(oldProduct.getName())));
            return baseResponse;
        }
    }

    public BaseResponse<List<ProductDto>> getData(String data) {
        BaseResponse<List<ProductDto>> baseResponse = new BaseResponse<>();
        if (data == null) {
            baseResponse.setData(getAll());
            baseResponse.setMessage("Read All Products successfully");
            baseResponse.setStatus(HttpStatus.OK.toString());
            return baseResponse;
        } else {
            try {
                JsonNode jsonData = getObjectMapper().readTree(data);
                String productName = jsonData.get("name").asText();
                String productCategory = jsonData.get("category").asText();
                Category category = categoryRepository.findByName(productCategory);
                if (productName != null && productCategory != null) {
                    List<ProductDto> list = new ArrayList<>();
                    list.add(findById(productName, productCategory));
                    baseResponse.setData(list);
                    baseResponse.setMessage("Read Product Done successfully");
                    baseResponse.setStatus(HttpStatus.OK.toString());
                    return baseResponse;
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return null;
    }


}



