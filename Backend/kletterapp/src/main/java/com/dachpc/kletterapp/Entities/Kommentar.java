package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "kommentare")
@JsonPropertyOrder({"id", "routenId", "userId", "datum", "text"})
public class Kommentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @Column(name = "route_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int routenId;

    @Column(name = "user_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @Column(name = "datum")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime datum;

    @Column(name = "kommentar_text")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
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