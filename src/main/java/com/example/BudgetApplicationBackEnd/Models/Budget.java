package com.example.BudgetApplicationBackEnd.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnore
    protected List<User> users;

    @OneToMany
    @JoinColumn
    private List<Expense> expenses;

    @OneToMany
    @JoinColumn
    private List<Income> incomes;


    public Budget(){}

    public Budget(long id, String name, List<User> users, List<Expense> expenses, List<Income> incomes) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.incomes = incomes;
        this.expenses = expenses;
    }

    public Budget(String name, List<User> users, List<Expense> expenses, List<Income> incomes) {
        this.name = name;
        this.users = users;
        this.incomes = incomes;
        this.expenses = expenses;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
