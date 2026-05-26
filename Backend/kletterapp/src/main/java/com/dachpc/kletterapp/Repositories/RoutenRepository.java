package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.Route;

public interface RoutenRepository extends JpaRepository<Route, Integer> {
    List<Route> findByWand_HallenId(int hallenId);

    // @Query(
    //     value = """
    //         SELECT * FROM 
    //             (SELECT routen.id, wand_id, name , farbe, schwierigkeit , is_toprope, is_vorstieg, schrauber, schraubdatum, is_active, beschreibung 
    //             FROM routen JOIN wände on routen.wand_id = wände.id
    //             WHERE wände.hallen_id = ?1
    //         )
    //         WHERE (?2 IS NULL OR schwierigkeit >= ?2) AND
    //         (?3 IS NULL OR schwierigkeit <= ?3) AND
    //         (?4 IS NULL OR is_toprope = ?4) AND
    //         (?5 IS NULL OR is_vorstieg = ?5) AND
    //         (?6 IS NULL OR is_active = ?6)
    //     """,
    //     nativeQuery = true
    // )
    // List<Route> filter(int hallenID, Float minGrade, Float maxGrade, Boolean isToprope, Boolean isVorstieg, Boolean isActive);
}
