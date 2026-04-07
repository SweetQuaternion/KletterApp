package com.dachpc.kletterapp;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Dtos.AscentCreateDTO;
import com.dachpc.kletterapp.Entities.Ascent;
import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.Sicherung;
import com.dachpc.kletterapp.Entities.Style;
import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;
import com.dachpc.kletterapp.Repositories.AscentRepository;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;
import com.dachpc.kletterapp.Services.AscentService;
import com.dachpc.kletterapp.Services.UserService;


public class AscentRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AscentRepository ascentRepository;

    @Autowired
    private AscentService ascentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private HallenRepository hallenRepository;

    private int hallenId;
    private User user1;
    private User user2;
    private String userId1;
    private String userId2;
    private int routenId1;
    private int routenId2;

    @BeforeEach
    public void setUp() {

        hallenRepository.deleteAll();
        routenRepository.deleteAll();
        wandRepository.deleteAll();
        userRepository.deleteAll();

        Halle halle = hallenRepository.save(new Halle("DAV Darmstadt", "Lichtwiesenweg 15", "DAV"));
        hallenId = halle.getId();

        Wand wand1 = new Wand();
        wand1.setId(new WandId(hallenId, 1));
        wand1 = wandRepository.save(wand1);

        Route route1 = routenRepository.save(new Route(wand1, "Route 1", "rot", 5f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        Route route2 = routenRepository.save(new Route(wand1, "Route 2", "grün", 7f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        routenId1 = route1.getId();
        routenId2 = route2.getId();

        user1 = userService.syncUser(new UserSyncRequest("keycloakId1", "PremiumUser"));
        user2 = userService.syncUser(new UserSyncRequest("keycloakId2", "CoolerAdmin"));
        userId1 = user1.getKeycloakId();
        userId2 = user2.getKeycloakId();

        ascentService.addAscent(new AscentCreateDTO(userId1, routenId1, LocalDate.now(), Style.onsight, Sicherung.vorstieg));
        ascentService.addAscent(new AscentCreateDTO(userId1, routenId2, LocalDate.now(), Style.flash, Sicherung.vorstieg));
        ascentService.addAscent(new AscentCreateDTO(userId2, routenId1, LocalDate.now(), Style.redpoint, Sicherung.vorstieg));
        ascentService.addAscent(new AscentCreateDTO(userId2, routenId2, LocalDate.now(), Style.toprope, Sicherung.toprope));
        ascentService.addAscent(new AscentCreateDTO(userId1, routenId1, LocalDate.now(), Style.attempt, Sicherung.toprope));
    }
    
    @Test
    public void testSearchByUserId() {
        List<Ascent> result1 = ascentRepository.search(userId1, null);
        assertThat(result1).hasSize(3);
        List<Ascent> result2 = ascentRepository.search(userId2, null);
        assertThat(result2).hasSize(2);
    }

    @Test
    public void testSearchByRoutenId() {
        List<Ascent> result1 = ascentRepository.search(null, routenId1);
        assertThat(result1).hasSize(3);
        List<Ascent> result2 = ascentRepository.search(null, routenId2);
        assertThat(result2).hasSize(2);
    }

    @Test
    public void testSearchByUserIdAndRoutenId() {
        List<Ascent> result1 = ascentRepository.search(userId1, routenId1);
        assertThat(result1).hasSize(2);
        List<Ascent> result2 = ascentRepository.search(userId1, routenId2);
        assertThat(result2).hasSize(1);
        List<Ascent> result3 = ascentRepository.search(userId2, routenId1);
        assertThat(result3).hasSize(1);
        List<Ascent> result4 = ascentRepository.search(userId2, routenId2);
        assertThat(result4).hasSize(1);
    }
}
