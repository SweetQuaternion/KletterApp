package com.dachpc.kletterapp;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.Ascent;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Repositories.AscentRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;


public class AscentRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private AscentRepository ascentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private WandRepository wandRepository;

    private int userId1;
    private int userId2;
    private int wandId;
    private int routeId1;
    private int routeId2;

    @BeforeEach
    public void setUp() {
        routenRepository.deleteAll();
        wandRepository.deleteAll();
        userRepository.deleteAll();
        Wand wand = wandRepository.save(new Wand(1,1,"Sektor in erster Halle"));
        wandId = wand.getId();
        Route route1 = routenRepository.save(new Route(wandId, "Route 1", "rot", 5.3f));
        Route route2 = routenRepository.save(new Route(wandId, "Route 2", "blau", 6.3f));
        routeId1 = route1.getId();
        routeId2 = route2.getId();
        User user1 = userRepository.save( new User( "Max Mustermann", "max.mustermann@example.com", "password123" ));
        User user2 = userRepository.save( new User( "Erika Musterfrau", "erika.musterfrau@example.com", "password456" ));
        userId1 = user1.getId();
        userId2 = user2.getId();

        Ascent ascent1 = new Ascent(userId1, routeId1, LocalDateTime.now(), "onsight", "vorstieg");
        Ascent ascent2 = new Ascent(userId1, routeId2, LocalDateTime.now(), "flash", "vorstieg");
        Ascent ascent3 = new Ascent(userId2, routeId1, LocalDateTime.now(), "redpoint", "vorstieg");
        Ascent ascent4 = new Ascent(userId2, routeId2, LocalDateTime.now(), "toprope", "toprope");
        Ascent ascent5 = new Ascent(userId1, routeId1, LocalDateTime.now(), "attempt", "toprope");

        ascentRepository.save(ascent1);
        ascentRepository.save(ascent2);
        ascentRepository.save(ascent3);
        ascentRepository.save(ascent4);
        ascentRepository.save(ascent5);
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
        List<Ascent> result1 = ascentRepository.search(null, routeId1);
        assertThat(result1).hasSize(3);
        List<Ascent> result2 = ascentRepository.search(null, routeId2);
        assertThat(result2).hasSize(2);
    }

    @Test
    public void testSearchByUserIdAndRoutenId() {
        List<Ascent> result1 = ascentRepository.search(userId1, routeId1);
        assertThat(result1).hasSize(2);
        List<Ascent> result2 = ascentRepository.search(userId1, routeId2);
        assertThat(result2).hasSize(1);
        List<Ascent> result3 = ascentRepository.search(userId2, routeId1);
        assertThat(result3).hasSize(1);
        List<Ascent> result4 = ascentRepository.search(userId2, routeId2);
        assertThat(result4).hasSize(1);
    }
}
