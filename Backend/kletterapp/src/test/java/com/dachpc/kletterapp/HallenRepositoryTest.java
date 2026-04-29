package com.dachpc.kletterapp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;
import com.dachpc.kletterapp.Services.HalleService;


public class HallenRepositoryTest extends AbstractIntegrationTest {

    @Test
    void testContainer() {
        assertThat(postgresqlContainer.isRunning()).isTrue();
    }

    @Autowired
    private HallenRepository hallenRepository;

    @Autowired
    private HalleService hallenService;

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private RoutenRepository routenRepository;

    @BeforeEach
    void setUp() {
        hallenRepository.deleteAll();
        hallenRepository.save( new Halle( "DAV Kletterzentrum Darmstadt", "Lichtwiesenweg 15, 64287 Darmstadt", "DAV Sektion Darmstadt Starkenburg" ));
        hallenRepository.save( new Halle( "DAV Sandsteinbruch Heubach", "Wilhelm-Leuschner-Straße 250, 64823 Groß-Umstadt", "DAV Sektion Darmstadt Starkenburg"));
        hallenRepository.save( new Halle( "Boulderhalle Frankfurt","Mainzer Landstraße 123","Boulder GmbH"));
        hallenRepository.save( new Halle( "Kletterhalle Wiesbaden", "Rheinstraße 45", "Kletterfreunde Wiesbaden" ) );
        hallenRepository.save( new Halle( "DAV Kletterzentrum Frankfurt","Mainzer Landstraße 123, 60329 Frankfurt am Main","DAV Sektion Frankfurt"));
    }

    @Test
    void testFuzzySearch() {
        List<Halle> result1 = hallenRepository.search("Darmstadt");
        assertThat(result1).hasSize(2);
        assertThat(result1.get(0).getName()).isEqualTo("DAV Kletterzentrum Darmstadt");
        assertThat(result1.get(1).getName()).isEqualTo("DAV Sandsteinbruch Heubach");        
    }

    @Test
    void testSearchNoResults() {
        List<Halle> result2 = hallenRepository.search("Hamburg");
        assertThat(result2).isEmpty();
        List<Halle> result = hallenRepository.search("NonExistentHall");
        assertThat(result).isEmpty();
    }

    @Test
    void testFindById() {
        Halle saved = hallenRepository.save(new Halle("DAV Kletterzentrum Darmstadt", "Lichtwiesenweg 15, 64287 Darmstadt", "DAV Sektion Darmstadt Starkenburg"));
        Optional<Halle> result = hallenRepository.findById(saved.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("DAV Kletterzentrum Darmstadt");
    }

    @Test
    void deleteById() {
        Halle saved = hallenRepository.save(new Halle("DAV Kletterzentrum Darmstadt", "Lichtwiesenweg 15, 64287 Darmstadt", "DAV Sektion Darmstadt Starkenburg"));
        assertThat(hallenRepository.findById(saved.getId())).isPresent();
        hallenRepository.deleteById(saved.getId());
        Optional<Halle> result = hallenRepository.findById(saved.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void deleteByIdWithRouten() {
        Halle saved = hallenRepository.save(new Halle("DAV Kletterzentrum Darmstadt", "Lichtwiesenweg 15, 64287 Darmstadt", "DAV Sektion Darmstadt Starkenburg"));
        assertThat(hallenRepository.findById(saved.getId())).isPresent();
        Wand wand1 = new Wand();
        wand1.setId(new WandId(saved.getId(), 1));
        wand1 = wandRepository.save(wand1);
        Wand wand2 = new Wand();
        wand2.setId(new WandId(saved.getId(), 2));
        wand2 = wandRepository.save(wand2);
        routenRepository.save(new Route(wand1, "Route 1", "Rot", 5.10f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        routenRepository.save(new Route(wand1, "Route 2", "Blau", 5.11f, false, true, "Schrauber 2", LocalDate.now(), true, "Beschreibung 2"));
        routenRepository.save(new Route(wand2, "Route 3", "Grün", 5.12f, true, false, "Schrauber 3", LocalDate.now(), true, "Beschreibung 3"));
        routenRepository.save(new Route(wand2, "Route 4", "Gelb", 5.13f, false, true, "Schrauber 4", LocalDate.now(), true, "Beschreibung 4"));
        hallenService.deleteHalle(saved.getId());
        assertThat(hallenRepository.findById(saved.getId())).isEmpty();
    }

    
}