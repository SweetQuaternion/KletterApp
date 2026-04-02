package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KommentarCreateDTO {
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int routenId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

}
