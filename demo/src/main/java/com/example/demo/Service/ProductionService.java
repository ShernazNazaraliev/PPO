package com.example.demo.Service;

import com.example.demo.DTO.ProductionDTO;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Material;
import com.example.demo.entity.Product;
import com.example.demo.entity.Production;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductionService {

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Production> findAll() {
        return productionRepository.findAll();
    }

    public void delete(Long id) {
        productionRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(ProductionDTO productionDTO) throws Exception {

        List<Ingredient> ingredientList = ingredientRepository.findAllByProduct_Id(productionDTO.getProductId());

        double sumProduct = 0;
        for (Ingredient i : ingredientList) {
            if (i.getMaterial().getQuantity() - i.getQuantity() * productionDTO.getQuantity() < 0)
                throw new Exception("Не хватает ингредиентов!!! " + i.getMaterial().getName() + ": "
                        + i.getMaterial().getQuantity());
            else {
                Material material = materialRepository.getById(i.getMaterial().getId());
                double sum = material.getPrice() / material.getQuantity() * productionDTO.getQuantity() * i.getQuantity();
                material.setQuantity(material.getQuantity() - i.getQuantity() * productionDTO.getQuantity());
//                double index = productionDTO.getQuantity() * i.getQuantity() * (material.getPrice() / material.getQuantity());
                sumProduct += sum;
                material.setPrice(material.getPrice() - sum);
                materialRepository.save(material);
            }

        }

        Production production = new Production();
        production.setProduct(productRepository.getById(productionDTO.getProductId()));
        production.setEmployee(employeeRepository.getById(productionDTO.getEmployeeId()));
        production.setDateProduction(productionDTO.getDateProduction());
        production.setQuantity(productionDTO.getQuantity());
        productionRepository.save(production);

        Product product = productRepository.getById(productionDTO.getProductId());
        product.setQuantity(product.getQuantity() + productionDTO.getQuantity());
        product.setPrice(product.getPrice() + sumProduct);
        productRepository.save(product);
    }

    public void update(Long id, Production production) {
        Production productionUpdate = productionRepository.getById(id);

        productionUpdate.setDateProduction(production.getDateProduction());
        productionUpdate.setQuantity(production.getQuantity());
        productionUpdate.setProduct(production.getProduct());
        productionUpdate.setEmployee(production.getEmployee());
        productionRepository.save(productionUpdate);
    }
}
