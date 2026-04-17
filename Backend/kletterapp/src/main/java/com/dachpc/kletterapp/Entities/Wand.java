package com.dachpc.kletterapp.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "wände")
@JsonPropertyOrder({"id", "hallenId", "wandNr", "sektor", "startX", "startY", "endX", "endY", "position", "routen"})
public class Wand {

    @EmbeddedId
    private WandId id;

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
    private List<Route> routen;
    

    int getHallenId() {
        return id.getHallenId();
    }

    int getWandNr() {
        return id.getWandNr();
    }

}