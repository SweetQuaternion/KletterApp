package com.dachpc.kletterapp.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dachpc.kletterapp.Entities.Halle;

public interface HallenRepository extends JpaRepository<Halle, Long> {
    List<Halle> findByName(String name);
}
