package com.example.demo.Controller;

import com.example.demo.DTO.PurchaseDTO;
import com.example.demo.DTO.SaleDTO;
import com.example.demo.Service.SaleService;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.Sale;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;

@Controller
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/sale",method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("sales", saleService.findAll());
        return "sale";
    }

    @RequestMapping(value = "/sale/edit/{id}", method = RequestMethod.GET)
    public String saleEdit(@PathVariable Long id, Model model) {
        model.addAttribute("sale", saleRepository.getById(id));
        model.addAttribute("employees",employeeRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "saleEdit";
    }


    @RequestMapping(value = "/sale/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String saleEdit(@PathVariable Long id, Sale sale) {
        saleService.update(id, sale);
        return "redirect:/sale";
    }

    @RequestMapping(value = "/sale/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        model.addAttribute("sale", new SaleDTO(sqlDate));
        model.addAttribute("employees",employeeRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "saleCreate";
    }

    @RequestMapping(value = "/sale/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(SaleDTO saleDTO, RedirectAttributes redirectAttrs) throws Exception {
        try {
            saleService.create(saleDTO);
            return "redirect:/sale";
        }
        catch (Exception exception){
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/Error";
        }
    }

    @RequestMapping(value = "/sale/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id){
        saleService.delete(id);
        return "redirect:/sale";
    }
}
