package com.dachpc.kletterapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers; 
import org.testcontainers.containers.PostgreSQLContainer;

@DataJpaTest
@Testcontainers
public class HallenRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("testuser")
            .withPassword("testpasswort")
            .withInitScript("init.sql");

    @Test
    void testFuzzySearch() {
        // Testcode hier
    }
}