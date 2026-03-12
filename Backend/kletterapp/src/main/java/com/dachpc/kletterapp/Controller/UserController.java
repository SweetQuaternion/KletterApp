package com.dachpc.kletterapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    // @GetMapping
    // public ResponseEntity<User> get(@RequestParam(required = false) Integer id, @RequestParam(required = false) String email) {
    //     if (id != null) {
    //         User user = userRepository.findById(id).orElse(null);
    //         if (user == null) {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //         }
    //         return ResponseEntity.status(HttpStatus.OK).body(user);
    //     } else if (email != null) {
    //         User user = userRepository.findByEmail(email).orElse(null);
    //         if (user == null) {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //         }
    //         return ResponseEntity.status(HttpStatus.OK).body(user);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //     }
    // }

    // @GetMapping("/all")
    // public List<User> getAll() {
    //     return userRepository.findAll();
    // }


    // @PostMapping("/login")
    // public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    //     try {
    //         AuthResponse response = userService.login(request);
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     } catch (UsernameNotFoundException e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    //     }
    // }


    // @PostMapping("/register")
    // public ResponseEntity<AuthResponse> add(@RequestBody User user) {
    //     try {
    //         AuthResponse response = userService.register(user);
    //         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    //     }
    // }

    @PostMapping("/register")
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<User> change(@PathVariable String id, @RequestBody User updatedUser) {
        User prevUser = userRepository.findById(id).orElse(null);
        if (prevUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (updatedUser.getName() != null) prevUser.setName(updatedUser.getName());
        if (updatedUser.getBildUrl() != null) prevUser.setBildUrl(updatedUser.getBildUrl());
        userRepository.save(prevUser);
        return ResponseEntity.status(HttpStatus.OK).body(prevUser);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}