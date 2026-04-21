package com.dachpc.kletterapp.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dachpc.kletterapp.Entities.Halle;

public interface HallenRepository extends JpaRepository<Halle, Integer> {
    @Query(
        value = """
            SELECT *
            FROM hallen
            WHERE similarity (name, ?1) > 0.2
            OR similarity (adresse, ?1) > 0.2
            OR similarity (betreiber, ?1) > 0.2
            OR name ILIKE CONCAT('%', ?1, '%')
            OR adresse ILIKE CONCAT('%', ?1, '%')
            OR betreiber ILIKE CONCAT('%', ?1, '%')
            ORDER BY GREATEST(similarity(name || ' ' || adresse, ?1), similarity(name, ?1), similarity(adresse, ?1), similarity(betreiber, ?1)) DESC
            LIMIT 3
        """,
        nativeQuery = true
    )
    List<Halle> search(String search);
    List<Halle> findAll();
    Optional<Halle> findById(int id);
    void deleteById(int id);
}
