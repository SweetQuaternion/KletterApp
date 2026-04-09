package com.dachpc.kletterapp.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "keycloak_id")
    private String keycloakId;

    @Column(name = "name")
    private String name;

    @Column(name = "bild_url")
    private String bildUrl;

    @Column(name = "bio")
    private String bio;

    @Column(name = "punkte")
    private int punkte;

    @Column(name = "ascent_count")
    private int ascentCount;


    public User(String keycloakId, String name) {
        this.keycloakId = keycloakId;
        this.name = name;
        this.bio = "";
        this.punkte = 0;
        this.ascentCount = 0;
    }

}