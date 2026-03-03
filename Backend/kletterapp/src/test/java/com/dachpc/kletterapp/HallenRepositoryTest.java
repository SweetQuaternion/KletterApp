package com.dachpc.kletterapp;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Repositories.HallenRepository;


public class HallenRepositoryTest extends AbstractIntegrationTest {

    @Test
    void testContainer() {
        assertThat(postgresqlContainer.isRunning()).isTrue();
    }

    @Autowired
    private HallenRepository hallenRepository;

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

    
}