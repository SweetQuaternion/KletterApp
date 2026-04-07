package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
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


    public User(String keycloakId, String name) {
        this.keycloakId = keycloakId;
        this.name = name;
        this.bio = "";
    }

}