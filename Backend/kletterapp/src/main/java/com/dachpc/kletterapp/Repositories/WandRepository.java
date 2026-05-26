package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dachpc.kletterapp.Entities.Wand;

public interface WandRepository extends JpaRepository<Wand, Integer> {

    List<Wand> findByHallenId(int hallenId);
    @Query("SELECT w FROM Wand w LEFT JOIN FETCH w.routen WHERE w.hallenId = :hallenId")
    List<Wand> findByHallenIdWithRouten(@Param("hallenId") int hallenId);
    void deleteById(int id);
    void deleteByHallenId(int hallenId);
}