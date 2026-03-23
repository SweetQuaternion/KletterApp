package com.dachpc.kletterapp.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
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

    protected Wand() {}

    public Wand(Integer hallenId, Integer wandNr, String sektor, Float startX, Float startY, Float endX, Float endY, String position) {
        this.id = new WandId(hallenId, wandNr);
        this.sektor = sektor;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.position = position;
    }

    // Getters and setters
    public int getHallenId() { return id.getHallenId(); }
    public int getWandNr() { return id.getWandNr(); }
    public String getSektor() { return sektor; }
    public Float getStartX() { return startX; }
    public Float getStartY() { return startY; }
    public Float getEndX() { return endX; }
    public Float getEndY() { return endY; }
    public String getPosition() { return position; }
    public List<Route> getRouten() { return routen; }

    public void setId(WandId id) { this.id = id; }
    public void setHallenId(Integer hallenId) { this.id.setHallenId(hallenId); }
    public void setWandNr(Integer wandNr) { this.id.setWandNr(wandNr); }
    public void setSektor(String sektor) { this.sektor = sektor; }
    public void setStartX(Float startX) { this.startX = startX; }
    public void setStartY(Float startY) { this.startY = startY; }
    public void setEndX(Float endX) { this.endX = endX; }
    public void setEndY(Float endY) { this.endY = endY; }
    public void setPosition(String position) { this.position = position; }
}