package com.example.demo.Service;

import com.example.demo.DTO.EmployeeCreateDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Entity;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void create(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = new Employee();
        employee.setFullName(employeeCreateDTO.getFullName());
        employee.setAddress(employeeCreateDTO.getAddress());
        employee.setPhone(employeeCreateDTO.getPhone());
        employee.setSalary(employeeCreateDTO.getSalary());
        employee.setPosition(positionRepository.getById(employeeCreateDTO.getPosition()));
        employeeRepository.save(employee);
    }

    public void update(Long id, Employee employee) {
        Employee employeeUpdate = employeeRepository.getById(id);
        employeeUpdate.setFullName(employee.getFullName());
        employeeUpdate.setSalary(employee.getSalary());
        employeeUpdate.setPosition(employee.getPosition());
        employeeUpdate.setPhone(employee.getPhone());
        employeeUpdate.setAddress(employee.getAddress());
        employeeRepository.save(employeeUpdate);
    }


    public void delete(Long id) throws Exception {
        try {
            employeeRepository.deleteById(id);
        }
        catch (Exception e){
            throw new Exception("Конфликт при удалении!");
        }
    }

}
