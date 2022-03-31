package com.example.demo.DTO;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SaleDTO {
    private Long id;

    private Long productId;

    private double quantity;

    private double price;

    private Date dateOfSales;

    private Long employeeId;

    public SaleDTO(Date dateOfSales) {
        this.dateOfSales = dateOfSales;
    }
}
