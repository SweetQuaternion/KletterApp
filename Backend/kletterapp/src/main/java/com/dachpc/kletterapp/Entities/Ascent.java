package com.dachpc.kletterapp.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ascents")
@JsonPropertyOrder({"id", "userId", "routenId", "datum", "style", "sicherungsart"})
public class Ascent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "route_id")
    private int routenId;

    @Column(name = "datum")
    private LocalDate datum;

    @Enumerated(EnumType.STRING)
    @Column(name = "style")
    private Style style;

    @Enumerated(EnumType.STRING)
    @Column(name = "sicherung")
    private Sicherung sicherung;

}