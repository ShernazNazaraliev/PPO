package com.example.demo.Controller;

import com.example.demo.Service.ProductService;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product";
    }

    @RequestMapping(value = "/product/edit/{id}", method = RequestMethod.GET)
    public String productEdit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.getById(id));
        return "ProductEdit";
    }


    @RequestMapping(value = "/product/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String productEdit(@PathVariable Long id, Product product) {
        productService.update(id, product);
        return "redirect:/product";
    }

    @RequestMapping(value = "/product/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        model.addAttribute("product", new Product());
        return "productCreate";
    }

    @RequestMapping(value = "/product/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(Product product) {
        productService.create(product);
        System.out.println(product.toString());
        return "redirect:/product";
    }

    @RequestMapping(value = "/product/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            productService.delete(id);
            return "redirect:/product";

        } catch (Exception exception) {
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/deleteConflict";
        }
    }
}
