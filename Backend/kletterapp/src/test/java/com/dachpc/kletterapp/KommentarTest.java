package com.dachpc.kletterapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dachpc.kletterapp.Dtos.KommentarCreateDTO;
import com.dachpc.kletterapp.Dtos.KommentarResponseDTO;
import com.dachpc.kletterapp.Entities.Kommentar;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Repositories.KommentarRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Services.KommentarService;


public class KommentarTest extends AbstractIntegrationTest {
    
    @Autowired
    private KommentarRepository kommentarRepository;

    @Autowired
    private KommentarService kommentarService;

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private HallenRepository hallenRepository;

    User user1;
    User user2;
    String userId1;
    String userId2;
    int routenId1;
    int routenId2;
    int hallenId;

    @BeforeEach
    public void setUp() {
        kommentarRepository.deleteAll();
        routenRepository.deleteAll();
        userRepository.deleteAll();

        user1 = userRepository.save(new User("keycloakId1", "User 1"));
        user2 = userRepository.save(new User("keycloakId2", "User 2"));
        userId1 = user1.getKeycloakId();
        userId2 = user2.getKeycloakId();

        Halle halle = hallenRepository.save(new Halle("Test Halle", "Test Adresse", "Test Betreiber"));
        hallenId = halle.getId();
        
        Wand wand = new Wand();
        wand = wandRepository.save(wand);

        Route route1 = routenRepository.save(new Route(wand, "Route 1", "Rot", 5.10f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        Route route2 = routenRepository.save(new Route(wand, "Route 2", "Blau", 5.11f, false, true, "Schrauber 2", LocalDate.now(), true, "Beschreibung 2"));
        routenId1 = route1.getId();
        routenId2 = route2.getId();

        kommentarRepository.save(new Kommentar(route1, user1, LocalDateTime.now(), "Tolle Route!"));
        kommentarRepository.save(new Kommentar(route1, user2, LocalDateTime.now(), "War ganz ok."));
        kommentarRepository.save(new Kommentar(route2, user1, LocalDateTime.now(), "Für diesen Kommentar werde ich gebannt"));
        kommentarRepository.save(new Kommentar(route2, user2, LocalDateTime.now(), "Hallo Welt!"));
    }

    @Test
    public void testFindByRoutenId() {
        List<Kommentar> result1 = kommentarRepository.findByRoute_id(routenId1);
        assertThat(result1).hasSize(2);
        List<Kommentar> result2 = kommentarRepository.findByRoute_id(routenId2);
        assertThat(result2).hasSize(2);
    }

    @Test
    public void testFindByUserId() {
        List<Kommentar> result1 = kommentarRepository.findByUser_keycloakId(userId1);
        assertThat(result1).hasSize(2);
        List<Kommentar> result2 = kommentarRepository.findByUser_keycloakId(userId2);
        assertThat(result2).hasSize(2);
    }

    @Test
    public void testSaveKommentar() {
        KommentarResponseDTO saved = kommentarService.addKommentar(new KommentarCreateDTO(routenId1, userId1, "Test"));
        assertThat(saved.getId()).isPositive();
        assertThat(saved.getUsername()).isEqualTo(user1.getName());
        assertThat(saved.getRoutenId()).isEqualTo(routenId1);
        assertThat(saved.getText()).isEqualTo("Test");
    }

    @Test
    public void testDeleteKommentar() {
        KommentarResponseDTO saved = kommentarService.addKommentar(new KommentarCreateDTO(routenId1, userId1, "Test"));
        kommentarRepository.deleteById(saved.getId());
        assertThat(kommentarRepository.findById(saved.getId())).isEmpty();
    }
}
