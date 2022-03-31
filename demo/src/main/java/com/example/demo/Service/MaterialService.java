package com.example.demo.Service;

import com.example.demo.entity.Material;
import com.example.demo.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;


    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public void create(Material material) {
        materialRepository.save(material);
    }

    public void update(Long id, Material material) {
        Material materialUpdate = materialRepository.getById(id);
        materialUpdate.setPrice(material.getPrice());
        materialUpdate.setQuantity(material.getQuantity());
        materialUpdate.setName(material.getName());
        materialUpdate.setUnit(material.getUnit());
        materialRepository.save(materialUpdate);
    }

    public void delete(Long id) throws Exception {
        try {
            materialRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Конфликт при удалении");
        }
    }

}
