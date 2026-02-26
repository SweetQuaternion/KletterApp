package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "hallen")
@JsonPropertyOrder({"id", "name", "adresse"})
public class Halle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "betreiber")
    private String betreiber;

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getAdresse() { return adresse; }
    public String getBetreiber() { return betreiber; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setBetreiber(String betreiber) { this.betreiber = betreiber; }
}