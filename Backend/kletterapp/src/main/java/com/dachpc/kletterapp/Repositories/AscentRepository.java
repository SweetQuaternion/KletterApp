package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dachpc.kletterapp.Entities.Ascent;

public interface AscentRepository extends JpaRepository<Ascent, Integer> {
    @Query (
        value = """
            SELECT * FROM ascents
            WHERE (:userId IS NULL OR user_id = :userId)
            AND (:routenId IS NULL OR routen_id = :routenId)
        """,
        nativeQuery = true
    )
    List<Ascent> search(int userId, int routenId);
    void deleteById(int id);
}