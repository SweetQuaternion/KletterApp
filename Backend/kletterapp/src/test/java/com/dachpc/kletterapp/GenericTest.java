package com.dachpc.kletterapp;

import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class GenericTest {

    // private RedisBackedCache underTest;

    
    @Container
    public GenericContainer postgres = new GenericContainer(DockerImageName.parse("postgres:18.2"))
        .withExposedPorts(5433);

    // }



    @BeforeEach
    public void setUp() {
        // postgres.start();
        String address = postgres.getHost();
        Integer port = postgres.getFirstMappedPort();

        // Now we have an address and port for Redis, no matter where it is running
        // underTest = new RedisBackedCache(address, port);
    }

    // @Test
    // public void testSimplePutAndGet() {
    //     underTest.put("test", "example");

    //     String retrieved = underTest.get("test");
    //     assertThat(retrieved).isEqualTo("example");
    // }

    @Test
    public void testSomething() {
        // This is just a placeholder test to demonstrate the container setup
        // assertThat(postgres.isRunning()).isTrue();
    }
}