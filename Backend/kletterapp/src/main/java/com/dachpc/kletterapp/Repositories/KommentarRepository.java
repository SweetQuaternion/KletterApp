package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.Kommentar;

public interface KommentarRepository extends JpaRepository<Kommentar, Integer> {

    List<Kommentar> findByUser_keycloakId(int userId);
    List<Kommentar> findByRoute_id(int routenId);
    void deleteById(int id);
}