package com.dachpc.kletterapp.Entities;

import java.time.LocalDate;
// import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
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
    private Boolean isToprope;

    @Column (name = "is_vorstieg")
    private Boolean isVorstieg;

    @Column (name = "schrauber")
    private String schrauber;

    @Column (name = "schraubdatum")
    private LocalDate schraubdatum;

    @Column (name = "is_active")
    private Boolean isActive;

    @Column (name = "beschreibung")
    private String beschreibung;

    // @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    // private List<Kommentar> kommentare;

    public Route(Wand wand, String name, String farbe, Float schwierigkeit, 
                Boolean isToprope, Boolean isVorstieg, String schrauber, 
                LocalDate schraubdatum, Boolean isActive, String beschreibung) {
        this.wand = wand;
        this.name = name;
        this.farbe = farbe;
        this.schwierigkeit = schwierigkeit;
        this.isToprope = isToprope;
        this.isVorstieg = isVorstieg;
        this.schrauber = schrauber;
        this.schraubdatum = schraubdatum;
        this.isActive = isActive;
        this.beschreibung = beschreibung;
    }

}
