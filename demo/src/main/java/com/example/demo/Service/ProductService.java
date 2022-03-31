package com.example.demo.Service;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public void create(Product product){
        productRepository.save(product);
    }

    public void update(Long id, Product product){

        Product productUpdate = productRepository.getById(id);
        productUpdate.setUnit(product.getUnit());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setName(product.getName());
        productUpdate.setQuantity(product.getQuantity());
        productRepository.save(productUpdate);
    }

    public void delete(Long id) throws Exception{
        try {

            productRepository.deleteById(id);
        }
        catch (Exception exception){
            throw new Exception("Конфликт при удалении!");
        }
    }


}
