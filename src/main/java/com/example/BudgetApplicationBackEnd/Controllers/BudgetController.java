package com.example.BudgetApplicationBackEnd.Controllers;

import com.example.BudgetApplicationBackEnd.Models.Budget;
import com.example.BudgetApplicationBackEnd.Models.Expense;
import com.example.BudgetApplicationBackEnd.Models.Income;
import com.example.BudgetApplicationBackEnd.Models.User;
import com.example.BudgetApplicationBackEnd.Repositories.BudgetRepository;
import com.example.BudgetApplicationBackEnd.Repositories.ExpenseRepository;
import com.example.BudgetApplicationBackEnd.Repositories.IncomeRepository;
import com.example.BudgetApplicationBackEnd.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin("*")
public class BudgetController {
    BudgetRepository budgetRepository;
    UserRepository userRepository;
    ExpenseRepository expenseRepository;
    IncomeRepository incomeRepository;
    private static final Logger log = LoggerFactory.getLogger(BudgetController.class);

    public BudgetController(BudgetRepository budgetRepository, UserRepository userRepository, ExpenseRepository expenseRepository, IncomeRepository incomeRepository){
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    @GetMapping("budgets")
    public List<Budget> getAllBudgets(){
        return budgetRepository.findAll();
    }

    @PostMapping("budgets/user/{userId}")
    public Budget addBudget(@RequestBody Budget budget, @PathVariable Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            log.warn("User with ID: " + userId + " was not found");
            return null;
        }
        User user = userOptional.get();
        List<Budget> currentBudgets = user.getBudgets();
        currentBudgets.add(budget);
        user.setBudgets(currentBudgets);
        budget.setUsers(List.of(user));
        userRepository.save(user);
        budgetRepository.save(budget);
        log.info("Following budget added: " + budget + "\n" + "with following user: " + user);
        return budget;
    }

    @GetMapping("budgets/{id}")
    public Budget getBudgetById(@PathVariable Long id){
        Optional<Budget> budgetOptional = budgetRepository.findById(id);
        if(budgetOptional.isEmpty()){
            log.warn("User with ID: " + id + " was not found");
            return null;
        }
        return budgetOptional.get();
    }

    @PutMapping("budgets/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget updatedBudget){
        Budget existingBudget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
        existingBudget.setUsers(updatedBudget.getUsers());
        existingBudget.setName(updatedBudget.getName());
        existingBudget.setIncomes(updatedBudget.getIncomes());
        existingBudget.setExpenses(updatedBudget.getExpenses());

        Budget savedBudget = budgetRepository.save(existingBudget);

        log.info("Following expense updated: " + savedBudget);
        return savedBudget;
    }

    @DeleteMapping("budgets/{id}")
    public ResponseEntity<?> deleteBudgetById(@PathVariable Long id) {
        log.info("Deleting budget with ID: " + id);

        Optional<Budget> budgetOptional = budgetRepository.findById(id);
        if (budgetOptional.isEmpty()) {
            log.warn("Budget with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        Budget budget = budgetOptional.get();
        log.info("The budget: " + budget);
        // Remove the budget from associated users
        List<User> budgetUsers = budget.getUsers();
        log.info("BudgetUsers: " + budgetUsers);
        budgetUsers.forEach(user -> user.getBudgets().remove(budget));
        log.info("BudgetUsers after: " + budgetUsers);
        userRepository.saveAll(budgetUsers);

        log.info("getUsers: " + budget.getUsers());

        // Clear associations from expenses and incomes
        List<Expense> budgetExpenses = expenseRepository.findByBudgetId(budget.getId());
        budgetExpenses.forEach(expense -> {
            expense.setUser(null);
            expense.setBudget(null);
        });
        expenseRepository.saveAll(budgetExpenses);
        expenseRepository.deleteAll(budgetExpenses);


        List<Income> budgetIncomes = incomeRepository.findByBudgetId(budget.getId());
        budgetIncomes.forEach(income -> {
            income.setUser(null);
            income.setBudget(null);
        });
        incomeRepository.saveAll(budgetIncomes);
        incomeRepository.deleteAll(budgetIncomes);

        // Now delete the budget
        log.info("BUDGET: " + budgetRepository.findById(id));
        Budget deletedBudget = budgetRepository.findById(id).get();
        budgetRepository.deleteById(id);

        log.info("Budget: " + deletedBudget+ " was deleted successfully");
        return ResponseEntity.ok().build();
    }



}
