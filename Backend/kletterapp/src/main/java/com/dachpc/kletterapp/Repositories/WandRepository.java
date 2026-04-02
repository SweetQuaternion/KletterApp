package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;

public interface WandRepository extends JpaRepository<Wand, WandId> {

    Wand getReferenceById(WandId id);
    List<Wand> findByIdHallenId(int hallenId);
    @Query("SELECT w FROM Wand w LEFT JOIN FETCH w.routen WHERE w.id.hallenId = :hallenId")
    List<Wand> findByHallenIdWithRouten(@Param("hallenId") int hallenId);
    void deleteById(WandId id);
}