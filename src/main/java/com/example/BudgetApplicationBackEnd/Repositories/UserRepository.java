package com.example.BudgetApplicationBackEnd.Repositories;

import com.example.BudgetApplicationBackEnd.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
