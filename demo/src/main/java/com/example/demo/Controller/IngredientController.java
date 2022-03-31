package com.example.demo.Controller;

import com.example.demo.DTO.Filter;
import com.example.demo.DTO.IngredientDTO;
import com.example.demo.Service.IngredientService;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Material;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @RequestMapping(value = "/ingredient", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("filter", new Filter());
        return "ingredient";
    }

    @RequestMapping(value = "/ingredientFilter", method = RequestMethod.GET)
    public String getAllFilter(Model model, Filter filter) {
        model.addAttribute("ingredients", ingredientService.findAllByProductId(filter.getId()));
        model.addAttribute("products", productRepository.findAll());
        return "ingredient";
    }

    @RequestMapping(value = "/ingredient/edit/{id}", method = RequestMethod.GET)
    public String employeeCreate(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.getById(id));
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("materials", materialRepository.findAll());
        return "ingredientEdit";
    }

    @RequestMapping(value = "/ingredient/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(@PathVariable Long id, Ingredient ingredient) {
        ingredientService.update(id, ingredient);
        return "redirect:/ingredient";
    }

    @RequestMapping(value = "/ingredient/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        model.addAttribute("ingredient", new IngredientDTO());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("materials", materialRepository.findAll());
        return "ingredientCreate";
    }

    @RequestMapping(value = "/ingredient/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(IngredientDTO ingredientDTO) {
        ingredientService.create(ingredientDTO);
        System.out.println(ingredientDTO.toString());
        return "redirect:/ingredient";
    }


    @RequestMapping(value = "/ingredient/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id) {
        ingredientService.delete(id);
        return "redirect:/ingredient";
    }

}