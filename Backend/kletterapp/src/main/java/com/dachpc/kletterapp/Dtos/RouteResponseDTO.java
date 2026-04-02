package com.dachpc.kletterapp.Dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteResponseDTO {

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int hallenId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int wandNr;

    private String name;
    private String farbe;
    private Float schwierigkeit;
    private Boolean isToprope;
    private Boolean isVorstieg;
    private String schrauber;
    private LocalDate schraubdatum;
    private Boolean isActive;
    private String beschreibung;

}