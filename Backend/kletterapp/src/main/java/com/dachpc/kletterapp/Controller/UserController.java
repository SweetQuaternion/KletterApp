package com.dachpc.kletterapp.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;



@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/id/{id}")
    public Optional<User> getById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping("/email/{email}")
    public Optional<User> getByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }

    @GetMapping("/einloggen")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> change(@PathVariable int id, @RequestBody User updatedUser) {
        User prevUser = userRepository.findById(id).orElse(null);
        if (prevUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if (updatedUser.getName() != null) prevUser.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null) prevUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) prevUser.setPassword(updatedUser.getPassword());
        if (updatedUser.getBildUrl() != null) prevUser.setBildUrl(updatedUser.getBildUrl());
        if (updatedUser.getRole() != null) prevUser.setRole(updatedUser.getRole());
        userRepository.save(prevUser);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
    
}