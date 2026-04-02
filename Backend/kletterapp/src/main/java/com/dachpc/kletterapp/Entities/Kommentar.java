package com.dachpc.kletterapp.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "kommentare")
@JsonPropertyOrder({"id", "routenId", "userId", "datum", "text"})
public class Kommentar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "keycloak_id")
    private User user;

    @Column(name = "datum")
    private LocalDateTime datum;

    @Column(name = "kommentar_text")
    private String text;


    public Kommentar(Route route, User user, LocalDateTime datum, String text) {
        this.route = route;
        this.user = user;
        this.datum = datum;
        this.text = text;
    }

}