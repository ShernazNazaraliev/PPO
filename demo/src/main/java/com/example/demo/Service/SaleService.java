package com.example.demo.Service;

import com.example.demo.DTO.SaleDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.repository.BudgetRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public void delete(Long id) {
        saleRepository.deleteById(id);
    }

    @Transactional
    public void create(SaleDTO saleDTO) throws Exception {
        try {
            if (productRepository.getById(saleDTO.getProductId()).getQuantity() - saleDTO.getQuantity() < 0)
                throw new Exception("Продукт не хватает!!!");


            Budget budget = budgetRepository.getById(1L);

            Sale sale = new Sale();
            sale.setProduct(productRepository.getById(saleDTO.getProductId()));
            sale.setEmployee(employeeRepository.getById(saleDTO.getEmployeeId()));
            sale.setQuantity(saleDTO.getQuantity());
            sale.setDateOfSales(saleDTO.getDateOfSales());


            Product product = productRepository.getById(saleDTO.getProductId());
            product.setPrice(product.getPrice() - ((product.getPrice() / (product.getQuantity())) * saleDTO.getQuantity()));
            product.setQuantity(product.getQuantity() - saleDTO.getQuantity());
            sale.setPrice(product.getPrice() / product.getQuantity() * saleDTO.getQuantity() * (1 + budget.getAdditive_percentage() / 100));
            productRepository.save(product);


            double product_sale_quantity = product.getPrice() / product.getQuantity() * saleDTO.getQuantity() * (1 + budget.getAdditive_percentage() / 100);

            budget.setBudget(budget.getBudget() + product_sale_quantity);
            budgetRepository.save(budget);


            sale.setPrice(product_sale_quantity);
            saleRepository.save(sale);
        } catch (Exception exception) {
            throw new Exception("Ошибка!");
        }

    }

    public void update(Long id, Sale sale) {
        Sale saleUpdate = saleRepository.getById(id);
        saleUpdate.setDateOfSales(sale.getDateOfSales());
        saleUpdate.setPrice(sale.getPrice());
        saleUpdate.setEmployee(sale.getEmployee());
        saleUpdate.setProduct(sale.getProduct());
        saleUpdate.setQuantity(sale.getQuantity());
        saleRepository.save(saleUpdate);
    }
}
