package com.example.BudgetApplicationBackEnd.Controllers;


import com.example.BudgetApplicationBackEnd.Models.Budget;
import com.example.BudgetApplicationBackEnd.Models.Income;
import com.example.BudgetApplicationBackEnd.Repositories.BudgetRepository;
import com.example.BudgetApplicationBackEnd.Repositories.IncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class IncomeController {
    IncomeRepository incomeRepository;
    private static final Logger log = LoggerFactory.getLogger(IncomeController.class);
    public IncomeController(IncomeRepository incomeRepository){
        this.incomeRepository = incomeRepository;
    }

    @RequestMapping("incomes/hello")
    public String hello(){
        return "hello incomes";
    }

    @GetMapping("incomes")
    public List<Income> getAllIncomes(){
        return incomeRepository.findAll();
    }

    @PostMapping("incomes")
    public Income addIncome(@RequestBody Income income){
        incomeRepository.save(income);
        log.info("Following income added: " + income);
        return income;
    }

    @GetMapping("incomes/{id}")
    public Income getIncomeById(@PathVariable Long id){
        return incomeRepository.findById(id).get();
    }

    @DeleteMapping("incomes/{id}")
    public ResponseEntity<?> deleteIncomeById(@PathVariable Long id){
        log.info("Deleting income with ID: " + id);

        if (!incomeRepository.existsById(id)) {
            log.warn("Income with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        incomeRepository.deleteById(id);

        log.info("Income with ID " + id + " deleted successfully");
        return ResponseEntity.ok().build();
    }

}
