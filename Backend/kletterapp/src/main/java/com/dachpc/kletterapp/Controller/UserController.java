package com.dachpc.kletterapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<User> get(@RequestParam(required = false) Integer id, @RequestParam(required = false) String email) {
        if (id != null) {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else if (email != null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // TODO 
    // eher login
    // kein get, passt nicht in Rest
    // credentials als Body schicken
    // geht nicht mit Get (normalerweise kein Body)
    // eher standardmäßig POST request
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    // sollte nicht String zurückgeben, sondern den angelegten User
    // möchte die ID ja wieder wissen und was mit dem machen
    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            User newUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    // patch ist eins der Standards um Entitäten zu updaten (partiell)
    // put ersetzt die komplette Entität
    // ändern in Patch
    @PatchMapping("/{id}")
    public ResponseEntity<User> change(@PathVariable int id, @RequestBody User updatedUser) {
        User prevUser = userRepository.findById(id).orElse(null);
        if (prevUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (updatedUser.getName() != null) prevUser.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null) prevUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) prevUser.setPassword(updatedUser.getPassword());
        if (updatedUser.getBildUrl() != null) prevUser.setBildUrl(updatedUser.getBildUrl());
        if (updatedUser.getRole() != null) prevUser.setRole(updatedUser.getRole());
        userRepository.save(prevUser);
        return ResponseEntity.status(HttpStatus.OK).body(prevUser);
    }

    // standardmäßig gibt man hier nichts zurück
    // response status sagt schon alles (200 vs 404)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}