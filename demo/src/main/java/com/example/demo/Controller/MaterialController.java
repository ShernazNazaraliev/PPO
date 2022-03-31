package com.example.demo.Controller;

import com.example.demo.Service.MaterialService;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialRepository materialRepository;

    @RequestMapping(value = "/material", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("materials", materialService.findAll());
        return "material";
    }

    @RequestMapping(value = "/material/edit/{id}", method = RequestMethod.GET)
    public String materialEdit(@PathVariable Long id, Model model) {
        model.addAttribute("material", materialRepository.getById(id));
        return "materialUpdate";
    }

    @RequestMapping(value = "/material/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String materialEdit(@PathVariable Long id, Material material) {
        materialService.update(id, material);
        return "redirect:/material";
    }

    @RequestMapping(value = "/material/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        model.addAttribute("material", new Material());
        return "materialCreate";
    }

    @RequestMapping(value = "/material/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(Material material) {
        materialService.create(material);
        System.out.println(material.toString());
        return "redirect:/material";
    }


    @RequestMapping(value = "/material/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            materialService.delete(id);
            return "redirect:/material";

        } catch (Exception exception) {
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/deleteConflict";
        }
    }

}
