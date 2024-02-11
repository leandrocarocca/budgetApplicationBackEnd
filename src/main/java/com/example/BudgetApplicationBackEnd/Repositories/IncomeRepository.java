package com.example.BudgetApplicationBackEnd.Repositories;

import com.example.BudgetApplicationBackEnd.Models.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByBudgetId(Long id);
}
