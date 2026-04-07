package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor 
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


    public UserRoutenStatus(String userId, int routenId) {
        this.id = new UserRoutenStatusId(userId, routenId);
        this.isFavorit = false;
        this.isProjekt = false;
        this.notiz = "";
    }

    public String getUserId() {
        return id.getUserId();
    }

    public int getRoutenId() {
        return id.getRouteId();
    }

}
