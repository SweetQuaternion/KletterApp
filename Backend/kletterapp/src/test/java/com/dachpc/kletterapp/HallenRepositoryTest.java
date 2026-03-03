package com.dachpc.kletterapp;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Repositories.HallenRepository;


@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@Testcontainers
public class HallenRepositoryTest {


    @Container
    @ServiceConnection
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:18.2")
        .withDatabaseName("test-db")
        .withUsername("testuser")
        .withPassword("testpasswort")
        .withInitScript("init.sql");

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

        List<Halle> result2 = hallenRepository.search("Hamburg");
        assertThat(result2).isEmpty();
    }
}