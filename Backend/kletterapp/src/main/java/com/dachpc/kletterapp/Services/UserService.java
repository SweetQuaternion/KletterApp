package com.dachpc.kletterapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findUser(String id, String username) {
    if (id != null) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User mit id " + id + " nicht gefunden"));
    }
    if (username != null) {
        return userRepository.findByName(username).orElseThrow(() -> new EntityNotFoundException("User mit username " + username + " nicht gefunden"));
    }
    throw new IllegalArgumentException("need either id or username");
    }

    public User syncUser(UserSyncRequest request) {
        User user = userRepository.findByKeycloakId(request.keycloakId()).orElse(null);
        if (user == null) {
            user = userRepository.save(new User(request.keycloakId(), request.name()));
        }
        return user;
    }

    public User updateUser(User updatedUser) {
        User prevUser = userRepository.findByKeycloakId(updatedUser.getKeycloakId()).orElseThrow(() -> new EntityNotFoundException());
        if (updatedUser.getName() != null) prevUser.setName(updatedUser.getName());
        if (updatedUser.getBildUrl() != null) prevUser.setBildUrl(updatedUser.getBildUrl());
        if (updatedUser.getBio() != null) prevUser.setBio(updatedUser.getBio());
        userRepository.save(prevUser);
        return prevUser;
    }

    public void deleteUser(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new EntityNotFoundException());
        userRepository.delete(user);
    }

}
