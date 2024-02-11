package com.example.BudgetApplicationBackEnd.Repositories;

import com.example.BudgetApplicationBackEnd.Models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
