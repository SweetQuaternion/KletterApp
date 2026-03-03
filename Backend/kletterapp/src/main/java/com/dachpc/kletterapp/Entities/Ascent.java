package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "ascents")
@JsonPropertyOrder({"id", "userId", "routenId", "datum", "style", "sicherungsart"})
public class Ascent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "route_id")
    private int routenId;

    @Column(name = "datum")
    private LocalDateTime datum;

    @Column(name = "style")
    private String style;

    @Column(name = "sicherung")
    private String sicherung;

    protected Ascent() {
    }

    public Ascent(int userId, int routenId, LocalDateTime datum, String style, String sicherungsart) {
        this.userId = userId;
        this.routenId = routenId;
        this.datum = datum;
        this.style = style;
        this.sicherung = sicherungsart;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getRoutenId() { return routenId; }
    public LocalDateTime getDatum() { return datum; }
    public String getStyle() { return style; }
    public String getSicherung() { return sicherung; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setRoutenId(int routenId) { this.routenId = routenId; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }
    public void setStyle(String style) { this.style = style; }
    public void setSicherung(String sicherung) { this.sicherung = sicherung; }
}