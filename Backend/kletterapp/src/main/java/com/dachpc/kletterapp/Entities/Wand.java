package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "wände")
@JsonPropertyOrder({"id", "hallenId", "wandNr", "sektor"})
public class Wand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hallen_id")
    private int hallenId;

    @Column(name = "wand_nr")
    private int wandNr;

    @Column(name = "sektor")
    private String sektor;

    protected Wand() {}

    public Wand(int hallenId, int wandNr, String sektor) {
        this.hallenId = hallenId;
        this.wandNr = wandNr;
        this.sektor = sektor;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getHallenId() { return hallenId; }
    public int getWandNr() { return wandNr; }
    public String getSektor() { return sektor; }

    public void setId(int id) { this.id = id; }
    public void setHallenId(int hallenId) { this.hallenId = hallenId; }
    public void setWandNr(int wandNr) { this.wandNr = wandNr; }
    public void setSektor(String sektor) { this.sektor = sektor; }
}