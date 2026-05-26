package com.dachpc.kletterapp;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;


public class RoutenRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private HallenRepository hallenRepository;

    int hallenId1;
    int hallenId2;
    Wand wand1;
    Wand wand2;
    Wand wand3;

    @BeforeEach
    public void setUp() {
        routenRepository.deleteAll();
        wandRepository.deleteAll();

        Halle halle1 = hallenRepository.save(new Halle("Test Halle", "Test Adresse", "Test Betreiber"));
        hallenId1 = halle1.getId();

        Halle halle2 = hallenRepository.save(new Halle("Test Halle", "Test Adresse", "Test Betreiber"));
        hallenId2 = halle2.getId();
        
        Wand wand1 = new Wand();
        wand1 = wandRepository.save(wand1);

        Wand wand2 = new Wand();
        wand2 = wandRepository.save(wand2);

        Wand wand3 = new Wand();
        wand3 = wandRepository.save(wand3);

        // legt alle Routen in der gleichen Halle an
        routenRepository.save(new Route(wand1, "Route 1", "Rot", 5.10f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        routenRepository.save(new Route(wand1, "Route 2", "Blau", 5.11f, false, true, "Schrauber 2", LocalDate.now(), true, "Beschreibung 2"));
        routenRepository.save(new Route(wand2, "Route 3", "Grün", 5.12f, true, false, "Schrauber 3", LocalDate.now(), true, "Beschreibung 3"));
        routenRepository.save(new Route(wand2, "Route 4", "Gelb", 5.13f, false, true, "Schrauber 4", LocalDate.now(), true, "Beschreibung 4"));
        // andere Halle
        routenRepository.save(new Route(wand3, "Route 5", "Rot", 5.10f, true, false, "Schrauber 5", LocalDate.now(), true, "Beschreibung 5"));
        routenRepository.save(new Route(wand3, "Route 6", "Blau", 5.11f, false, true, "Schrauber 6", LocalDate.now(), true, "Beschreibung 6"));
    }


    @Test
    public void testFilterByHalle() {
        List<Route> result1 = routenRepository.findByWand_HallenId(hallenId1);
        assertThat(result1).hasSize(4);
        List<Route> result2 = routenRepository.findByWand_HallenId(hallenId2);
        assertThat(result2).hasSize(2);
    }

    @Test
    public void testFilterNotFound() {
        List<Route> result = routenRepository.findByWand_HallenId(3);
        assertThat(result).isEmpty();
    }

}
