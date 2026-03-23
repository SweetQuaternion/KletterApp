package com.dachpc.kletterapp.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

// Tells Hibernate that this class should be tracked as a table
@Entity
// provides table name, if not provided, the table name would be the same as the class name
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

    protected Route() {}

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


    // Getters and setters

    public Integer getId() { return id; }
    public Integer getHallenId() { return wand.getHallenId(); }
    public Integer getWandNr() { return wand.getWandNr(); }
    public Wand getWand() { return wand; }
    public String getName() { return name; }
    public String getFarbe() { return farbe; }
    public Float getSchwierigkeit() { return schwierigkeit; }
    public Boolean getIs_toprope() { return is_toprope; }
    public Boolean getIs_vorstieg() { return is_vorstieg; }
    public String getSchrauber() { return schrauber; }
    public LocalDate getSchraubdatum() { return schraubdatum; }
    public Boolean isIs_active() { return is_active; }
    public String getBeschreibung() { return beschreibung; }

    public void setId(Integer id) { this.id = id; }
    public void setWand(Wand wand) { this.wand = wand; }
    public void setName(String name) { this.name = name; }
    public void setFarbe(String farbe) { this.farbe = farbe; }
    public void setSchwierigkeit(Float schwierigkeit) { this.schwierigkeit = schwierigkeit; }
    public void setIs_toprope(Boolean is_toprope) { this.is_toprope = is_toprope; }
    public void setIs_vorstieg(Boolean is_vorstieg) { this.is_vorstieg = is_vorstieg; }
    public void setSchrauber(String schrauber) { this.schrauber = schrauber; }
    public void setSchraubdatum(LocalDate schraubdatum) { this.schraubdatum = schraubdatum; }
    public void setIsActive(Boolean isActive) { this.is_active = isActive; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
}
