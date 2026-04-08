package com.dachpc.kletterapp.Dtos;

import java.time.LocalDate;

import com.dachpc.kletterapp.Entities.Sicherung;
import com.dachpc.kletterapp.Entities.Style;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AscentResponseDTO {

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private RouteResponseDTO route;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate datum;

    private Style style;
    private Sicherung sicherung;
    
}
