package com.example.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeCreateDTO {
    private Long id;
    private String fullName;
    private double salary;
    private String address;
    private String phone;
    private Long position;
}
