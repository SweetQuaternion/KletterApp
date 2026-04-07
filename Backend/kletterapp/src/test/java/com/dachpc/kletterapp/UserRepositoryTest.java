package com.dachpc.kletterapp;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;
import com.dachpc.kletterapp.Services.UserService;


public class UserRepositoryTest extends AbstractIntegrationTest {

    @Test
    void testContainer() {
        assertThat(postgresqlContainer.isRunning()).isTrue();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userService.syncUser(new UserSyncRequest("keycloakId1", "PremiumUser"));
        userService.syncUser(new UserSyncRequest("keycloakId2", "CoolerAdmin"));
    }

    @Test
    void testFindByName() {
        Optional<User> result1 = userRepository.findByName("PremiumUser");
        assertThat(result1).isPresent();
        assertThat(result1.get().getName()).isEqualTo("PremiumUser");
        assertThat(result1.get().getKeycloakId()).isEqualTo("keycloakId1");
    }

    @Test
    void testFindByNameNotFound() {
        Optional<User> result = userRepository.findByName("NonExistentUser");
        assertThat(result).isNotPresent();
    }

    @Test
    void testSaveUser() {
        User user = userService.syncUser(new UserSyncRequest("keycloakId3", "Mona"));
        User saved = userRepository.save(user);
        assertThat(saved.getKeycloakId()).isEqualTo("keycloakId3");
        assertThat(saved.getName()).isEqualTo("Mona");
        assertThat(saved.getBildUrl()).isNull();
        assertThat(saved.getBio()).isEqualTo("");
    }

    @Test
    void testFindById() {
        User saved = userService.syncUser(new UserSyncRequest("keycloakId3", "Mona"));
        User result = userService.findUser(saved.getKeycloakId(), null);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Mona");
    }

    @Test
    void testDeleteById() {
        User saved = userService.syncUser(new UserSyncRequest("keycloakId3", "Mona"));
        userRepository.deleteById(saved.getKeycloakId());
        assertThat(userRepository.findById(saved.getKeycloakId())).isEmpty();
    }

}