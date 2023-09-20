package com.example.demo.Reppsitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
@Repository
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {
    T findByName(String name);
}
