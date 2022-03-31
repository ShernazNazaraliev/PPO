package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class IngredientDTO {
    private Long id;

    private Long productId;

    private Long materialId;

    private double quantity;
}
