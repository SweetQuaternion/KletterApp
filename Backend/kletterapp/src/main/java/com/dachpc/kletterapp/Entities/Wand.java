package com.dachpc.kletterapp.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "wände")
@JsonPropertyOrder({"id", "hallenId", "wandNr", "sektor", "startX", "startY", "endX", "endY", "position", "routen"})
public class Wand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @Column(name = "hallen_id")
    private int hallenId;

    @Column(name = "wand_nr")
    private int wandNr;

    @Column(name = "name")
    private String name;

    @Column(name = "start_x")
    private float startX;

    @Column(name = "start_y")
    private float startY;

    @Column(name = "end_x")
    private float endX;

    @Column(name = "end_y")
    private float endY;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @OneToMany(mappedBy = "wand") // fetch = FetchType.EAGER hat es irgendwie nicht getan
    private List<Route> routen = new ArrayList<>();

}