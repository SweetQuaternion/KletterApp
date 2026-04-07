package com.dachpc.kletterapp;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Dtos.WandCreateDTO;
import com.dachpc.kletterapp.Dtos.WandResponseDTO;
import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;
import com.dachpc.kletterapp.Services.WandService;


public class WandRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private WandService wandService;

    @Autowired
    private HallenRepository hallenRepository;

    private int hallenId1;
    private int hallenId2;


    @BeforeEach
    public void setUp() {
        wandRepository.deleteAll();
        hallenRepository.deleteAll();

        Halle halle1 = hallenRepository.save(new Halle("DAV Darmstadt", "Lichtwiesenweg 15", "DAV"));
        Halle halle2 = hallenRepository.save(new Halle("Heubach", "Adresse", "DAV"));
        hallenId1 = halle1.getId();
        hallenId2 = halle2.getId();

        wandService.addWand(new WandCreateDTO(hallenId1, "Wand in erster Halle",0,0,0,0,"indoor"));
        wandService.addWand(new WandCreateDTO(hallenId1, "Wand in zweiter Halle",0,0,0,0,"outdoor"));
        wandService.addWand(new WandCreateDTO(hallenId2, "Wand in zweiter Halle mit Toprope",0,0,0,0,"outdoor"));
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
        WandResponseDTO savedWand = wandService.addWand(new WandCreateDTO(hallenId1, "Neue Wand in erster Halle",0,0,0,0,"indoor"));
        assertThat(savedWand.getHallenId()).isEqualTo(hallenId1);
        assertThat(savedWand.getWandNr()).isEqualTo(3);
        assertThat(savedWand.getName()).isEqualTo("Neue Wand in erster Halle");
    }

    @Test
    void testDeleteById() {
        wandRepository.deleteById(new WandId(hallenId1, 1));
        assertThat(wandRepository.findById(new WandId(hallenId1, 1))).isEmpty();
    }
}
