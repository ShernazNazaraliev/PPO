package com.example.demo.entity;

import com.example.demo.entity.Enum.Month;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private Month month;

    private String year;

    private double salary;

    private int sales;

    private int productions;

    private int purchases;

    private double percentSalary;

    private double finalSalary;

}
