package com.dachpc.kletterapp.Entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Tells Hibernate that this class should be tracked as a table
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "routen")
@JsonPropertyOrder({"id", "name", "schwierigkeit", "farbe", "wand_id", "is_vorstieg", "is_toprope", "is_active", "schrauber", "schraubdatum", "beschreibung"})
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumns({
        @JoinColumn(name = "hallen_id", referencedColumnName = "hallen_id"),
        @JoinColumn(name = "wand_nr", referencedColumnName = "wand_nr")
    })
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Wand wand;

    @Column(name = "name")
    private String name;

    @Column(name = "farbe")
    private String farbe;

    @Column(name = "schwierigkeit")
    private Float schwierigkeit;

    @Column (name = "is_toprope")
    private Boolean is_toprope;

    @Column (name = "is_vorstieg")
    private Boolean is_vorstieg;

    @Column (name = "schrauber")
    private String schrauber;

    @Column (name = "schraubdatum")
    private LocalDate schraubdatum;

    @Column (name = "is_active")
    private Boolean is_active;

    @Column (name = "beschreibung")
    private String beschreibung;

    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    private List<Kommentar> kommentare;

    public Route(Wand wand, String name, String farbe, Float schwierigkeit, 
                Boolean is_toprope, Boolean is_vorstieg, String schrauber, 
                LocalDate schraubdatum, Boolean is_active, String beschreibung) {
        this.wand = wand;
        this.name = name;
        this.farbe = farbe;
        this.schwierigkeit = schwierigkeit;
        this.is_toprope = is_toprope;
        this.is_vorstieg = is_vorstieg;
        this.schrauber = schrauber;
        this.schraubdatum = schraubdatum;
        this.is_active = is_active;
        this.beschreibung = beschreibung;
    }

}
