package com.example.BudgetApplicationBackEnd.Controllers;

import com.example.BudgetApplicationBackEnd.Models.Budget;
import com.example.BudgetApplicationBackEnd.Models.Income;
import com.example.BudgetApplicationBackEnd.Models.User;
import com.example.BudgetApplicationBackEnd.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping("users/hello")
    public String hello(){
        return "hello users";
    }

    @GetMapping("users")
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        log.info("Users fetched: " + users);
        return users;
    }

    @PostMapping("users")
    public List<User> addUser(@RequestBody User user){
        userRepository.save(user);
        log.info("Following user added: " + user);
        return userRepository.findAll();
    }

    @GetMapping("users/{userId}/budgets")
    public List<Budget> getAllBudgetsByUserId(@PathVariable Long userId){
        User user = userRepository.findById(userId).get();
        log.info("Budget: " + user.getBudgets());
        return user.getBudgets();
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    @PutMapping("users/{id}")
    public User updateUser(@PathVariable Long id, User updatedUser){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setBudgets(updatedUser.getBudgets());
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setPassword(updatedUser.getPassword());

        User savedUser = userRepository.save(existingUser);

        log.info("Following user updated: " + savedUser);
        return savedUser;
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        log.info("Deleting user with ID: " + id);

        if (!userRepository.existsById(id)) {
            log.warn("User with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);

        log.info("User with ID " + id + " deleted successfully");
        return ResponseEntity.ok().build();
    }
}

