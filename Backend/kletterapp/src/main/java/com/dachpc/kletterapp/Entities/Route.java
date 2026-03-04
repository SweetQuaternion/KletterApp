package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

// Tells Hibernate that this class should be tracked as a table
@Entity
// provides table name, if not provided, the table name would be the same as the class name
@Table(name = "routen")
@JsonPropertyOrder({"id", "name", "schwierigkeit", "farbe", "wand_id", "is_vorstieg", "is_toprope", "is_active", "schrauber", "schraubdatum", "beschreibung"})
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int wand_id;
    private String name;
    private String farbe;
    private float schwierigkeit;
    private boolean is_toprope;
    private boolean is_vorstieg;
    private String schrauber;
    private LocalDateTime schraubdatum;
    private boolean is_active;
    private String beschreibung;

    protected Route() {}

    public Route(int wand_id, String name, String farbe, float schwierigkeit, boolean is_toprope, boolean is_vorstieg, String schrauber, LocalDateTime schraubdatum, boolean is_active, String beschreibung) {
        this.wand_id = wand_id;
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

    public Route(int wand_id, String name, String farbe, float schwierigkeit) {
        this.wand_id = wand_id;
        this.name = name;
        this.farbe = farbe;
        this.schwierigkeit = schwierigkeit;
        this.is_toprope = false;
        this.is_vorstieg = true;
        this.schrauber = null;
        this.schraubdatum = LocalDateTime.now();
        this.is_active = true;
        this.beschreibung = null;
    }


    // Getters and setters

    public int getId() { return id; }
    public int getWand_id() { return wand_id; }
    public String getName() { return name; }
    public String getFarbe() { return farbe; }
    public float getSchwierigkeit() { return schwierigkeit; }
    public boolean getIs_toprope() { return is_toprope; }
    public boolean getIs_vorstieg() { return is_vorstieg; }
    public String getSchrauber() { return schrauber; }
    public LocalDateTime getSchraubdatum() { return schraubdatum; }
    public boolean isIs_active() { return is_active; }
    public String getBeschreibung() { return beschreibung; }

    public void setId(int id) { this.id = id; }
    public void setWand_id(int wand_id) { this.wand_id = wand_id; }
    public void setName(String name) { this.name = name; }
    public void setFarbe(String farbe) { this.farbe = farbe; }
    public void setSchwierigkeit(float schwierigkeit) { this.schwierigkeit = schwierigkeit; }
    public void setIs_toprope(boolean is_toprope) { this.is_toprope = is_toprope; }
    public void setIs_vorstieg(boolean is_vorstieg) { this.is_vorstieg = is_vorstieg; }
    public void setSchrauber(String schrauber) { this.schrauber = schrauber; }
    public void setSchraubdatum(LocalDateTime schraubdatum) { this.schraubdatum = schraubdatum; }
    public void setIsActive(boolean isActive) { this.is_active = isActive; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
}
