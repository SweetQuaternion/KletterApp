package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "hallen")
@JsonPropertyOrder({"id", "name", "adresse"})
public class Halle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @Column(name = "name")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Column(name = "adresse")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    
    private String adresse;

    @Column(name = "betreiber")
    private String betreiber;

    
    public Halle(String name, String adresse, String betreiber) {
        this.name = name;
        this.adresse = adresse;
        this.betreiber = betreiber;
    }

}