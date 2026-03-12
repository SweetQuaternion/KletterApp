package com.dachpc.kletterapp;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;


public class RoutenRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private WandRepository wandRepository;


    @BeforeEach
    public void setUp() {
        routenRepository.deleteAll();
        wandRepository.deleteAll();
        Wand wand = wandRepository.save(new Wand(1,1,"Sektor in erster Halle"));
        Wand wand2 = wandRepository.save(new Wand(2,1,"Sektor in zweiter Halle"));
        Wand wand3 = wandRepository.save(new Wand(3,1,"Sektor in dritter Halle mit Toprope"));
        int wandId1 = wand.getId();
        int wandId2 = wand2.getId();
        int wandId3 = wand3.getId();

        // legt alle Routen in der gleichen Halle an
        routenRepository.save(new Route(wandId1, "Route 1", "rot", 5.3f));
        routenRepository.save(new Route(wandId1, "Route 2", "blau", 6.3f));
        routenRepository.save(new Route(wandId1, "Route 3", "grün", 7f));
        routenRepository.save(new Route(wandId1, "Route 4", "gelb", 8.3f));
        // andere Halle
        routenRepository.save(new Route(wandId2, "Route 5", "schwarz", 6.3f));
        routenRepository.save(new Route(wandId2, "Route 6", "weiß", 7.3f));
        routenRepository.save(new Route(wandId2, "Route 7", "orange", 8.3f));
        // dritte Halle
        routenRepository.save(new Route(wandId3, "Route 8", "lila", 6f, true, false, null, null, true, null));
        routenRepository.save(new Route(wandId3, "Route 9", "orange", 8.3f));
        
    }

    @Test
    public void testFilter() {
        List<Route> result = routenRepository.filter(1, 6f, 8f, null, null, true);
        assertThat(result).hasSize(2);
    }

    @Test
    public void testFilterByHalle() {
        List<Route> result1 = routenRepository.filter(1, 0f, 20f, null, null, null);
        assertThat(result1).hasSize(4);
        List<Route> result2 = routenRepository.filter(2, 0f, 20f, null, null, null);
        assertThat(result2).hasSize(3);
    }

    @Test
    public void testFilterNotFound() {
        List<Route> result = routenRepository.filter(1, 9f, 7f, null, null, true);
        assertThat(result).isEmpty();
    }

    @Test
    public void testFilterNurToprope() {
        List<Route> result1 = routenRepository.filter(1, 0f, 99f, true, null, null);
        List<Route> result2 = routenRepository.filter(3, 0f, 99f, true, null, null);
        assertThat(result1).hasSize(0);
        assertThat(result2).hasSize(1);
    }

    @Test
    public void testFilterNurInaktiv() {
        List<Route> result = routenRepository.filter(1, 0f, 99f, null, null, false);
        assertThat(result).isEmpty();
    }
}
