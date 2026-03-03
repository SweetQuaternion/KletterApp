package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.Ascent;

public interface AscentRepository extends JpaRepository<Ascent, Integer> {
    List<Ascent> findByUserId(int userId);
    List<Ascent> findByRoutenId(int routenId);
    void deleteById(int id);
}