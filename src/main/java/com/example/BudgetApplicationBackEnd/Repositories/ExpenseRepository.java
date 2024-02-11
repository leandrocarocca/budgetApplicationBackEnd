package com.example.BudgetApplicationBackEnd.Repositories;

import com.example.BudgetApplicationBackEnd.Models.Budget;
import com.example.BudgetApplicationBackEnd.Models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public List<Expense> findByBudgetId(Long id);
}
