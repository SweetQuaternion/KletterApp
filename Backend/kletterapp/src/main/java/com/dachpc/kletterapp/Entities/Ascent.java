package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "ascents")
@JsonPropertyOrder({"id", "userId", "routenId", "datum", "style", "sicherungsart"})
public class Ascent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @Column(name = "route_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int routenId;

    @Column(name = "datum")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime datum;

    @Column(name = "style")
    private String style;

    @Column(name = "sicherung")
    private String sicherung;

    protected Ascent() {
    }

    public Ascent(String userId, int routenId, LocalDateTime datum, String style, String sicherungsart) {
        this.userId = userId;
        this.routenId = routenId;
        this.datum = datum;
        this.style = style;
        this.sicherung = sicherungsart;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getUserId() { return userId; }
    public int getRoutenId() { return routenId; }
    public LocalDateTime getDatum() { return datum; }
    public String getStyle() { return style; }
    public String getSicherung() { return sicherung; }

    public void setId(int id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setRoutenId(int routenId) { this.routenId = routenId; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }
    public void setStyle(String style) { this.style = style; }
    public void setSicherung(String sicherung) { this.sicherung = sicherung; }
}