package com.example.demo.Reppsitory;

import com.example.demo.Model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, String> {
    Category findByName(String name);
}
