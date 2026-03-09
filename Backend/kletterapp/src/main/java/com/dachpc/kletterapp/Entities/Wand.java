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
    private Integer hallenId;

    @Column(name = "wand_nr")
    private Integer wandNr;

    @Column(name = "sektor")
    private String sektor;

    protected Wand() {}

    public Wand(Integer hallenId, Integer wandNr, String sektor) {
        this.hallenId = hallenId;
        this.wandNr = wandNr;
        this.sektor = sektor;
    }

    // Getters and setters
    public int getId() { return id; }
    public Integer getHallenId() { return hallenId; }
    public Integer getWandNr() { return wandNr; }
    public String getSektor() { return sektor; }

    public void setId(int id) { this.id = id; }
    public void setHallenId(Integer hallenId) { this.hallenId = hallenId; }
    public void setWandNr(Integer wandNr) { this.wandNr = wandNr; }
    public void setSektor(String sektor) { this.sektor = sektor; }
}