package com.example.demo.Controller;

import com.example.demo.Service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @RequestMapping(value = "/salary",method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("salary", salaryService.findAll());
        return "salary";
    }

}
