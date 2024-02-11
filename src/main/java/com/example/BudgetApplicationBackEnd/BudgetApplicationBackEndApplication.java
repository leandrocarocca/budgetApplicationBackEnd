package com.example.BudgetApplicationBackEnd;

import com.example.BudgetApplicationBackEnd.Models.Budget;
import com.example.BudgetApplicationBackEnd.Models.Expense;
import com.example.BudgetApplicationBackEnd.Models.Income;
import com.example.BudgetApplicationBackEnd.Models.User;
import com.example.BudgetApplicationBackEnd.Repositories.BudgetRepository;
import com.example.BudgetApplicationBackEnd.Repositories.ExpenseRepository;
import com.example.BudgetApplicationBackEnd.Repositories.IncomeRepository;
import com.example.BudgetApplicationBackEnd.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BudgetApplicationBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetApplicationBackEndApplication.class, args);
	}

	@Bean
    public CommandLineRunner cmr(BudgetRepository budgetRepository, ExpenseRepository expenseRepository, IncomeRepository incomeRepository, UserRepository userRepository){
        return (args -> {
			User user1 = new User("l", "d", new ArrayList<>());
			User user2 = new User("Emma", "Pemma", new ArrayList<>());

			Budget budget1 = new Budget("Carocca Budget", List.of(user1, user2), new ArrayList<>(), new ArrayList<>());
			user1.setBudgets(List.of(budget1));
			user2.setBudgets(List.of(budget1));
			Income income1 = new Income(user1, budget1, "Leandros lön", 40000, 0.1);
			Income income2 = new Income(user2, budget1, "Emmas lön", 25700, 0.1);
			Expense expense1 = new Expense(user1, budget1, "Rent", "2024-01-29", "Household expenses", 9000, false, false, "Invoice");
			Expense expense2 = new Expense(user2, budget1, "Mat", "2024-01-29", "Household expenses", 4000, false, false, "Invoice");
			budget1.setIncomes(List.of(income1, income2));
			budget1.setExpenses(List.of(expense1, expense2));
			userRepository.save(user1);
			userRepository.save(user2);
			budgetRepository.save(budget1);
			incomeRepository.save(income1);
			incomeRepository.save(income2);
			expenseRepository.save(expense1);
			expenseRepository.save(expense2);



        });
    }
}
