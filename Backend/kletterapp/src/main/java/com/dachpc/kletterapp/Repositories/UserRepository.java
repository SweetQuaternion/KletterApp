package com.dachpc.kletterapp.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    void deleteById(int id);
    
}