package com.dachpc.kletterapp.Dtos;

import java.time.LocalDate;

import com.dachpc.kletterapp.Entities.Sicherung;
import com.dachpc.kletterapp.Entities.Style;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscentCreateDTO {
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int routenId;

    private LocalDate datum;
    private Style style;
    private Sicherung sicherung;
    
}
