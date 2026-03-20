package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "kommentare")
@JsonPropertyOrder({"id", "routenId", "userId", "datum", "text"})
public class Kommentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "route_id", nullable = false)
    private int routenId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "datum", nullable = false)
    private LocalDateTime datum;

    @Column(name = "kommentar_text", nullable = false)
    private String text;

    protected Kommentar() {
    }

    public Kommentar(int routenId, String userId, LocalDateTime datum, String text) {
        this.routenId = routenId;
        this.userId = userId;
        this.datum = datum;
        this.text = text;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getRoutenId() { return routenId; }
    public String getUserId() { return userId; }
    public LocalDateTime getDatum() { return datum; }
    public String getText() { return text; }

    public void setId(int id) { this.id = id; }
    public void setRoutenId(int routenId) { this.routenId = routenId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }
    public void setText(String text) { this.text = text; }
}