package com.example.demo.Reppsitory;


import com.example.demo.Model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String> {
    Inventory findByskeuCode(String skeuCode);
}
