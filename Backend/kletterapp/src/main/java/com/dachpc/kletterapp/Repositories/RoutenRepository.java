package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.Route;

public interface RoutenRepository extends JpaRepository<Route, Integer> {
    List<Route> findByName(String name);
    List<Route> findBySchwierigkeit(String schwierigkeit);
}
