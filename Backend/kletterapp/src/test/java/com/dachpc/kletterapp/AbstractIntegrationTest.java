package com.dachpc.kletterapp;

// import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@Testcontainers
public abstract class AbstractIntegrationTest {
    
    // Startet Testcontainer-DB wuhu
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:18.2")
        .withDatabaseName("test-db")
        .withUsername("testuser")
        .withPassword("testpasswort")
        .withInitScript("init.sql");

    // @AfterAll
    // static void afterAll() {
    //     postgresqlContainer.stop();
    //     postgresqlContainer.close();
    // }
}
