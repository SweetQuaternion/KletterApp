package com.dachpc.kletterapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<User> get(@RequestParam String id) {
        User user = userRepository.findByKeycloakId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    

    @PostMapping
    public ResponseEntity<User> sync(@RequestBody UserSyncRequest request) {
        User user = null;
        if (!userRepository.existsByKeycloakId(request.keycloakId())) { // User noch nicht in DB, müssen wir anlegen
            user = userRepository.save(new User(request.keycloakId(), request.name()));
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
            
        } else {
            user = userRepository.findByKeycloakId(request.keycloakId()).orElse(null); // User ist schon in DB, holen wir ihn einfach raus
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @PatchMapping
    public ResponseEntity<User> change(@RequestBody User updatedUser) {
        User prevUser = userRepository.findByKeycloakId(updatedUser.getKeycloakId()).orElse(null);
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
        if (!userRepository.existsByKeycloakId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userRepository.deleteByKeycloakId(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}