package com.example.demo.Service;

import com.example.demo.DTO.SalaryDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Salary;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Salary> findAll(){
        return salaryRepository.findAll();
    }

    public void create(SalaryDTO salaryDTO) throws Exception {

        Budget budget = budgetRepository.getById(1L);

        Employee employee = employeeRepository.getById(salaryDTO.getEmployee());

        if (budget.getBudget() < employee.getSalary() + (employee.getSalary() / 100 * budget.getBonus())) {
            throw new Exception("Бюджета не хватает!");
        }

        budget.setBudget(budget.getBudget() - employee.getSalary() + (employee.getSalary() / 100 * budget.getBonus()));
        budgetRepository.save(budget);

        Salary salary = new Salary();
        salary.setEmployee(employee);
        salary.setSales(saleRepository.findAllByEmployee_Id(employee.getId()).size());
        salary.setProductions(productionRepository.findAllByEmployee_Id(employee.getId()).size());
        salary.setPurchases(purchaseRepository.findAllByEmployee_Id(employee.getId()).size());
        salary.setFinalSalary(employee.getSalary() * budget.getBonus());
        salary.setMonth(salaryDTO.getMonth());
        salary.setYear(salaryDTO.getYear());
        salary.setPercentSalary(budget.getBonus());
        salaryRepository.save(salary);

    }

}
