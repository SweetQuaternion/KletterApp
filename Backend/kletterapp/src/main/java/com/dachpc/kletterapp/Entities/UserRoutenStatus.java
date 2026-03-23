package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "user_routen_status", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "route_id"}))
@JsonPropertyOrder({"userId", "routenId", "isFavorit", "isProjekt", "geschSchwierigkeit", "notiz"})
public class UserRoutenStatus {

    @EmbeddedId
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private UserRoutenStatusId id;

    @Column(name = "isfavorit")
    private Boolean isFavorit;

    @Column(name = "isprojekt")
    private Boolean isProjekt;

    @Column(name = "gesch_schwierigkeit")
    private Float geschSchwierigkeit;

    @Column(name = "notiz")
    private String notiz;

    protected UserRoutenStatus() {}

    public UserRoutenStatus(String userId, int routenId, boolean isFavorit, boolean isProjekt, float geschSchwierigkeit, String notiz) {
        this.id = new UserRoutenStatusId(userId, routenId);
        this.isFavorit = isFavorit;
        this.isProjekt = isProjekt;
        this.geschSchwierigkeit = geschSchwierigkeit;
        this.notiz = notiz;
    }

    // Getters and setters
    public String getUserId() { return id.getUserId(); }
    public int getRoutenId() { return id.getRouteId(); }
    public boolean isFavorit() { return isFavorit; }
    public boolean isProjekt() { return isProjekt; }
    public float getGeschSchwierigkeit() { return geschSchwierigkeit; }
    public String getNotiz() { return notiz; }

    public void setUserId(String userId) { this.id.setUserId(userId); }
    public void setRoutenId(int routenId) { this.id.setRouteId(routenId); }
    public void setFavorit(boolean favorit) { isFavorit = favorit; }
    public void setProjekt(boolean projekt) { isProjekt = projekt; }
    public void setGeschSchwierigkeit(float geschSchwierigkeit) { this.geschSchwierigkeit = geschSchwierigkeit; }
    public void setNotiz(String notiz) { this.notiz = notiz; }
}
