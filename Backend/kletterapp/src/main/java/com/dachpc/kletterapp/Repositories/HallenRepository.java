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
            OR similarity (name, ?1) > 0.2
            OR similarity (adresse, ?1) > 0.2
            OR similarity (betreiber, ?1) > 0.2
            ORDER BY GREATEST(similarity(name || ' ' || adresse, ?1), similarity(name, ?1), similarity(adresse, ?1), similarity(betreiber, ?1)) DESC
            LIMIT 3
        """,
        nativeQuery = true
    )
    List<Halle> fuzzySearch(String search);
}
