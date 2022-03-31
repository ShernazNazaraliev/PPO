package com.example.demo.Service;

import com.example.demo.DTO.PurchaseDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Material;
import com.example.demo.entity.Purchase;
import com.example.demo.repository.BudgetRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public void delete(Long id) {
        purchaseRepository.deleteById(id);
    }

    @Transactional
    public void create(PurchaseDTO purchaseDTO) throws Exception {

        if (purchaseDTO.getQuantity() * purchaseDTO.getPrice() > budgetRepository.getById(1L).getBudget())
            throw new Exception("Не хватает Бюджета!!! " + "\n     Бюджет : " + budgetRepository.getById(1L).getBudget() +
                    " \n      Сумма закупки: " + (purchaseDTO.getQuantity() * purchaseDTO.getPrice()));

        Purchase purchase = new Purchase();
        purchase.setEmployee(employeeRepository.getById(purchaseDTO.getEmployeeId()));
        purchase.setQuantity(purchaseDTO.getQuantity());
        purchase.setMaterial(materialRepository.getById(purchaseDTO.getMaterialId()));
        purchase.setPrice(purchaseDTO.getPrice());
        purchase.setPurchaseDate(purchaseDTO.getPurchaseDate());
        purchaseRepository.save(purchase);

        Budget budget = budgetRepository.getById(1L);
        budget.setBudget(budget.getBudget() - purchaseDTO.getQuantity() * purchaseDTO.getPrice());
        budgetRepository.save(budget);

        Material material = materialRepository.getById(purchaseDTO.getMaterialId());
        material.setQuantity(material.getQuantity() + purchaseDTO.getQuantity());
        material.setPrice(material.getPrice() + purchaseDTO.getPrice() * purchaseDTO.getQuantity());
        materialRepository.save(material);

    }

    public void update(Long id, Purchase purchase) {
        Purchase purchaseUpdate = purchaseRepository.getById(id);

        purchaseUpdate.setPurchaseDate(purchase.getPurchaseDate());
        purchaseUpdate.setPrice(purchase.getPrice());
        purchaseUpdate.setQuantity(purchase.getQuantity());
        purchaseUpdate.setEmployee(purchase.getEmployee());
        purchaseUpdate.setMaterial(purchase.getMaterial());
        purchaseRepository.save(purchaseUpdate);
    }
}
