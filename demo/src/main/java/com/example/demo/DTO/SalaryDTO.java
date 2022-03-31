package com.example.demo.DTO;
import com.example.demo.entity.Enum.Month;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SalaryDTO {

    private Long id;

    private Long employee;

    private Month month;

    private String year;

    private double salary;

    private int activity;

    private double percentSalary;

    private double finalSalary;

}
