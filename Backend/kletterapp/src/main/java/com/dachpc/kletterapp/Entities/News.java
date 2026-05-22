package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "news")
public class News {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @ManyToOne
    @JoinColumn(name = "hallen_id", referencedColumnName = "id", nullable = true)
    private Halle halle;

    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "keycloak_id", nullable = false)
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private User user;

    @Column(name = "datum")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime datum;

    @Column(name = "titel")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String titel;

    @Column(name = "inhalt")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String inhalt;

}
