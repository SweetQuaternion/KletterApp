package com.dachpc.kletterapp.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String username);
    Optional<User> findByKeycloakId(String keycloakId);
    boolean existsByKeycloakId(String keycloakId);
    void deleteByKeycloakId(String id);
}