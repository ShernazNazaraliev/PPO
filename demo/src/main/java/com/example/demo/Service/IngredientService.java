package com.example.demo.Service;

import com.example.demo.DTO.IngredientDTO;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Product;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }

    public void create(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(ingredientDTO.getQuantity());
        ingredient.setProduct(productRepository.findById(ingredientDTO.getProductId()).orElse(null));
        ingredient.setMaterial(materialRepository.findById(ingredientDTO.getMaterialId()).orElse(null));
        ingredientRepository.save(ingredient);

        Product product = productRepository.getById(ingredientDTO.getProductId());
        product.setPrice(product.getPrice() + ingredient.getMaterial().getPrice() / ingredient.getQuantity() * ingredientDTO.getQuantity());
        productRepository.save(product);
    }

    public void update(Long id, Ingredient ingredient) {
        Ingredient ingredientUpdate = ingredientRepository.getById(id);
        ingredientUpdate.setQuantity(ingredient.getQuantity());
        ingredientUpdate.setMaterial(ingredient.getMaterial());
        ingredientUpdate.setProduct(ingredient.getProduct());
        ingredientRepository.save(ingredientUpdate);
    }

    public List<Ingredient> findAllByProductId(Long productId) {
        if (productId == null)
            return ingredientRepository.findAll();
        return ingredientRepository.findAllByProduct_Id(productId);
    }

    public List<Ingredient> findAllByProduct(Product product) {
        if (product == null)
            return ingredientRepository.findAll();
        return ingredientRepository.findAllByProduct_Id(product.getId());
    }
}
