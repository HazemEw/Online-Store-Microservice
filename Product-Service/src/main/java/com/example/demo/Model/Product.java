package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Locale;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table

public class Product extends BaseModel<String> {


    @JsonIgnore
    @Id
    private String id;


    private String description;

    @Min(value = 0, message = "price must grater than Zero")
    @Max(value = 100000, message = "price must smaller than 100000$")
    @NotNull(message = "Price must be not  Null")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
