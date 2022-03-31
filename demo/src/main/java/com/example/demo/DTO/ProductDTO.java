package com.example.demo.DTO;

import com.example.demo.entity.Enum.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductDTO {

    private Long id;

    private String name;

    private Unit unit;

    private double price;

    private double quantity;
}
