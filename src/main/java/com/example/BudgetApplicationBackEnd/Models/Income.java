package com.example.BudgetApplicationBackEnd.Models;

import jakarta.persistence.*;

@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double salaryBeforeTaxes;
    private double charityFactor;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Budget budget;

    public Income(){}

    public Income(User user, Budget budget, String description, double salaryBeforeTaxes, double charityFactor){
        this.user = user;
        this.budget = budget;
        this.description = description;
        this.salaryBeforeTaxes = salaryBeforeTaxes;
        this.charityFactor = charityFactor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSalaryBeforeTaxes() {
        return salaryBeforeTaxes;
    }

    public void setSalaryBeforeTaxes(double salaryBeforeTaxes) {
        this.salaryBeforeTaxes = salaryBeforeTaxes;
    }

    public double getCharityFactor() {
        return charityFactor;
    }

    public void setCharityFactor(double charityFactor) {
        this.charityFactor = charityFactor;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", user=" + user +
                ", budget=" + budget +
                ", description='" + description + '\'' +
                ", salaryBeforeTaxes=" + salaryBeforeTaxes +
                ", charityFactor=" + charityFactor +
                '}';
    }
}
