package com.dachpc.kletterapp.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);
    // Optional<User> findByEmail(String email);

    // boolean existsByEmail(String email);

    void deleteById(String id);
}