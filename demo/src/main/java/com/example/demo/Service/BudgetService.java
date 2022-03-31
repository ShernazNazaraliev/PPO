package com.example.demo.Service;

import com.example.demo.DTO.Percent;
import com.example.demo.entity.Budget;
import com.example.demo.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget getBudget(){
        return budgetRepository.getById(1L);
    }

    public Budget getById(Long id){
        return budgetRepository.getById(id);
    }

    public void update(Long id, Budget budget) {
        Budget budgetUpdate = budgetRepository.getById(id);

        budgetUpdate.setBudget(budget.getBudget());
        budgetUpdate.setAdditive_percentage(budget.getAdditive_percentage());
        budgetRepository.save(budgetUpdate);
    }
}
