package com.dachpc.kletterapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Repositories.UserRoutenStatusRepository;
import com.dachpc.kletterapp.Repositories.WandRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;
import com.dachpc.kletterapp.Services.UserRoutenStatusService;
import com.dachpc.kletterapp.Services.UserService;

public class UserRoutenStatusRepositoryTest extends AbstractIntegrationTest {
    
    @Autowired
    private UserRoutenStatusRepository userRoutenStatusRepository;

    @Autowired
    private UserRoutenStatusService userRoutenStatusService;

    @Autowired
    private RoutenRepository routenRepository;

    @Autowired
    private WandRepository wandRepository;

    @Autowired
    private HallenRepository hallenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    User user1;
    User user2;
    String userId1;
    String userId2;
    int hallenId;
    int routenId1;
    int routenId2;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        user1 = userService.syncUser(new UserSyncRequest("keycloakId1", "PremiumUser"));
        user2 = userService.syncUser(new UserSyncRequest("keycloakId2", "CoolerAdmin"));
        userId1 = user1.getKeycloakId();
        userId2 = user2.getKeycloakId();

        Halle halle1 = hallenRepository.save(new Halle("Test Halle", "Test Adresse", "Test Betreiber"));
        hallenId = halle1.getId();

        Wand wand1 = new Wand();
        wand1.setId(new WandId(hallenId, 1));
        wand1 = wandRepository.save(wand1);

        routenRepository.deleteAll();
        Route route1 = routenRepository.save(new Route(wand1, "Route 1", "rot", 5f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        Route route2 = routenRepository.save(new Route(wand1, "Route 2", "grün", 7f, true, false, "Schrauber 1", LocalDate.now(), true, "Beschreibung 1"));
        routenId1 = route1.getId();
        routenId2 = route2.getId();

        userRoutenStatusRepository.deleteAll();
        userRoutenStatusService.addStatus(new UserRoutenStatus(userId1, routenId1));
        userRoutenStatusService.addStatus(new UserRoutenStatus(userId1, routenId2));
        userRoutenStatusService.addStatus(new UserRoutenStatus(userId2, routenId2));
    }

    @Test
    public void testFindByUserIdAndRoutenId() {
        UserRoutenStatus result = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(userId1, routenId1);
        assertThat(result).isNotNull();
        assertThat(result.getIsFavorit()).isFalse();
        assertThat(result.getIsProjekt()).isFalse();
        assertThat(result.getGeschSchwierigkeit()).isNull();
        assertThat(result.getNotiz()).isEqualTo("");
    }

    @Test
    public void testFindByUserId() {
        List<UserRoutenStatus> result = userRoutenStatusRepository.findByIdUserId(userId1);
        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindByRoutenId() {
        List<UserRoutenStatus> result = userRoutenStatusRepository.findByIdRouteId(routenId2);
        assertThat(result).hasSize(2);
    }

    @Test
    public void testChangeFavoritStatus() {
        UserRoutenStatus status = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(userId1, routenId1);
        status.setIsFavorit(false);
        userRoutenStatusRepository.save(status);

        UserRoutenStatus updatedStatus = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(userId1, routenId1);
        assertThat(updatedStatus.getIsFavorit()).isFalse();
    }

}
