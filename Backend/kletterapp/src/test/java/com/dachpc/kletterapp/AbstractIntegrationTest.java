package com.dachpc.kletterapp;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

@Testcontainers
@SpringBootTest
public abstract class AbstractIntegrationTest {

    // Startet Testcontainer-DB wuhu
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18.2")
            .withDatabaseName("test-db")
            .withUsername("testuser")
            .withPassword("testpasswort")
            .withInitScript("init.sql");

    // Sagt Spring wo die DB ist
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);        // Nimm die URL vom Testcontainer
        registry.add("spring.datasource.username", postgres::getUsername);  // Nimm den Benutzernamen vom Testcontainer
        registry.add("spring.datasource.password", postgres::getPassword);  // Nimm das Passwort vom Testcontainer
    }
}
