package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "user_routen_status", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "route_id"}))
@JsonPropertyOrder({"userId", "routenId", "isFavorit", "isProjekt", "geschSchwierigkeit", "notiz"})
public class UserRoutenStatus {

    @EmbeddedId
    // @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private UserRoutenStatusId id;

    @Column(name = "isfavorit")
    private Boolean isFavorit;

    @Column(name = "isprojekt")
    private Boolean isProjekt;

    @Column(name = "gesch_schwierigkeit")
    private Float geschSchwierigkeit;

    @Column(name = "notiz")
    private String notiz;


    public UserRoutenStatus(String userId, int routenId, boolean isFavorit, boolean isProjekt, float geschSchwierigkeit, String notiz) {
        this.id = new UserRoutenStatusId(userId, routenId);
        this.isFavorit = isFavorit;
        this.isProjekt = isProjekt;
        this.geschSchwierigkeit = geschSchwierigkeit;
        this.notiz = notiz;
    }

    public UserRoutenStatus(String userId, int routenId) {
        this.id = new UserRoutenStatusId(userId, routenId);
        this.isFavorit = false;
        this.isProjekt = false;
    }

    public String getUserId() {
        return id.getUserId();
    }

    public int getRoutenId() {
        return id.getRouteId();
    }

}
