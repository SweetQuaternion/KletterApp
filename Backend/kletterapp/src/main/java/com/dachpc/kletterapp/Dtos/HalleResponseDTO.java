package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HalleResponseDTO {

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String adresse;

    private String betreiber;
    
}
