package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KommentarResponseDTO {
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int routenId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String datum;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

}
