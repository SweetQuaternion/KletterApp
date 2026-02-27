package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dachpc.kletterapp.Entities.Route;

public interface RoutenRepository extends JpaRepository<Route, Integer> {
    List<Route> findByName(String name);
    List<Route> findBySchwierigkeit(String schwierigkeit);

    List<Route> findByHalle(String halle);

    List<Route> findBySicherungsart(String sicherungsart);


    // @Query(
    //     value = """
    //         SELECT *
    //         FROM routen
    //         WHERE schwierigkeit 
    //     """,
    //     nativeQuery = true
    // )
    // List<Route> find();
}
