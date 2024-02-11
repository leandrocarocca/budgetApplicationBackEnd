package com.example.BudgetApplicationBackEnd.Controllers;

import com.example.BudgetApplicationBackEnd.Models.Expense;
import com.example.BudgetApplicationBackEnd.Models.Income;
import com.example.BudgetApplicationBackEnd.Repositories.ExpenseRepository;
import com.example.BudgetApplicationBackEnd.Repositories.IncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ExpenseController {
    ExpenseRepository expenseRepository;
    private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);
    public ExpenseController(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    @RequestMapping("expenses/hello")
    public String hello(){
        return "hello expenses";
    }

    @GetMapping("expenses")
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    @PostMapping("expenses")
    public Expense addExpense(@RequestBody Expense expense){
        expenseRepository.save(expense);
        log.info("Following expense added: " + expense);
        return expense;
    }

    @PutMapping("expenses/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense){
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        existingExpense.setDescription(updatedExpense.getDescription());
        existingExpense.setDueDate(updatedExpense.getDueDate());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setPaid(updatedExpense.isPaid());
        existingExpense.setReminder(updatedExpense.isReminder());
        existingExpense.setPaymentMethod(updatedExpense.getPaymentMethod());

        Expense savedExpense = expenseRepository.save(existingExpense);

        log.info("Following expense updated: " + savedExpense);
        return savedExpense;
    }

    @GetMapping("expenses/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseRepository.findById(id).get();
    }

    @DeleteMapping("expenses/{id}")
    public ResponseEntity<?> deleteExpenseById(@PathVariable Long id){
        log.info("Deleting expense with ID: " + id);

        if (!expenseRepository.existsById(id)) {
            log.warn("Expense with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        expenseRepository.deleteById(id);

        log.info("Expense with ID " + id + " deleted successfully");
        return ResponseEntity.ok().build();
    }

}
