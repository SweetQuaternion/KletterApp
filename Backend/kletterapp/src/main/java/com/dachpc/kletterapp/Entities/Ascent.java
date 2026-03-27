package com.dachpc.kletterapp.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "ascents")
@JsonPropertyOrder({"id", "userId", "routenId", "datum", "style", "sicherungsart"})
public class Ascent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @Column(name = "route_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int routenId;

    @Column(name = "datum")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate datum;

    @Enumerated(EnumType.STRING)
    @Column(name = "style")
    private Style style;

    @Enumerated(EnumType.STRING)
    @Column(name = "sicherung")
    private Sicherung sicherung;


    public Ascent(String userId, int routenId, LocalDate datum, Style style, Sicherung sicherung) {
        this.userId = userId;
        this.routenId = routenId;
        this.datum = datum;
        this.style = style;
        this.sicherung = sicherung;
    }
}