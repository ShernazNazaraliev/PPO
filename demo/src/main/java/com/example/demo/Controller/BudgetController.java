package com.example.demo.Controller;

import com.example.demo.DTO.Percent;
import com.example.demo.Service.BudgetService;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Product;
import com.example.demo.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetRepository budgetRepository;

    @RequestMapping(value = "/budget",method = RequestMethod.GET)
    public String budget(Model model){
        model.addAttribute("budget", budgetService.getBudget());
        return "budget";
    }

    @RequestMapping(value = "/budgetError",method = RequestMethod.GET)
    public String budgetError(){
        return "budgetError";
    }

    @RequestMapping(value = "/budget/edit/{id}", method = RequestMethod.GET)
    public String budgetEdit(@PathVariable long id, Model model) {
        model.addAttribute("budget", budgetRepository.getById(id));
        return "budgetEdit";
    }

    @RequestMapping(value = "/budget/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String budgetEdit(@PathVariable Long id, Budget budget) {
        System.out.println(budget.toString());
        budgetService.update(id, budget);
        return "redirect:/budget";
    }

}
