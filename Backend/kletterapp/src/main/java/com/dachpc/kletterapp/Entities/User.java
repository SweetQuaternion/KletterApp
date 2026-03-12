package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
@JsonPropertyOrder({"id", "name", "email"})
public class User {

    @Id
    @Column(name = "keycloak_id")
    private String keycloakId;

    @Column(name = "name")
    private String name;

    @Column(name = "bild_url")
    private String bildUrl;

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

    public void setKeycloakId(String keycloakId) { this.keycloakId = keycloakId; }
    public void setName(String name) { this.name = name; }
    public void setBildUrl(String bildUrl) { this.bildUrl = bildUrl; }

    public String toString() {
        return "User{id=" + keycloakId + ", name='" + name + "', bildUrl='" + bildUrl + "'}";
    }
}