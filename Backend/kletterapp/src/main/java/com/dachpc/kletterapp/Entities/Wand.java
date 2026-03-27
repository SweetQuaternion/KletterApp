package com.dachpc.kletterapp.Entities;

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

    @EmbeddedId
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private WandId id;

    @Column(name = "sektor")
    private String sektor;

    @Column(name = "start_x")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Float startX;

    @Column(name = "start_y")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Float startY;

    @Column(name = "end_x")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Float endX;

    @Column(name = "end_y")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private Float endY;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "wand") // fetch = FetchType.EAGER hat es irgendwie nicht getan
    private List<Route> routen;


    public Wand(Integer hallenId, Integer wandNr, String sektor, Float startX, Float startY, Float endX, Float endY, String position) {
        this.id = new WandId(hallenId, wandNr);
        this.sektor = sektor;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.position = position;
    }

}