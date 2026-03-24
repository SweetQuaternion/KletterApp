package com.dachpc.kletterapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // kann ungesichert bleiben, da wir nur die ID, Username und Profilbild abfragen und keine sensiblen Daten zurückgeben
    // User sollen einander sehen können
    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String id) {
        User user = userRepository.findByKeycloakId(id).orElseThrow(() -> new EntityNotFoundException());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    
    // müssen wir absichern, damit sich User nur selbst synchronisieren / hinzufügen können (oder Admins),
    // an diesem Punkt wurde das Token schon geprüft, ist also gültig, darum müssen wir uns hier nicht kümmern
    // man will das Token eigentlich nie in die Hand nehmen
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or #request.keycloakId == authentication.principal.subject")
    public ResponseEntity<User> syncUser(@RequestBody UserSyncRequest request) { 
        User user = userRepository.findByKeycloakId(request.keycloakId()).orElse(null);
        if (user == null) {
            user = userRepository.save(new User(request.keycloakId(), request.name()));
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    // müssen wir absichern, damit nur der User selbst seine Daten ändern kann (oder Admins)
    // an diesem Punkt wurde aber das Token schon geprüft, ist also gültig, darum müssen wir uns hier nicht kümmern
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #request.keycloakId == authentication.principal.subject")
    public User changeUser(@RequestBody User updatedUser) {
        System.out.println("Received update request for user:" + updatedUser.toString());
        User prevUser = userRepository.findByKeycloakId(updatedUser.getKeycloakId()).orElseThrow(() -> new EntityNotFoundException());
        if (updatedUser.getName() != null) prevUser.setName(updatedUser.getName());
        if (updatedUser.getBildUrl() != null) prevUser.setBildUrl(updatedUser.getBildUrl());
        userRepository.save(prevUser);
        return prevUser;
    }
    
    // müssen wir absichern, damit nur der User selbst seine Daten löschen kann (oder Admins)
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #request.keycloakId == authentication.principal.subject")
    public void deleteUser(@RequestParam String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new EntityNotFoundException());
        userRepository.delete(user);
    }
    
}