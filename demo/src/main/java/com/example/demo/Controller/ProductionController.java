package com.example.demo.Controller;

import com.example.demo.DTO.ProductionDTO;
import com.example.demo.Service.ProductionService;
import com.example.demo.entity.Product;
import com.example.demo.entity.Production;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @RequestMapping(value = "/production",method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("productions", productionService.findAll());
        return "production";
    }

    @RequestMapping(value = "/production/edit/{id}", method = RequestMethod.GET)
    public String productionEdit(@PathVariable Long id, Model model) {
        model.addAttribute("production", productionRepository.getById(id));
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "productionEdit";
    }


    @RequestMapping(value = "/production/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String productionEdit(@PathVariable Long id, Production production) {
        productionService.update(id, production);
        return "redirect:/production";
    }


    @RequestMapping(value = "/production/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        model.addAttribute("production", new ProductionDTO(sqlDate));
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "productionCreate";
    }

    @RequestMapping(value = "/production/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(ProductionDTO product, RedirectAttributes redirectAttrs) throws Exception {
        try {
            productionService.create(product);
            return "redirect:/production";
        }
        catch (Exception exception){
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/Error";
        }
    }

    @RequestMapping(value = "/production/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id){
        productionService.delete(id);
        return "redirect:/production";
    }

}
