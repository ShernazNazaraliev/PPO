package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductionDTO {

    private Long id;

    private Long productId;

    private double quantity;

    private Date dateProduction;

    private Long employeeId;

    public ProductionDTO(Date dateProduction) {
        this.dateProduction = dateProduction;
    }
}
