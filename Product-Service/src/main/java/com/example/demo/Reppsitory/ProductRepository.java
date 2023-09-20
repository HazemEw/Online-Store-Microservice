package com.example.demo.Reppsitory;

import com.example.demo.Model.Product;
import org.springframework.stereotype.Repository;
@Repository

public interface ProductRepository extends BaseRepository<Product,String> {
    Product findByName(String name);
}
