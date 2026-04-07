package com.dachpc.kletterapp.Entities;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// können wir in andere Klassen einbetten oder so
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoutenStatusId implements Serializable{

    @NotNull
    @Column(name = "user_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    
    @NotNull
    @Column(name = "route_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int routeId;
    
}
