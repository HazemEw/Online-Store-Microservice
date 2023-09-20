package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@Table
@NoArgsConstructor
public class Category extends BaseModel<String> {

    @JsonIgnore
    @Id
    private String id;

    private String description;
}
