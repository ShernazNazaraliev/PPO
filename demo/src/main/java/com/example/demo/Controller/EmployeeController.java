package com.example.demo.Controller;

import com.example.demo.DTO.EmployeeCreateDTO;
import com.example.demo.Service.EmployeeService;
import com.example.demo.Service.PositionService;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String employee(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee";
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        model.addAttribute("employee", new EmployeeCreateDTO());
        model.addAttribute("positions", positionService.findAll());
        return "employeeCreate";
    }


    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.GET)
    public String employeeEdit(@PathVariable("id") long id, Model model) {
        model.addAttribute("employeeEdit", employeeRepository.getById(id));
        model.addAttribute("positions", positionService.findAll());
        return "employeeEdit";
    }

    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String employee_create(@PathVariable Long id, Employee employee) {
        employeeService.update(id, employee);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String employee_create(EmployeeCreateDTO employee) {
        employeeService.create(employee);
        System.out.println(employee.toString());
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {

        try {
            employeeService.delete(id);
            return "redirect:/employee";
        }
        catch (Exception exception){
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/deleteConflict";
        }
    }
}
