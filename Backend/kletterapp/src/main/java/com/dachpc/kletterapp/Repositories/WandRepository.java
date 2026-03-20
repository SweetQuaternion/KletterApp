package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;

public interface WandRepository extends JpaRepository<Wand, WandId> {
    List<Wand> findByIdHallenId(int hallenId);
    // N+1 query problem: findByIdHallenId lädt die Wände, aber nicht die Routen, die zu den Wänden gehören
    // die lädt es dann separat für jede Wand -> sehr viele langsame Queries

    @Query("SELECT w FROM Wand w LEFT JOIN FETCH w.routen WHERE w.id.hallenId = :hallenId")
    List<Wand> findByHallenIdWithRouten(@Param("hallenId") int hallenId);
    // nur eine Query
    // join fetch ist jakarta-spezifisch und mappt die Routen direkt in die Wände, cool

    void deleteById(WandId id);
}