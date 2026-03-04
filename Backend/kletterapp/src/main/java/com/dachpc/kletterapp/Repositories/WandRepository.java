package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.Wand;

public interface WandRepository extends JpaRepository<Wand, Integer> {
    List<Wand> findByHallenId(int hallenId);

    // List<Wand> findByHallenIdAndWandNr(int hallenId, int wandNr);
    // List<Wand> findBySektor(String sektor);
    void deleteById(int id);
}