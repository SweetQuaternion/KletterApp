package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// Tells Hibernate that this class should be tracked as a table
@Entity
// provides table name, if not provided, the table name would be the same as the class name
@Table(name = "routen")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int wand_id;
    private String name;
    private String farbe;
    private String schwierigkeit;
    private boolean is_toprope;
    private boolean is_vorstieg;
    private String Schrauber;
    private LocalDateTime schraubdatum;
    private boolean is_active;
    private String beschreibung;

    public int getId() {
        return id;
    }
    
    public int getWand_id() {
        return wand_id;
    }

    public String getName() {
        return name;
    }

    public String getFarbe() {
        return farbe;
    }

    public String getSchwierigkeit() {
        return schwierigkeit;
    }

    public boolean getIs_toprope() {
        return is_toprope;
    }

    public boolean getIs_vorstieg() {
        return is_vorstieg;
    }

    public String getSchrauber() {
        return Schrauber;
    }

    public LocalDateTime getSchraubdatum() {
        return schraubdatum;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWand_id(int wand_id) {
        this.wand_id = wand_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public void setSchwierigkeit(String schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }

    public void setIs_toprope(boolean is_toprope) {
        this.is_toprope = is_toprope;
    }

    public void setIs_vorstieg(boolean is_vorstieg) {
        this.is_vorstieg = is_vorstieg;
    }

    public void setSchrauber(String schrauber) {
        Schrauber = schrauber;
    }

    public void setSchraubdatum(LocalDateTime schraubdatum) {
        this.schraubdatum = schraubdatum;
    }

    public void setIsActive(boolean isActive) {
        this.is_active = isActive;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

}
