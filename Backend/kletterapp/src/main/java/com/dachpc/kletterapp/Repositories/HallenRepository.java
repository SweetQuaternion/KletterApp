package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dachpc.kletterapp.Entities.Halle;

public interface HallenRepository extends JpaRepository<Halle, Integer> {
    @Query(
        value = """
            SELECT *
            FROM hallen
            WHERE similarity(name || ' ' || adresse, ?1) > 0.2
            OR (name || ' ' || adresse) ILIKE CONCAT('%', ?1, '%')
            ORDER BY similarity(name || ' ' || adresse, ?1) DESC
            LIMIT 5
        """,
        nativeQuery = true
    )
    List<Halle> fuzzySearch(String search);
}
