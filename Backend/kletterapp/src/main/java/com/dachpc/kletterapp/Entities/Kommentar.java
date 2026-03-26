package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "kommentare")
@JsonPropertyOrder({"id", "routenId", "userId", "datum", "text"})
public class Kommentar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "keycloak_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private User user;

    @Column(name = "datum")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime datum;

    @Column(name = "kommentar_text")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

    protected Kommentar() {
    }

    public Kommentar(Route route, User user, LocalDateTime datum, String text) {
        this.route = route;
        this.user = user;
        this.datum = datum;
        this.text = text;
    }

    // Getters and setters
    public int getId() { return id; }
    public Route getRoute() { return route; }
    public User getUser() { return user; }
    public LocalDateTime getDatum() { return datum; }
    public String getText() { return text; }

    public void setId(int id) { this.id = id; }
    public void setRoute(Route route) { this.route = route; }
    public void setUser(User user) { this.user = user; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }
    public void setText(String text) { this.text = text; }
}