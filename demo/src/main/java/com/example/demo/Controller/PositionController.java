package com.example.demo.Controller;

import com.example.demo.DTO.ExceptionDTO;
import com.example.demo.Service.PositionService;
import com.example.demo.entity.Material;
import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ExceptionController exceptionController;

    @RequestMapping(value = "/position", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("positions", positionService.findAll());
        return "position";
    }

    @RequestMapping(value = "/position/edit/{id}", method = RequestMethod.GET)
    public String positionEdit(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionRepository.getById(id));
        return "PositionEdit";
    }

    @RequestMapping(value = "/position/edit/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String positionEdit(@PathVariable Long id, Position position) {
        positionService.update(id, position);
        return "redirect:/position";
    }


    @RequestMapping(value = "/position/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        model.addAttribute("position", new Position());
        return "positionCreate";
    }

    @RequestMapping(value = "/position/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String material_create(Position position) {
        positionService.create(position);
        System.out.println(position.toString());
        return "redirect:/position";
    }

    @RequestMapping(value = "/position/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id , RedirectAttributes redirectAttrs) throws Exception {
        try {
            positionService.delete(id);
            return "redirect:/position";
        }
        catch (Exception exception){
            redirectAttrs.addAttribute("message", exception.getMessage());
            return "redirect:/deleteConflict";
        }
    }


}
