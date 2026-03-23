package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@JsonPropertyOrder({"id", "name", "email"})
public class User {

    @Id
    @Column(name = "keycloak_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String keycloakId;

    @Column(name = "name")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Column(name = "bild_url")
    private String bildUrl;

    @Column(name = "bio")
    private String bio;

    protected User() {
    }

    public User(String keycloakId, String name) {
        this.keycloakId = keycloakId;
        this.name = name;
    }

    // Getters and setters
    public String getKeycloakId() { return keycloakId; }
    public String getName() { return name; }
    public String getBildUrl() { return bildUrl; }
    public String getBio() { return bio; }

    public void setKeycloakId(String keycloakId) { this.keycloakId = keycloakId; }
    public void setName(String name) { this.name = name; }
    public void setBildUrl(String bildUrl) { this.bildUrl = bildUrl; }
    public void setBio(String bio) { this.bio = bio; }

    public String toString() {
        return "User{id=" + keycloakId + ", name='" + name + "', bildUrl='" + bildUrl + "', bio='" + bio + "'}";
    }
}