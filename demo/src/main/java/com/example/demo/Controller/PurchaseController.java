package com.example.demo.Controller;

import com.example.demo.DTO.ProductionDTO;
import com.example.demo.DTO.PurchaseDTO;
import com.example.demo.DTO.message;
import com.example.demo.Service.PurchaseService;
import com.example.demo.entity.Production;
import com.example.demo.entity.Purchase;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.ProductionRepository;
import com.example.demo.repository.PurchaseRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchase";
    }

    @RequestMapping(value = "/purchase/edit/{id}", method = RequestMethod.GET)
    public String purchaseEdit(@PathVariable Long id, Model model) {
        model.addAttribute("purchase", purchaseRepository.getById(id));
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("materials", materialRepository.findAll());
        return "purchaseEdit";
    }


    @RequestMapping(value = "/purchase/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String purchaseEdit(@PathVariable Long id, Purchase purchase) {
        purchaseService.update(id, purchase);
        return "redirect:/purchase";
    }



    @RequestMapping(value = "/purchase/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        model.addAttribute("purchase", new PurchaseDTO(sqlDate));
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("materials", materialRepository.findAll());
        return "purchaseCreate";
    }

    @SneakyThrows
    @RequestMapping(value = "/purchase/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(PurchaseDTO purchaseDTO, RedirectAttributes redirectAttrs) {
        try {
            purchaseService.create(purchaseDTO);
            return "redirect:/purchase";
        }
        catch (Exception exception){
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/budgetError";
        }
    }

    @RequestMapping(value = "/purchase/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id) {
        purchaseService.delete(id);
        return "redirect:/purchase";
    }
}
