package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseDTO {

    private Long id;

    private Long materialId;

    private double quantity;

    private double price;

    private Date purchaseDate;

    private Long employeeId;

    public PurchaseDTO(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
