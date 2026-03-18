package com.dachpc.kletterapp;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;


public class WandRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private HallenRepository hallenRepository;

    private int hallenId1;
    // private int hallenId2;


    @BeforeEach
    public void setUp() {
        wandRepository.deleteAll();
        hallenRepository.deleteAll();

        Halle halle1 = hallenRepository.save(new Halle("DAV Darmstadt", "Lichtwiesenweg 15", "DAV"));
        // Halle halle2 = hallenRepository.save(new Halle("Heubach", "Adresse", "DAV"));
        hallenId1 = halle1.getId();
        // hallenId2 = halle2.getId();

        // wandRepository.save(new Wand(hallenId1,1,"Sektor in erster Halle"));
        // wandRepository.save(new Wand(hallenId1,2,"Sektor in zweiter Halle"));
        // wandRepository.save(new Wand(hallenId2,1,"Sektor in dritter Halle mit Toprope"));
    }
    
    @Test
    public void testFindByHallenId() {
        List<Wand> result = wandRepository.findByIdHallenId(hallenId1);
        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindByHallenIdNichtGefunden() {
        List<Wand> result = wandRepository.findByIdHallenId(999);
        assertThat(result).isEmpty();
    }

    @Test
    public void testSaveWand() {
        // Wand newWand = new Wand(hallenId1, 3, "Neue Sektor in erster Halle");
        // Wand savedWand = wandRepository.save(newWand);

        // assertThat(savedWand.getId()).isNotNull();
        // assertThat(savedWand.getHallenId()).isEqualTo(hallenId1);
        // assertThat(savedWand.getWandNr()).isEqualTo(3);
        // assertThat(savedWand.getSektor()).isEqualTo("Neue Sektor in erster Halle");
    }

    @Test
    void testDeleteById() {
        // Wand saved = wandRepository.save(new Wand(hallenId1, 4, "Sektor D"));
        // wandRepository.deleteById(saved.getId());
        // assertThat(wandRepository.findById(saved.getId())).isEmpty();
    }
}
