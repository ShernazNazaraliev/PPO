package com.example.demo.Controller;

import com.example.demo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/deleteConflict",method = RequestMethod.GET)
    public String deleteConflict() {
        return "deleteConflict";
    }
}
